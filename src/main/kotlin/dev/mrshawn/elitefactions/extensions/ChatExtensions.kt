package dev.mrshawn.elitefactions.extensions

import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.CommandSender

fun Chat.tell(toWhom: CommandSender, vararg messages: String) {
	for (message in messages) {
		toWhom.sendMessage(colorize(message))
	}
}