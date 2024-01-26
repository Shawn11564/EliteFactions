package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.getPlayerName
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("info")
class InfoCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands.info")
) {

	@CommandCompletion("@factions")
	override fun execute(sender: CommandSender, args: Array<String>) {
		if (args.isEmpty() && sender !is Player) {
			Chat.tell(sender, EMessages.CMD_INFO_USAGE)
			return
		}

		val faction = if (args.isEmpty()) {
			FPlayer.get(sender)?.getFaction()
		} else {
			FactionManager.getFaction(args[0])
		}

		// faction can only be null if args are provided
		if (faction == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_FACTION_DOESNT_EXIST, if (args.isEmpty()) "null" else args[0])
			return
		}

		if (faction.isServerFaction()) {
			Chat.tell(sender, EMessages.CMD_SERVER_FACTION_INFO_MESSAGE, faction.getName(), faction.getDescription())
			return
		}

		Chat.tell(sender,
			EMessages.CMD_FACTION_INFO_MESSAGE,
			faction.getName(),
			faction.getDescription(),
			faction.getMemberContainer().getLeader().getPlayerName(),
			faction.getMemberContainer().getMembers().size,
			10,
			faction.getMemberContainer().getOnlineMembers().size,
			faction.getPower(),
			faction.getMaxPower(),
			faction.isOpen()
		)
	}

}