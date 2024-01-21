package dev.mrshawn.elitefactions.engine.factions.players.ranks

enum class PermissibleAction(
	val displayName: String
) {

	DISBAND("disband"),
	INVITE("invite"),
	KICK("kick"),
	OPEN("open"),
	PROMOTE("promote"),
	DEMOTE("demote"),
	RENAME("rename"),
	CLAIM("claim"),
	UNCLAIM("unclaim");

}