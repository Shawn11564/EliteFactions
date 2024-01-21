package dev.mrshawn.elitefactions.commands

import dev.mrshawn.elitefactions.annotations.CommandAliases
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class FactionCommand(

) {

	private val subcommands = HashMap<Array<String>, FactionCommand>()

	open fun execute(sender: CommandSender, args: Array<String>) {}
	open fun execute(sender: Player, args: Array<String>) {}

	fun addSubcommands(vararg subcommands: FactionCommand) {
		for (subcommand in subcommands) {
			this.subcommands[subcommand.getAliases()] = subcommand
		}
	}

	fun getAliases(): Array<String> {
		if (!javaClass.isAnnotationPresent(CommandAliases::class.java)) {
			return arrayOf()
		}
		return javaClass.getAnnotation(CommandAliases::class.java).aliases.split("|").map { it.lowercase() }.toTypedArray()
	}

}