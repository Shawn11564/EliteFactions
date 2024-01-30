package dev.mrshawn.elitefactions.extensions

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun Class<*>.isCommandSender(): Boolean {
	return when (this) {
		Player::class.java -> true
		CommandSender::class.java -> true
		FPlayer::class.java -> true
		else -> false
	}
}