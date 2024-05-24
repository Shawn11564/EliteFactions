package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.preconditions.Precondition

@dev.mrshawn.mlib.commands.annotations.CommandAlias("leave")
class LeaveCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.leave")
		.hasFaction()
		.build()
) {

	@CommandExecutor
	fun execute(fPlayer: FPlayer) {
		val faction = fPlayer.getFaction()
		if (faction.getMemberContainer().getRank(fPlayer) == Rank.LEADER) {
			Chat.tell(fPlayer.getPlayer(), "&cYou must promote someone or disband your faction to leave it.")
			return
		}

		faction.getMemberContainer().removeMember(fPlayer)
		fPlayer.setFaction(null)
		Chat.tell(fPlayer.getPlayer(), "&aYou have left your faction.")
	}

}