package dev.mrshawn.elitefactions.extensions

import org.bukkit.permissions.Permissible

fun Permissible.hasPermission(permission: String?): Boolean {
	if (permission == null) return true
	return hasPermission(permission)
}