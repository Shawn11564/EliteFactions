package dev.mrshawn.elitefactions.files

import dev.mrshawn.elitefactions.EliteFactions
import dev.mrshawn.elitefactions.files.struct.PathEnum
import dev.mrshawn.elitefactions.files.struct.impl.YamlFile
import java.io.File

object ConfigFile: YamlFile(
	File(EliteFactions.instance.dataFolder, "config.yml"),
	true
)

enum class CValues(
	private val path: String
): PathEnum {

	FACTION_POWER_PER_PLAYER("factions.power.per-player"),
	FACTION_POWER_STARTING("factions.power.starting"),
	FACTION_POWER_MIN("factions.power.min"),
	FACTION_POWER_REGEN_ENABLED("factions.power.regen.enabled"),
	FACTION_POWER_REGEN_AMOUNT("factions.power.regen.amount"),
	FACTION_POWER_REGEN_INTERVAL("factions.power.regen.interval"),

	FACTION_TOP_TRIM_EMPTY("factions.top.trim-empty"),

	FACTION_INVITE_TIMEOUT("factions.invite.timeout"),

	FACTION_RESPAWN_AT_HOME("factions.respawn-at-home");

	override fun getPath(): String {
		return path
	}

}
