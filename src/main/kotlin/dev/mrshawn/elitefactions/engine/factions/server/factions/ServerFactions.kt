package dev.mrshawn.elitefactions.engine.factions.server.factions

import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.claims.ClaimContainer
import dev.mrshawn.elitefactions.engine.factions.players.MemberContainer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.engine.factions.players.ranks.RankContainer
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import java.util.*

object ServerFactions {

	val SAFEZONE_UUID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
	val WARZONE_UUID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000001")
	val WILDERNESS_UUID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000002")

	val SAFEZONE = Faction(
		SAFEZONE_UUID,
		MessagesFile.getString(EMessages.FACTIONS_SAFEZONE_NAME)!!,
		MessagesFile.getString(EMessages.FACTIONS_SAFEZONE_DESCRIPTION)!!,
		Double.MAX_VALUE,
		false,
		ClaimContainer(),
		MemberContainer(),
		RankContainer().apply { Rank.entries.forEach { clearPermissions(it) } }
	)
	val WARZONE = Faction(
		WARZONE_UUID,
		MessagesFile.getString(EMessages.FACTIONS_WARZONE_NAME)!!,
		MessagesFile.getString(EMessages.FACTIONS_WARZONE_DESCRIPTION)!!,
		Double.MAX_VALUE,
		false,
		ClaimContainer(),
		MemberContainer(),
		RankContainer().apply { Rank.entries.forEach { clearPermissions(it) } }
	)
	val WILDERNESS = Faction(
		WILDERNESS_UUID,
		MessagesFile.getString(EMessages.FACTIONS_WILDERNESS_NAME)!!,
		MessagesFile.getString(EMessages.FACTIONS_WILDERNESS_DESCRIPTION)!!,
		Double.MAX_VALUE,
		false,
		ClaimContainer(),
		MemberContainer(),
		RankContainer().apply { Rank.entries.forEach { clearPermissions(it) } }
	)

}