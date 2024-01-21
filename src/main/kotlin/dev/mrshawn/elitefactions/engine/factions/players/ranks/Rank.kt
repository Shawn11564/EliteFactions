package dev.mrshawn.elitefactions.engine.factions.players.ranks

import org.bukkit.ChatColor

abstract class Rank(
	private var name: String,
	private var color: ChatColor,
	private var permissions: ArrayList<PermissibleAction>
) {

	fun hasPermission(action: PermissibleAction): Boolean {
		return permissions.contains(action)
	}

	fun getName(): String {
		return name
	}

	fun getColor(): ChatColor {
		return color
	}

	fun getPermissions(): List<PermissibleAction> {
		return permissions
	}

	fun addPermission(action: PermissibleAction) {
		permissions.add(action)
	}

	fun removePermission(action: PermissibleAction) {
		permissions.remove(action)
	}

}