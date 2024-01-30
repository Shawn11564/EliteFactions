package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("open")
class OpenCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.open")
		.hasPermissible(PermissibleAction.OPEN)
		.hasFaction(true)
		.build()
) {

	@CommandCompletion("@boolean")
	@CommandExecutor
	fun execute(sender: Player, args: Array<String>) {
		val faction = FPlayer.get(sender).getFaction()
		val open = if (args.isEmpty()) !faction.isOpen() else args[0].lowercase().toBooleanStrictOrNull()

		if (open == null) {
			Chat.tell(sender, EMessages.CMD_OPEN_USAGE)
			return
		}

		faction.setOpen(open)
		if (open) {
			Chat.tell(sender, EMessages.CMD_OPEN_OPENED_MESSAGE)
			Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_OPENED, sender.name)
		} else {
			Chat.tell(sender, EMessages.CMD_OPEN_CLOSED_MESSAGE)
			Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_CLOSED, sender.name)
		}
	}

}