package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat

@CommandAlias("join")
class JoinCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.join")
		.notInFaction(true)
		.build()
) {

	@CommandCompletion("@pending-invites")
	@CommandExecutor
	fun execute(fPlayer: FPlayer, faction: Faction) {
		if (!faction.isOpen() && !faction.getMemberContainer().isInvited(fPlayer.getPlayerUUID())) {
			Chat.tell(fPlayer, EMessages.CMD_ERROR_NOT_INVITED)
			return
		}

		faction.getMemberContainer().addMember(fPlayer)
		fPlayer.setFaction(faction)

		Chat.tell(fPlayer, EMessages.CMD_JOIN_MESSAGE, faction.getName())
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_PLAYER_JOINED, fPlayer.getPlayerName())
	}

}