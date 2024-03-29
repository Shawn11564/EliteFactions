package dev.mrshawn.elitefactions.commands

import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.Optional
import dev.mrshawn.elitefactions.commands.enhancements.ExecutionContext
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.exceptions.ContextResolverFailedException
import dev.mrshawn.elitefactions.extensions.isCommandSender
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
	private val commandContexts: HashMap<Class<*>, (CommandSender, Array<String>) -> Any> = HashMap()

	init {
		// default completions
		registerCompletion("@nothing") { listOf(" ") }
		registerCompletion("@players") { Bukkit.getOnlinePlayers().map { it.name } }
		registerCompletion("@boolean") { listOf("true", "false") }

		// default contexts
		registerContext(ExecutionContext::class.java) { sender, args -> ExecutionContext(sender, args) }
		registerContext(Player::class.java) { _, args ->
			Bukkit.getPlayer(args[0]) ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_PLAYER_NOT_FOUND, args[0])
		}
		registerContext(FPlayer::class.java) { sender, _ -> FPlayer.get(sender) ?: throw ContextResolverFailedException(EMessages.PRECONDITIONS_ERROR_NOT_PLAYER) }
		registerContext(String::class.java) { _, args -> args[0] }
		registerContext(Array<String>::class.java) { _, args -> args }
		registerContext(Int::class.java) { _, args -> args[0].toIntOrNull() ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_NOT_A_NUMBER, args[0]) }
		registerContext(Double::class.java) { _, args -> args[0].toDoubleOrNull() ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_NOT_A_NUMBER, args[0]) }
		registerContext(Boolean::class.java) { _, args -> args[0].lowercase().toBooleanStrictOrNull() ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_NOT_A_BOOLEAN, args[0]) }
	}

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

	fun <T: Any> registerContext(clazz: Class<T>, resolver: (CommandSender, Array<String>) -> T) {
		commandContexts[clazz] = resolver
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

	private fun <T> parseContext(objType: Class<T>, sender: CommandSender, args: Array<String>, allowNull: Boolean = false): Any? {
		if (args.isEmpty() && allowNull) {
			return null
		} else if (args.isEmpty()) {
			throw ContextResolverFailedException()
		}
		val context = commandContexts[objType]?.invoke(sender, args)
		if (context == null && allowNull) {
			return null
		} else if (context == null) {
			throw ContextResolverFailedException()
		}
		return context
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

		val parsedContext: List<Any?>?
		try {
			parsedContext = currentCommand?.getExecuteMethodParams()?.mapIndexed { index, param ->
				// if the first param is CommandSender, Player, or FPlayer, handle it as the sender
				// else, try to resolve the context
				if (index == 0 && param.type.isCommandSender()) {
					when (param.type) {
						CommandSender::class.java -> sender
						Player::class.java -> sender as? Player
							?: throw ContextResolverFailedException(EMessages.PRECONDITIONS_ERROR_NOT_PLAYER)

						FPlayer::class.java -> FPlayer.get(sender)
							?: throw ContextResolverFailedException(EMessages.PRECONDITIONS_ERROR_NOT_PLAYER)

						else -> parseContext(param.type, sender, args.copyOfRange(index, args.size), param.isAnnotationPresent(Optional::class.java))
					}
				} else {
					parseContext(param.type, sender, args.copyOfRange(index, args.size), param.isAnnotationPresent(Optional::class.java))
						?: throw ContextResolverFailedException()
				}
			}
		} catch (ex: ContextResolverFailedException) {
			Chat.tell(sender, currentCommand?.getUsageMessage())
			return false
		}

		if (!parsedContext.isNullOrEmpty()) {
			currentCommand?.getExecuteMethod()?.invoke(currentCommand, *parsedContext.toTypedArray())
		} else {
			currentCommand?.getExecuteMethod()?.invoke(currentCommand)
		}
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
		val executeMethod = currentCommand?.getExecuteMethod()
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
