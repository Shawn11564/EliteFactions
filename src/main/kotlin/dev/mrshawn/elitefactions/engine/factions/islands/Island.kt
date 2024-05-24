package dev.mrshawn.elitefactions.engine.factions.islands

import org.bukkit.Location

class Island(
	private val centerLocation: Location
) {

	private var homeLocation: Location

	init {
		homeLocation = centerLocation.add(0.0, 1.0, 0.0)
	}

	fun setHomeLocation(location: Location) {
		homeLocation = location
	}

	fun getHomeLocation(): Location {
		return homeLocation
	}

	fun inBounds(location: Location): Boolean {
		return location.x >= centerLocation.x - 50 && location.x <= centerLocation.x + 50
				&& location.z >= centerLocation.z - 50 && location.z <= centerLocation.z + 50
	}

}