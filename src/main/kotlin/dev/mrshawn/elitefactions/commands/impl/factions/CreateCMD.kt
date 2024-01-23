package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("create")
class CreateCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.create")
		.isPlayer(true)
		.notInFaction(true)
		.build()
) {

	override fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_CREATE_USAGE)
			return
		}

		if (FactionManager.isFaction(args[0])) {
			Chat.tell(sender, EMessages.CMD_ERROR_FACTION_ALREADY_EXISTS)
			return
		}

		val faction = Faction.Factory()
			.creator(FPlayer.get(sender))
			.id(UUID.randomUUID())
			.name(args[0])
			.build()

		if (faction == null) {
			Chat.tell(sender, "&cThere was an error creating your faction. Please try again.")
			return
		}

		Chat.tell(sender, EMessages.CMD_CREATE_MESSAGE, args[0])
	}

}