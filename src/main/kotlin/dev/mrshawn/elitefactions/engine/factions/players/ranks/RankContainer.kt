package dev.mrshawn.elitefactions.engine.factions.players.ranks

class RankContainer {

	private val ranks = HashMap<Rank, List<PermissibleAction>>()

	companion object {
		fun create(): RankContainer {
			return RankContainer().apply {
				Rank.entries.forEach { ranks[it] = it.getDefaultPermissions() }
			}
		}
	}

	fun hasPermission(rank: Rank, action: PermissibleAction): Boolean {
		val permissions = ranks[rank] ?: return false
		return permissions.contains(PermissibleAction.ALL) || permissions.contains(action)
	}

	fun getPermissions(rank: Rank): List<PermissibleAction> {
		return ranks[rank] ?: emptyList()
	}

	fun addPermissions(rank: Rank, vararg actions: PermissibleAction) {
		ranks[rank] = actions.toList()
	}

	fun removePermissions(rank: Rank, vararg actions: PermissibleAction) {
		ranks[rank] = ranks[rank]?.filter { !actions.contains(it) } ?: emptyList()
	}

	fun clearPermissions(rank: Rank) {
		ranks[rank] = emptyList()
	}

}