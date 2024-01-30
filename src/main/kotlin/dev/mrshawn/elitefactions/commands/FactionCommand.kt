package dev.mrshawn.elitefactions.commands

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.extensions.isCommandSender
import java.lang.reflect.Parameter

abstract class FactionCommand(
	private val preconditions: Preconditions = Preconditions.empty(),
	private var parentCommand: FactionCommand? = null
) {

	private val subcommands = ArrayList<FactionCommand>()
	private val aliases = javaClass.getAnnotation(CommandAlias::class.java).aliases.split("|").map { it.lowercase() }.toList()

	fun getExecuteMethod() = javaClass.methods.find { it.isAnnotationPresent(CommandExecutor::class.java) }

	fun getExecuteMethodParams(): Array<Parameter>? = getExecuteMethod()?.parameters

	fun getAliases(): List<String> {
		return aliases
	}

	fun addSubcommands(vararg subcommands: FactionCommand) {
		for (subcommand in subcommands) {
			this.subcommands.add(subcommand)
			subcommand.parentCommand = this
		}
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

	open fun getUsageMessage(): String {
		val usageMessage = StringBuilder().append("&cUsage: /")
		var currentCommand: FactionCommand? = this

		while (currentCommand != null) {
			usageMessage.insert(0, "${currentCommand.getAliases()[0]} ")
			currentCommand = currentCommand.parentCommand
		}

		getExecuteMethodParams()?.forEachIndexed { index, param ->
			if (!(index == 0 && param.type.isCommandSender())) {
				usageMessage.append(" <${param.type.simpleName.lowercase()}>")
			}
		}

		return usageMessage.toString()
	}

}