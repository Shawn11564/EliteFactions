package dev.mrshawn.elitefactions.commands

import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class FactionCommandManager: TabExecutor {

	private val commands = HashMap<Array<String>, FactionCommand>()

	fun registerCommand(command: FactionCommand) {
		if (command.getAliases().isEmpty()) {
			Chat.error("Command ${command.javaClass.simpleName} does not have a CommandAliases annotation or has no aliases specified!")
			return
		}

		commands[command.getAliases()] = command
	}

	fun getCommand(cmdString: String): FactionCommand? {
		val lowercase = cmdString.lowercase()
		return commands.values.stream()
			.filter { cmd: FactionCommand -> cmd.getAliases().contains(lowercase) }
			.findFirst()
			.orElse(null)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
		TODO("Not yet implemented")
	}

	override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String>): MutableList<String>? {
		TODO("Not yet implemented")
	}

}