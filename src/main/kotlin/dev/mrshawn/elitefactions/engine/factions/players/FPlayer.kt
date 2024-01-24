package dev.mrshawn.elitefactions.engine.factions.players

import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.server.factions.ServerFactions
import dev.mrshawn.elitefactions.files.CValues
import dev.mrshawn.elitefactions.files.ConfigFile
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class FPlayer private constructor(
	private val playerUUID: UUID
) {

	private var cachedPlayer: Player? = null
	private var power: Double = ConfigFile.getDouble(CValues.FACTION_POWER_STARTING) ?: 10.0
	private var faction: Faction? = null

	init {
		cachedPlayer = Bukkit.getPlayer(playerUUID)
	}

	companion object {

		private val cache = HashMap<UUID, FPlayer>()

		fun get(uuid: UUID): FPlayer {
			if (!Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()) throw RuntimeException("Player with UUID $uuid has never played before!")
			if (!cache.containsKey(uuid)) {
				cache[uuid] = FPlayer(uuid)
			}
			return cache[uuid]!!
		}

		fun get(player: Player): FPlayer {
			return get(player.uniqueId)
		}

		fun get(commandSender: CommandSender): FPlayer? {
			return if (commandSender is Player) {
				get(commandSender)
			} else {
				null
			}
		}

		fun remove(uuid: UUID) {
			cache.remove(uuid)
		}

		fun remove(player: Player) {
			remove(player.uniqueId)
		}

	}

	fun getFaction(): Faction {
		return faction ?: ServerFactions.WILDERNESS
	}

	fun setFaction(faction: Faction?) {
		this.faction = faction
	}

	fun getPower(): Double {
		return power
	}

	fun getPlayerName(): String {
		return cachedPlayer?.name ?: "null"
	}

	fun getUUID(): UUID {
		return playerUUID
	}

}