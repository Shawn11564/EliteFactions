package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("description|desc")
class DescriptionCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.description")
		.hasPermissible(PermissibleAction.CHANGE_DESCRIPTION)
		.hasFaction(true)
		.build()
) {

	override fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_DESCRIPTION_USAGE)
			return
		}

		val faction = FPlayer.get(sender).getFaction()
		faction.changeDescription(args.joinToString(" "))
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_DESCRIPTION_CHANGED, sender.name)
	}

}