package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat

@CommandAlias("description|desc")
class DescriptionCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.description")
		.hasPermissible(PermissibleAction.CHANGE_DESCRIPTION)
		.hasFaction(true)
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