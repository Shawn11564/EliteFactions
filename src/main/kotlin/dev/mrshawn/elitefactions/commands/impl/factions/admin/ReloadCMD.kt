package dev.mrshawn.elitefactions.commands.impl.factions.admin

import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.ConfigFile
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.command.CommandSender

@CommandAlias("reload")
class ReloadCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.admin.reload")
	)
) {

	@CommandExecutor
	fun execute(sender: CommandSender) {
		ConfigFile.reload()
		MessagesFile.reload()
		Chat.tell(sender, EMessages.CMD_ADMIN_RELOAD_MESSAGE)
	}

}