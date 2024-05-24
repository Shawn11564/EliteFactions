package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.event.player.PlayerTeleportEvent

@CommandAlias("home")
class HomeCMD: MCommand(
	Precondition.Builder()
		.isPlayer()
		.hasFaction()
		.hasPermission("elitefactions.commands.home")
		.build()
) {

	@CommandExecutor
	fun execute(player: FPlayer) {
		if (!player.getFaction().hasIsland()) {
			Chat.tell(player, EMessages.CMD_ERROR_NO_ISLAND)
			return
		}

		player.getPlayer()?.teleport(player.getFaction().getIsland()!!.getHomeLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN)
		Chat.tell(player, EMessages.CMD_HOME_TELEPORTED_MESSAGE)
	}

}