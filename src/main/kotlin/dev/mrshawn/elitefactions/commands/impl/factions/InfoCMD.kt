package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.annotations.Optional
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.getPlayerName
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("info")
class InfoCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.info")
	)
) {

	@CommandCompletion("@factions")
	@CommandExecutor
	fun execute(sender: CommandSender, @Optional faction: Faction?) {
		if (faction == null && sender !is Player) {
			Chat.tell(sender, EMessages.CMD_INFO_USAGE)
			return
		}

		val targetFaction = faction ?: FPlayer.get(sender)?.getFaction()

		if (targetFaction == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_FACTION_DOESNT_EXIST)
			return
		}

		if (targetFaction.isServerFaction()) {
			Chat.tell(sender, EMessages.CMD_SERVER_FACTION_INFO_MESSAGE, targetFaction.getName(), targetFaction.getDescription())
			return
		}

		Chat.tell(sender,
			EMessages.CMD_FACTION_INFO_MESSAGE,
			targetFaction.getName(),
			targetFaction.getDescription(),
			targetFaction.getMemberContainer().getLeader().getPlayerName(),
			targetFaction.getMemberContainer().getMembers().size,
			10,
			targetFaction.getMemberContainer().getOnlineMembers().size,
			targetFaction.getPower(),
			targetFaction.getMaxPower(),
			targetFaction.isOpen()
		)
	}

	override fun getUsageMessage(): String {
		return EMessages.CMD_INFO_USAGE.getMessage()
	}

}