package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.entity.Player

@CommandAlias("open")
class OpenCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.open")
		.hasPermissible(PermissibleAction.OPEN)
		.hasFaction()
		.build()
) {

	@CommandCompletion("@boolean")
	@CommandExecutor
	fun execute(sender: Player, open: Boolean?) {
		val faction = FPlayer.get(sender).getFaction()
		val newState = open ?: !faction.isOpen()

		faction.setOpen(newState)
		if (newState) {
			Chat.tell(sender, EMessages.CMD_OPEN_OPENED_MESSAGE)
			Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_OPENED, sender.name)
		} else {
			Chat.tell(sender, EMessages.CMD_OPEN_CLOSED_MESSAGE)
			Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_CLOSED, sender.name)
		}
	}

}