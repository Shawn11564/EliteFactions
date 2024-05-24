package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.Precondition

@CommandAlias("disband")
class DisbandCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.disband")
		.hasPermissible(PermissibleAction.DISBAND)
		.hasFaction()
		.build()
) {

	@CommandExecutor
	fun execute(fPlayer: FPlayer) {
		if (!fPlayer.hasFaction()) {
			Chat.tell(fPlayer, EMessages.CMD_ERROR_NOT_IN_FACTION)
			return
		}
		Chat.tell(fPlayer, EMessages.FACTIONS_ALERTS_FACTION_DISBANDED, fPlayer.getPlayerName())
		FactionManager.disbandFaction(fPlayer.getFaction())
		Chat.tell(fPlayer, EMessages.CMD_DISBAND_MESSAGE)
	}

	override fun getUsageMessage(): String {
		return EMessages.CMD_DISBAND_USAGE.getMessage()
	}

}