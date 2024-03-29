package dev.mrshawn.elitefactions.commands.impl.factions.admin

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.ConfigFile
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.CommandSender

@CommandAlias("reload")
class ReloadCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands.admin.reload")
) {

	@CommandExecutor
	fun execute(sender: CommandSender) {
		ConfigFile.reload()
		MessagesFile.reload()
		Chat.tell(sender, EMessages.CMD_ADMIN_RELOAD_MESSAGE)
	}

}