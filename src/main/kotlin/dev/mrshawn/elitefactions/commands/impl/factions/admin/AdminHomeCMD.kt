package dev.mrshawn.elitefactions.commands.impl.factions.admin

import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandCompletion
import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

@CommandAlias("home")
class AdminHomeCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.admin.home")
		.build()
) {

	@CommandCompletion("@factions")
	@CommandExecutor
	fun execute(player: Player, faction: Faction) {
		if (!faction.hasIsland()) {
			Chat.tell(player, EMessages.CMD_ADMIN_ERROR_NO_ISLAND)
			return
		}

		player.teleport(faction.getIsland()!!.getHomeLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN)
		Chat.tell(player, EMessages.CMD_ADMIN_HOME_MESSAGE, faction.getName())
	}

}