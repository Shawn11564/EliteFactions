package dev.mrshawn.elitefactions.extensions

import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.files.MessagesFile
import dev.mrshawn.elitefactions.files.struct.PathEnum
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

fun Chat.tell(toWhom: CommandSender, message: PathEnum) {
	val originalMessage = MessagesFile.get(message)
	if (originalMessage is String) {
		tell(toWhom, originalMessage)
	} else if (originalMessage is List<*>) {
		for (msg in originalMessage) {
			tell(toWhom, msg.toString())
		}
	}
}

fun Chat.tell(toWhom: CommandSender, message: PathEnum, vararg replacements: Any) {
	val originalMessage = MessagesFile.get(message)
	if (originalMessage is String) {
		tell(toWhom, doReplacements(originalMessage, *replacements))
	} else if (originalMessage is List<*>) {
		for (msg in originalMessage) {
			tell(toWhom, doReplacements(msg.toString(), *replacements))
		}
	}
}

fun Chat.tell(fPlayer: FPlayer, message: PathEnum) {
	val player = fPlayer.getPlayer() ?: return
	tell(player, message)
}

fun Chat.tell(fPlayer: FPlayer, message: PathEnum, vararg replacements: Any) {
	val player = fPlayer.getPlayer() ?: return
	tell(player, message, *replacements)
}

fun Chat.tell(faction: Faction, message: PathEnum) {
	faction.getMemberContainer().getOnlineMembers().forEach { member ->
		val player = Bukkit.getPlayer(member)
		if (player != null) tell(player, message)
	}
}

fun Chat.tell(faction: Faction, message: PathEnum, vararg replacements: Any) {
	faction.getMemberContainer().getOnlineMembers().forEach { member ->
		val player = Bukkit.getPlayer(member)
		if (player != null) tell(player, message, *replacements)
	}
}

fun Chat.tell(toWhom: UUID, message: String) {
	val player = Bukkit.getPlayer(toWhom) ?: return
	tell(player, message)
}

fun Chat.tell(toWhom: UUID, message: PathEnum) {
	val player = Bukkit.getPlayer(toWhom) ?: return
	tell(player, message)
}

fun Chat.tell(toWhom: UUID, message: PathEnum, vararg replacements: Any) {
	val player = Bukkit.getPlayer(toWhom) ?: return
	tell(player, message, *replacements)
}

fun doReplacements(message: String, vararg replacements: Any): String {
	var replacedMessage = message
	replacements.forEachIndexed { index, replacement ->
		replacedMessage = replacedMessage.replace("{${index}}", replacement.toString())
	}
	return replacedMessage
}