package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("help|?")
class HelpCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands.help")
) {

	companion object {
		fun execute(sender: CommandSender) {
			Chat.tell(sender, EMessages.CMD_HELP_MESSAGE)
		}
	}

	override fun execute(sender: Player, args: Array<String>) {
		execute(sender)
	}

}