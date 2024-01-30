package dev.mrshawn.elitefactions.commands.enhancements

import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ExecutionContext(
	private val commandSender: CommandSender,
	private val args: Array<String>
) {

	fun getFaction(): Faction {
		return getFPlayer().getFaction()
	}

	fun getPlayer(): Player {
		return commandSender as Player
	}

	fun getName(): String {
		return getSender().name
	}

	fun getFPlayer(): FPlayer {
		return FPlayer.get(getPlayer())
	}

	fun getSender(): CommandSender {
		return commandSender
	}

	fun getArgs(): Array<String> {
		return args
	}

}