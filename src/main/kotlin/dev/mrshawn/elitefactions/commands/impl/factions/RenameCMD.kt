package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.entity.Player

@CommandAlias("rename")
class RenameCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.rename")
		.hasPermissible(PermissibleAction.RENAME)
		.hasFaction()
		.build()
) {

	@CommandExecutor
	fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_RENAME_USAGE)
			return
		}

		if (FactionManager.isFaction(args[0])) {
			Chat.tell(sender, EMessages.CMD_ERROR_FACTION_ALREADY_EXISTS)
			return
		}

		val faction = FPlayer.get(sender).getFaction()
		faction.rename(args[0])
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_FACTION_RENAMED, args[0], sender.name)
	}

}