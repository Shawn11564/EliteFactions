package dev.mrshawn.elitefactions.engine.factions.players.ranks

enum class Rank(
	private val displayName: String,
	private val defaultPermissions: List<PermissibleAction>,
	private val weight: Int
) {

	LEADER("Leader", listOf(
		PermissibleAction.ALL
	), 4),
	COLEADER("Co-Leader", listOf(
		PermissibleAction.INVITE,
		PermissibleAction.KICK,
		PermissibleAction.OPEN,
		PermissibleAction.PROMOTE,
		PermissibleAction.DEMOTE,
		PermissibleAction.CLAIM,
		PermissibleAction.UNCLAIM
	), 3),
	OFFICER("Officer", listOf(
		PermissibleAction.INVITE,
		PermissibleAction.KICK,
		PermissibleAction.CLAIM,
		PermissibleAction.UNCLAIM
	), 2),
	MEMBER("Member", emptyList(), 1),
	GUEST("Guest", emptyList(), 0);

	fun getDisplayName(): String {
		return name
	}

	fun getDefaultPermissions(): List<PermissibleAction> {
		return defaultPermissions
	}

	fun isAbove(rank: Rank): Boolean {
		return weight > rank.weight
	}

	fun isAtOrAbove(rank: Rank): Boolean {
		return weight >= rank.weight
	}

	fun isBelow(rank: Rank): Boolean {
		return weight < rank.weight
	}

	fun isAtOrBelow(rank: Rank): Boolean {
		return weight <= rank.weight
	}

}