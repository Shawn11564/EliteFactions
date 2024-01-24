package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("disband")
class DisbandCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.disband")
		.hasPermissible(PermissibleAction.DISBAND)
		.hasFaction(true)
		.build()
) {

	override fun execute(sender: Player, args: Array<String>) {
		val fPlayer = FPlayer.get(sender)
		val faction = fPlayer.getFaction()

		Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_DISBANDED, sender.name)
		FactionManager.disbandFaction(faction)
		Chat.tell(sender, EMessages.CMD_DISBAND_MESSAGE)
	}

}