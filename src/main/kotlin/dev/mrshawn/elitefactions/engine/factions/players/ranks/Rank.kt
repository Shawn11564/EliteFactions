package dev.mrshawn.elitefactions.engine.factions.players.ranks

enum class Rank(
	private val displayName: String,
	private val defaultPermissions: List<PermissibleAction>
) {

	LEADER("Leader", listOf(
		PermissibleAction.ALL
	)),
	COLEADER("Co-Leader", listOf(
		PermissibleAction.INVITE,
		PermissibleAction.KICK,
		PermissibleAction.OPEN,
		PermissibleAction.PROMOTE,
		PermissibleAction.DEMOTE,
		PermissibleAction.CLAIM,
		PermissibleAction.UNCLAIM
	)),
	OFFICER("Officer", listOf(
		PermissibleAction.INVITE,
		PermissibleAction.KICK,
		PermissibleAction.CLAIM,
		PermissibleAction.UNCLAIM
	)),
	MEMBER("Member", emptyList()),
	GUEST("Guest", emptyList());

	fun getDisplayName(): String {
		return name
	}

	fun getDefaultPermissions(): List<PermissibleAction> {
		return defaultPermissions
	}

}