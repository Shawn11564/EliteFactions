package dev.mrshawn.elitefactions.commands

import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.lang.reflect.Field
import java.util.stream.Collectors

class FactionCommandManager: TabExecutor {

	private val commands = HashMap<List<String>, FactionCommand>()
	private val commandCompletions: HashMap<String, (CommandSender) -> Collection<String>> = HashMap()

	fun registerCommand(command: FactionCommand) {
		if (command.getAliases().isEmpty()) {
			Chat.error("Command ${command.javaClass.simpleName} does not have a CommandAliases annotation or has no aliases specified!")
			return
		}

		commands[command.getAliases()] = command
		registerToCommandMap(command)
	}

	private fun registerToCommandMap(command: FactionCommand) {
		try {
			val bukkitCommandMap: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
			bukkitCommandMap.isAccessible = true
			val commandMap = bukkitCommandMap.get(Bukkit.getServer()) as SimpleCommandMap

			command.getAliases().forEach { alias ->
				val bukkitCommand = object : Command(alias) {
					override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
						return onCommand(sender, this, commandLabel, args)
					}

					override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
						return onTabComplete(sender, this, alias, args)
					}
				}
				commandMap.register(alias, bukkitCommand)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun registerCompletion(id: String, completion: (CommandSender) -> Collection<String>) {
		commandCompletions[id] = completion
	}

	private fun getCommand(cmdString: String): FactionCommand? {
		val lowercase = cmdString.lowercase()
		return commands.values.stream()
			.filter { cmd: FactionCommand -> cmd.getAliases().contains(lowercase) }
			.findFirst()
			.orElse(null)
	}

	private fun parseCompletion(sender: CommandSender, completionID: String): Collection<String> {
		val completion = commandCompletions[completionID]
		return completion?.invoke(sender) ?: listOf()
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
		// find the valid command
		var currentCommand: FactionCommand? = getCommand(label)
		var i = 0
		while (i < args.size) {
			val subCommand = currentCommand?.getSubCommand(args[i])
			if (subCommand != null) {
				currentCommand = subCommand
				i++
			} else {
				break
			}
		}
		// check if the command overrides the player execute method
		val executeMethod = currentCommand?.javaClass?.getMethod("execute", Player::class.java, Array<String>::class.java)
		if (executeMethod?.declaringClass == currentCommand?.javaClass && sender !is Player) {
			Chat.tell(sender, EMessages.PRECONDITIONS_ERROR_NOT_PLAYER)
			return false
		}
		// check command preconditions
		if (currentCommand == null) return true
		val preconditions = currentCommand.getPreconditions().check(sender)
		if (!preconditions.first) {
			Chat.tell(sender, preconditions.second)
			return false
		}
		// execute the command
		currentCommand.execute(sender, if (args.isEmpty()) emptyArray() else args.copyOfRange(i, args.size))
		return true
	}

	override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String>): MutableList<String> {
		var currentCommand: FactionCommand? = getCommand(label)
		var i = 0
		while (i < args.size - 1) {
			val subCommand = currentCommand?.getSubCommand(args[i])
			if (subCommand != null) {
				currentCommand = subCommand
				i++
			} else {
				break
			}
		}
		val completions = if (i < args.size) {
			currentCommand?.getSubCommands()
				?.filter { it.getPreconditions().check(sender).first }
				?.flatMap { it.getAliases() }
				?.filter { it.startsWith(args[i]) }
				?.toMutableList() ?: mutableListOf()
		} else {
			currentCommand?.getSubCommands()
				?.filter { it.getPreconditions().check(sender).first }
				?.flatMap { it.getAliases() }
				?.toMutableList() ?: mutableListOf()
		}

		// Check if the execute method has the CommandCompletion annotation
		val executeMethod = currentCommand?.javaClass?.getMethod("execute", CommandSender::class.java, Array<String>::class.java)
		val commandCompletionAnnotation = executeMethod?.getAnnotation(CommandCompletion::class.java)
		if (commandCompletionAnnotation != null) {
			// parse completion
			val completionStrings = commandCompletionAnnotation.completions.split(" ")
			if (i <= completionStrings.size) {
				completions.addAll(
					parseCompletion(sender, completionStrings[i - 1]).stream()
						.filter { it.startsWith(args[i]) }
						.collect(Collectors.toList())
				)
			}
		}

		return completions
	}

}
