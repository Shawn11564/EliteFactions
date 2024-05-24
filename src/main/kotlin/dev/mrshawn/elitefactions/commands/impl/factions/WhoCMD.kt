package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.getPlayerName
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandCompletion
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("who")
class WhoCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.who")
	)
) {

	@CommandCompletion("@players")
	@CommandExecutor
	fun execute(sender: CommandSender, args: Array<String>) {
		if (args.isEmpty() && sender !is Player) {
			Chat.tell(sender, EMessages.CMD_WHO_USAGE)
			return
		}

		val player = if (args.isEmpty()) {
			sender as Player
		} else {
			Bukkit.getPlayer(args[0])
		}

		if (player == null) {
			Chat.tell(sender, EMessages.CMD_WHO_USAGE)
			return
		}

		val faction = FPlayer.get(player).getFaction()

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