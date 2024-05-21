package dev.mrshawn.elitefactions.engine.factions.players

import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.extensions.cancelTask
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class MemberContainer {

	private val members = HashMap<UUID, Rank>()
	private val invited = HashMap<UUID, Int>()

	fun invite(uuid: UUID, taskID: Int) {
		invited[uuid] = taskID
	}

	fun uninvite(uuid: UUID) {
		invited.remove(uuid).cancelTask()
	}

	fun isInvited(uuid: UUID): Boolean {
		return invited.contains(uuid)
	}

	fun getInvited(): List<UUID> {
		return invited.keys.toList()
	}

	fun getLeader(): UUID {
		return members.filter { it.value == Rank.LEADER }.keys.first()
	}

	fun isMember(uuid: UUID): Boolean {
		return members.contains(uuid)
	}

	fun isMember(fPlayer: FPlayer): Boolean {
		return members.contains(fPlayer.getPlayerUUID())
	}

	fun addMember(uuid: UUID, rank: Rank) {
		members[uuid] = rank
	}

	fun addMember(fPlayer: FPlayer) {
		members[fPlayer.getPlayerUUID()] = Rank.MEMBER
	}

	fun addMember(fPlayer: FPlayer, rank: Rank) {
		members[fPlayer.getPlayerUUID()] = rank
	}

	fun removeMember(uuid: UUID) {
		members.remove(uuid)
	}

	fun removeMember(fPlayer: FPlayer) {
		members.remove(fPlayer.getPlayerUUID())
	}

	fun getRank(uuid: UUID): Rank {
		return members[uuid] ?: Rank.GUEST
	}

	fun getRank(player: Player): Rank {
		return getRank(player.uniqueId)
	}

	fun getRank(fPlayer: FPlayer): Rank {
		return getRank(fPlayer.getPlayerUUID())
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