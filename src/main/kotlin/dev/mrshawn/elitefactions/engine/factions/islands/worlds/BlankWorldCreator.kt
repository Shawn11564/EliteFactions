package dev.mrshawn.elitefactions.engine.factions.islands.worlds

import dev.mrshawn.mlib.chat.Chat
import org.bukkit.*
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*

object BlankWorldCreator {

	fun exists(worldName: String): Boolean = Bukkit.getWorld(worldName) != null

	fun generateEmptyWorld(worldName: String): World {
		val worldCreator = WorldCreator(worldName)
			.type(WorldType.FLAT)
			.generator(object: ChunkGenerator() {
				override fun shouldGenerateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean { return false }
				override fun shouldGenerateMobs(): Boolean { return false }
				override fun shouldGenerateNoise(): Boolean { return false }
				override fun shouldGenerateSurface(): Boolean { return false }
				override fun shouldGenerateStructures(): Boolean { return false }
				override fun shouldGenerateDecorations(): Boolean { return false }
				override fun shouldGenerateBedrock(): Boolean { return false }
			})

		val world: World = worldCreator.createWorld() ?: throw IllegalStateException("Failed to create empty world.")

		world.setSpawnFlags(false, false)

		val spawnLocation = world.spawnLocation
		spawnLocation.y = 100.0
		world.spawnLocation = spawnLocation

		world.getBlockAt(
			spawnLocation.blockX,
			spawnLocation.blockY - 1,
			spawnLocation.blockZ
		).type = Material.BEDROCK

		Chat.log("Generated empty world: $worldName")
		return world
	}

}