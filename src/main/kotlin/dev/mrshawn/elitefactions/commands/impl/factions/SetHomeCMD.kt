package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.mlib.commands.preconditions.Precondition

@CommandAlias("sethome")
class SetHomeCMD: MCommand(
	Precondition.Builder()
		.isPlayer()
		.hasFaction()
		.hasPermission("elitefactions.commands.sethome")
		.hasPermissible(PermissibleAction.SETHOME)
		.build()
) {

	@CommandExecutor
	fun execute(player: FPlayer) {
		if (!player.getFaction().hasIsland()) {
			Chat.tell(player, EMessages.CMD_ERROR_NO_ISLAND)
			return
		}
		if (!player.getFaction().getIsland()!!.inBounds(player.getPlayer()!!.location)) {
			Chat.tell(player, EMessages.CMD_ERROR_OUTSIDE_ISLAND)
			return
		}

		player.getFaction().getIsland()!!.setHomeLocation(player.getPlayer()!!.location)
		Chat.tell(player, EMessages.CMD_HOME_SET_MESSAGE)
		Chat.tell(player.getFaction(), EMessages.FACTIONS_ALERTS_HOME_SET, player.getPlayer()?.name.toString())
	}

}