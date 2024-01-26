package dev.mrshawn.elitefactions.extensions

import org.bukkit.Bukkit

fun Int?.cancelTask() {
	if (this == null) return
	Bukkit.getScheduler().cancelTask(this)
}