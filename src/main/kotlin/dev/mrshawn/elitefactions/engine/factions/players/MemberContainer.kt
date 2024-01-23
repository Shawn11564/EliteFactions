package dev.mrshawn.elitefactions.engine.factions.players

import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import org.bukkit.Bukkit
import java.util.*

class MemberContainer {

	private val members = HashMap<UUID, Rank>()

	fun getLeader(): UUID {
		return members.filter { it.value == Rank.LEADER }.keys.first()
	}

	fun isMember(uuid: UUID): Boolean {
		return members.contains(uuid)
	}

	fun addMember(uuid: UUID, rank: Rank) {
		members[uuid] = rank
	}

	fun addMember(fPlayer: FPlayer) {
		members[fPlayer.getUUID()] = Rank.MEMBER
	}

	fun addMember(fPlayer: FPlayer, rank: Rank) {
		members[fPlayer.getUUID()] = rank
	}

	fun removeMember(uuid: UUID) {
		members.remove(uuid)
	}

	fun getRank(uuid: UUID): Rank? {
		return members[uuid]
	}

	fun getRank(fPlayer: FPlayer): Rank? {
		return members[fPlayer.getUUID()]
	}

	fun setRank(uuid: UUID, rank: Rank) {
		members[uuid] = rank
	}

	fun getMembers(): List<UUID> {
		return members.keys.toList()
	}

	fun getOnlineMembers(): List<UUID> {
		return members.keys.filter { Bukkit.getPlayer(it) != null }
	}

	fun getMembersWithRanks(): Map<UUID, Rank> {
		return members
	}

}