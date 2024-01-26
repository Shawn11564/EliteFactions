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
	FACTIONS_SAFEZONE_NAME("factions.server-factions.safezone.name"),
	FACTIONS_SAFEZONE_DESCRIPTION("factions.server-factions.safezone.description"),
	FACTIONS_WARZONE_NAME("factions.server-factions.warzone.name"),
	FACTIONS_WARZONE_DESCRIPTION("factions.server-factions.warzone.description"),
	FACTIONS_WILDERNESS_NAME("factions.server-factions.wilderness.name"),
	FACTIONS_WILDERNESS_DESCRIPTION("factions.server-factions.wilderness.description"),

	FACTIONS_PERMISSIONS_NO_PERMISSION("factions.permissions.no-permission"),

	FACTIONS_ALERTS_FACTION_DISBANDED("factions.alerts.faction-disbanded"),
	FACTIONS_ALERTS_FACTION_RENAMED("factions.alerts.faction-renamed"),
	FACTIONS_ALERTS_DESCRIPTION_CHANGED("factions.alerts.description-changed"),
	FACTIONS_ALERTS_PLAYER_INVITED("factions.alerts.player-invited"),
	FACTIONS_ALERTS_PLAYER_UNINVITED("factions.alerts.player-uninvited"),
	FACTIONS_ALERTS_PLAYER_JOINED("factions.alerts.player-joined"),
	FACTIONS_ALERTS_FACTION_OPENED("factions.alerts.faction-opened"),
	FACTIONS_ALERTS_FACTION_CLOSED("factions.alerts.faction-closed"),
	FACTIONS_ALERTS_PLAYER_KICKED("factions.alerts.player-kicked"),

	CMD_HELP_MESSAGE("commands.messages.help"),
	CMD_FACTION_INFO_MESSAGE("commands.messages.faction-info"),
	CMD_SERVER_FACTION_INFO_MESSAGE("commands.messages.server-faction-info"),
	CMD_CREATE_MESSAGE("commands.messages.create"),
	CMD_DISBAND_MESSAGE("commands.messages.disband"),
	CMD_TOP_MESSAGE_LIST("commands.messages.top.list"),
	CMD_TOP_MESSAGE_ENTRY("commands.messages.top.entry"),
	CMD_INVITE_SENT_MESSAGE("commands.messages.invite.sent"),
	CMD_INVITE_RECEIVED_MESSAGE("commands.messages.invite.received"),
	CMD_INVITE_TIMEOUT_MESSAGE("commands.messages.invite.timeout"),
	CMD_UNINVITE_SENDER_MESSAGE("commands.messages.uninvite.sender"),
	CMD_UNINVITE_TARGET_MESSAGE("commands.messages.uninvite.target"),
	CMD_JOIN_MESSAGE("commands.messages.join"),
	CMD_OPEN_OPENED_MESSAGE("commands.messages.open.opened"),
	CMD_OPEN_CLOSED_MESSAGE("commands.messages.open.closed"),
	CMD_KICK_SENDER_MESSAGE("commands.messages.kick.sender"),
	CMD_KICK_TARGET_MESSAGE("commands.messages.kick.target"),

	CMD_CREATE_USAGE("commands.usage.create"),
	CMD_DISBAND_USAGE("commands.usage.disband"),
	CMD_INFO_USAGE("commands.usage.info"),
	CMD_WHO_USAGE("commands.usage.who"),
	CMD_RENAME_USAGE("commands.usage.rename"),
	CMD_DESCRIPTION_USAGE("commands.usage.description"),
	CMD_INVITE_USAGE("commands.usage.invite"),
	CMD_UNINVITE_USAGE("commands.usage.uninvite"),
	CMD_JOIN_USAGE("commands.usage.join"),
	CMD_OPEN_USAGE("commands.usage.open"),
	CMD_KICK_USAGE("commands.usage.kick"),

	CMD_ERROR_PLAYER_NOT_FOUND("commands.error.player-not-found"),
	CMD_ERROR_NOT_A_PLAYER("commands.error.not-a-player"),
	CMD_ERROR_FACTION_DOESNT_EXIST("commands.error.faction-doesnt-exist"),
	CMD_ERROR_FACTION_ALREADY_EXISTS("commands.error.faction-already-exists"),
	CMD_ERROR_NOT_IN_FACTION("commands.error.not-in-faction"),
	CMD_ERROR_TARGET_IN_FACTION("commands.error.target-in-faction"),
	CMD_ERROR_ALREADY_INVITED("commands.error.already-invited"),
	CMD_ERROR_TARGET_NOT_INVITED("commands.error.target-not-invited"),
	CMD_ERROR_NOT_INVITED("commands.error.not-invited"),
	CMD_ERROR_NOT_MEMBER("commands.error.not-member"),
	CMD_ERROR_TOO_LOW_TO_KICK("commands.error.too-low-to-kick"),

	CMD_ADMIN_RELOAD_MESSAGE("commands.admin.messages.reload"),

	PRECONDITIONS_SUCCESS("preconditions.success"),
	PRECONDITIONS_ERROR_NO_PERMISSION("preconditions.error.no-permission"),
	PRECONDITIONS_ERROR_NOT_PLAYER("preconditions.error.not-player"),
	PRECONDITIONS_ERROR_IN_FACTION("preconditions.error.in-faction"),
	PRECONDITIONS_ERROR_NOT_IN_FACTION("preconditions.error.not-in-faction");


	override fun getPath(): String {
		return path
	}

}