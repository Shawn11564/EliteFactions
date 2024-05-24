package dev.mrshawn.elitefactions.engine.listeners

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.files.CValues
import dev.mrshawn.elitefactions.files.ConfigFile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawnListener: Listener {

	@EventHandler
	fun onRespawn(event: PlayerRespawnEvent) {
		val player = FPlayer.get(event.player)
		if (ConfigFile.getBoolean(CValues.FACTION_RESPAWN_AT_HOME)!! && player.getFaction().hasIsland()) {
			event.respawnLocation = player.getFaction().getIsland()!!.getHomeLocation()
		}
	}

}