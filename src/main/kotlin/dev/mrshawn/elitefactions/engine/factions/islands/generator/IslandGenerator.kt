package dev.mrshawn.elitefactions.engine.factions.islands.generator

import dev.mrshawn.elitefactions.engine.factions.islands.Island
import dev.mrshawn.elitefactions.engine.factions.islands.generator.impl.SimpleIslandGenerator
import dev.mrshawn.elitefactions.engine.factions.islands.worlds.BlankWorldCreator
import org.bukkit.Bukkit
import org.bukkit.Location

interface IslandGenerator {

	companion object {
		private val generator = SimpleIslandGenerator(
			Bukkit.getWorld("fislands") ?: BlankWorldCreator.generateEmptyWorld("fislands")
		)

		fun getImpl(): IslandGenerator = generator
	}

	fun generateIsland(centerLocation: Location): Island

	fun getFreeLocation(): Location

}