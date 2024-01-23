package dev.mrshawn.elitefactions.engine.factions

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.MemberContainer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.engine.factions.players.ranks.RankContainer
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
	private val memberContainer: MemberContainer,
	private val rankContainer: RankContainer
) {

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
			if (id == null || name == null) return null
			val faction = Faction(
				id!!,
				name!!,
				MessagesFile.getString(EMessages.FACTIONS_DEFAULT_DESCRIPTION)!!,
				creator!!.getPower(),
				MemberContainer().apply { addMember(creator!!, Rank.LEADER) },
				RankContainer.create()
			)
			if (register) FactionManager.addFaction(faction)
			creator!!.setFaction(faction)
			return faction
		}

	}

}