package dev.mrshawn.elitefactions.engine.factions.islands.generator.impl

import dev.mrshawn.elitefactions.engine.factions.islands.Island
import dev.mrshawn.elitefactions.engine.factions.islands.generator.IslandGenerator
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

class SimpleIslandGenerator(
	private val world: World
): IslandGenerator {

	companion object {
		private const val ISLAND_RADIUS = 50
		private const val ISLAND_DISTANCE_X = 200
		private const val ISLAND_DISTANCE_Z = 0
		private const val DEFAULT_Y = 100.0
	}

	private var lastLocation: Location? = null

	override fun generateIsland(centerLocation: Location): Island {
		lastLocation = centerLocation
		for (x in -ISLAND_RADIUS..ISLAND_RADIUS) {
			for (z in -ISLAND_RADIUS..ISLAND_RADIUS) {
				val blockLocation = Location(centerLocation.world, centerLocation.x + x, centerLocation.y, centerLocation.z + z)
				if (blockLocation.distance(centerLocation) > ISLAND_RADIUS) continue
				blockLocation.block.type = Material.GRASS_BLOCK
			}
		}
		return Island(centerLocation)
	}

	override fun getFreeLocation(): Location {
		return if (lastLocation == null) {
			Location(world, 0 + ISLAND_DISTANCE_X.toDouble(), DEFAULT_Y, 0 + ISLAND_DISTANCE_X.toDouble())
		} else {
			Location(world, lastLocation!!.x + ISLAND_DISTANCE_X.toDouble(), DEFAULT_Y, lastLocation!!.z + ISLAND_DISTANCE_Z.toDouble())
		}
	}

}