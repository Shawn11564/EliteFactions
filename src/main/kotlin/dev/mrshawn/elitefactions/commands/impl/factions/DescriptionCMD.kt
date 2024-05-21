package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.Precondition

@CommandAlias("description|desc")
class DescriptionCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.description")
		.hasPermissible(PermissibleAction.CHANGE_DESCRIPTION)
		.hasFaction()
		.build()
) {

	@CommandExecutor
	fun execute(fPlayer: FPlayer, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(fPlayer, EMessages.CMD_DESCRIPTION_USAGE)
			return
		}

		val faction = fPlayer.getFaction()
		faction.changeDescription(args.joinToString(" "))
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_DESCRIPTION_CHANGED, fPlayer.getPlayerName())
	}

	override fun getUsageMessage(): String {
		return EMessages.CMD_DESCRIPTION_USAGE.getMessage()
	}

}