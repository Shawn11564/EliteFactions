package dev.mrshawn.elitefactions.extensions

import dev.mrshawn.elitefactions.EliteFactions
import org.bukkit.Bukkit

fun (() -> Unit).runLater(delay: Long): Int {
	return Bukkit.getScheduler().runTaskLater(
		EliteFactions.instance,
		this,
		delay
	).taskId
}