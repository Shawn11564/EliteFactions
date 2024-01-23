package dev.mrshawn.elitefactions.files

import dev.mrshawn.elitefactions.EliteFactions
import dev.mrshawn.elitefactions.files.struct.PathEnum
import dev.mrshawn.elitefactions.files.struct.impl.YamlFile
import java.io.File

object MessagesFile: YamlFile(
	File(EliteFactions.instance.dataFolder, "messages.yml"),
	true
)

enum class EMessages(
	private val path: String
): PathEnum {

	FACTIONS_DEFAULT_DESCRIPTION("factions.default-description"),

	FACTIONS_PERMISSIONS_NO_PERMISSION("factions.permissions.no-permission"),

	FACTIONS_ALERTS_FACTION_DISBANDED("factions.alerts.faction-disbanded"),

	CMD_HELP_MESSAGE("commands.messages.help"),
	CMD_FACTION_INFO_MESSAGE("commands.messages.faction-info"),
	CMD_CREATE_MESSAGE("commands.messages.create"),
	CMD_DISBAND_MESSAGE("commands.messages.disband"),
	CMD_TOP_MESSAGE_LIST("commands.messages.top.list"),
	CMD_TOP_MESSAGE_ENTRY("commands.messages.top.entry"),

	CMD_CREATE_USAGE("commands.usage.create"),
	CMD_DISBAND_USAGE("commands.usage.disband"),
	CMD_INFO_USAGE("commands.usage.info"),

	CMD_ERROR_FACTION_DOESNT_EXIST("commands.error.faction-doesnt-exist"),
	CMD_ERROR_FACTION_ALREADY_EXISTS("commands.error.faction-already-exists"),
	CMD_ERROR_NOT_IN_FACTION("commands.error.not-in-faction"),

	PRECONDITIONS_SUCCESS("preconditions.success"),
	PRECONDITIONS_ERROR_NO_PERMISSION("preconditions.error.no-permission"),
	PRECONDITIONS_ERROR_NOT_PLAYER("preconditions.error.not-player"),
	PRECONDITIONS_ERROR_IN_FACTION("preconditions.error.in-faction"),
	PRECONDITIONS_ERROR_NOT_IN_FACTION("preconditions.error.not-in-faction");


	override fun getPath(): String {
		return path
	}

}