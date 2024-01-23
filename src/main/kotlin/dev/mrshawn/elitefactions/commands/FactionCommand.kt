package dev.mrshawn.elitefactions.commands

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class FactionCommand(
	private val preconditions: Preconditions = Preconditions.empty()
) {

	private val subcommands = ArrayList<FactionCommand>()
	private val aliases = javaClass.getAnnotation(CommandAlias::class.java).aliases.split("|").map { it.lowercase() }.toList()

	open fun execute(sender: CommandSender, args: Array<String>) {
		if (sender is Player) execute(sender, args)
	}
	open fun execute(sender: Player, args: Array<String>) {}

	fun addSubcommands(vararg subcommands: FactionCommand) {
		for (subcommand in subcommands)
			this.subcommands.add(subcommand)
	}

	fun getAliases(): List<String> {
		return aliases
	}

	fun getSubCommand(alias: String): FactionCommand? {
		return subcommands.find { it.getAliases().contains(alias.lowercase()) }
	}

	fun getSubCommands(): List<FactionCommand> {
		return subcommands
	}

	fun getPreconditions(): Preconditions {
		return preconditions
	}

}