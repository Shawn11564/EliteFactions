package dev.mrshawn.elitefactions.engine.factions

import dev.mrshawn.elitefactions.engine.factions.players.MemberContainer
import java.util.*

class Faction(
	private val id: UUID,
	private val name: String,
	private val memberContainer: MemberContainer
) {

	fun getMemberContainer(): MemberContainer {
		return MemberContainer()
	}

	fun getId(): UUID {
		return id
	}

	fun getName(): String {
		return name
	}

}