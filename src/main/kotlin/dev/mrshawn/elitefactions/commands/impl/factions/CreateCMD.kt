package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat

@CommandAlias("create")
class CreateCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.create")
		.isPlayer(true)
		.notInFaction(true)
		.build()
) {

	@CommandExecutor
	fun execute(fPlayer: FPlayer, factionName: String) {
		if (FactionManager.isFaction(factionName)) {
			Chat.tell(fPlayer, EMessages.CMD_ERROR_FACTION_ALREADY_EXISTS)
			return
		}

		val faction = Faction.Factory()
			.creator(fPlayer)
			.name(factionName)
			.build()

		if (faction == null) {
			Chat.tell(fPlayer.getPlayer(), "&cThere was an error creating your faction. Please try again.")
			return
		}

		Chat.tell(fPlayer, EMessages.CMD_CREATE_MESSAGE, factionName)
	}

	override fun getUsageMessage(): String {
		return EMessages.CMD_CREATE_USAGE.getMessage()
	}

}