package dev.mrshawn.elitefactions.engine.factions

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.MemberContainer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.engine.factions.players.ranks.RankContainer
import dev.mrshawn.elitefactions.engine.factions.server.factions.ServerFactions
import dev.mrshawn.elitefactions.files.CValues
import dev.mrshawn.elitefactions.files.ConfigFile
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import java.util.*

class Faction(
	private val id: UUID,
	private var name: String,
	private var description: String,
	private var power: Double,
	private var isOpen: Boolean = false,
	private val memberContainer: MemberContainer,
	private val rankContainer: RankContainer
) {

	fun rename(name: String) {
		this.name = name
		FactionManager.updateName(this, name)
	}

	fun changeDescription(description: String) {
		this.description = description
	}

	fun isServerFaction(): Boolean {
		return     id == ServerFactions.SAFEZONE_UUID
				|| id == ServerFactions.WARZONE_UUID
				|| id == ServerFactions.WILDERNESS_UUID
	}

	fun getId(): UUID {
		return id
	}

	fun getName(): String {
		return name
	}

	fun getDescription(): String {
		return description
	}

	fun getPower(): Double {
		return power
	}

	fun getMaxPower(): Double {
		return memberContainer.getMembers().size * ConfigFile.getDouble(CValues.FACTION_POWER_PER_PLAYER)!!
	}

	fun setOpen(isOpen: Boolean) {
		this.isOpen = isOpen
	}

	fun isOpen(): Boolean {
		return isOpen
	}

	fun getMemberContainer(): MemberContainer {
		return memberContainer
	}

	fun getRankContainer(): RankContainer {
		return rankContainer
	}

	class Factory {

		private var creator: FPlayer? = null
		private var id: UUID? = null
		private var name: String? = null

		fun creator(creator: FPlayer) = apply { this.creator = creator }
		fun id(id: UUID) = apply { this.id = id }
		fun name(name: String) = apply { this.name = name }

		fun build(register: Boolean = true): Faction? {
			if (name == null) return null
			val faction = Faction(
				id ?: UUID.randomUUID(),
				name!!,
				MessagesFile.getString(EMessages.FACTIONS_DEFAULT_DESCRIPTION)!!,
				creator!!.getPower(),
				false,
				MemberContainer().apply { addMember(creator!!, Rank.LEADER) },
				RankContainer.create()
			)
			if (register) FactionManager.addFaction(faction)
			creator!!.setFaction(faction)
			return faction
		}

	}

}