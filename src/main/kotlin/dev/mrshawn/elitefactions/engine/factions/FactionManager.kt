package dev.mrshawn.elitefactions.engine.factions

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import java.util.*

object FactionManager {

	private val factions = HashMap<UUID, Faction>()
	private val factionNameMap = HashMap<String, Faction>()

	fun updateName(faction: Faction, name: String) {
		factionNameMap.remove(faction.getName())
		factionNameMap[name] = faction
	}

	fun disbandFaction(faction: Faction) {
		faction.getMemberContainer().getMembers().forEach { member ->
			val fPlayer = FPlayer.get(member)
			fPlayer.setFaction(null)
		}
		removeFaction(faction)
	}

	fun isFaction(id: UUID): Boolean {
		return factions.containsKey(id)
	}

	fun isFaction(name: String): Boolean {
		return factionNameMap.containsKey(name)
	}

	fun getFaction(id: UUID): Faction? {
		return factions[id]
	}

	fun getFaction(name: String): Faction? {
		return factionNameMap[name]
	}

	fun addFaction(faction: Faction) {
		factions[faction.getId()] = faction
		factionNameMap[faction.getName()] = faction
	}

	fun removeFaction(faction: Faction) {
		factions.remove(faction.getId())
		factionNameMap.remove(faction.getName())
	}

	fun removeFaction(id: UUID) {
		val faction = factions[id] ?: return
		factions.remove(id)
		factionNameMap.remove(faction.getName())
	}

	fun removeFaction(name: String) {
		val faction = factionNameMap[name] ?: return
		factions.remove(faction.getId())
		factionNameMap.remove(name)
	}

	fun getFactions(): Collection<Faction> {
		return factions.values
	}

	fun getFactionNames(): Collection<String> {
		return factionNameMap.keys
	}

}