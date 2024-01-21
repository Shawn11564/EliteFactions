package dev.mrshawn.elitefactions.engine.factions.players

import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import java.util.*

class MemberContainer {

	private val members = HashMap<UUID, Rank>()

	fun isMember(uuid: UUID): Boolean {
		return members.contains(uuid)
	}

	fun addMember(uuid: UUID, rank: Rank) {
		members[uuid] = rank
	}

	fun removeMember(uuid: UUID) {
		members.remove(uuid)
	}

	fun getRank(uuid: UUID): Rank? {
		return members[uuid]
	}

	fun setRank(uuid: UUID, rank: Rank) {
		members[uuid] = rank
	}

}