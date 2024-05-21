package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.command.CommandSender

@CommandAlias("help|?")
class HelpCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.help")
	)
) {

	companion object {
		fun execute(sender: CommandSender) {
			Chat.tell(sender, EMessages.CMD_HELP_MESSAGE)
		}
	}

	@CommandExecutor
	fun execute(sender: CommandSender) {
		execute(sender)
	}

}