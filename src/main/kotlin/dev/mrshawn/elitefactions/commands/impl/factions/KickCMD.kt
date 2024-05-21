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
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.entity.Player

@dev.mrshawn.mlib.commands.annotations.CommandAlias("kick")
class KickCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.kick")
		.hasPermissible(PermissibleAction.KICK)
		.hasFaction()
		.build()
) {

	@CommandCompletion("@faction-members")
	@CommandExecutor
	fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_KICK_USAGE)
			return
		}

		val faction = FPlayer.get(sender).getFaction()
		val memberContainer = faction.getMemberContainer()
		val target = FPlayer.get(args[0])

		if (target == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_PLAYER_NOT_FOUND)
			return
		}

		if (!memberContainer.isMember(target)) {
			Chat.tell(sender, EMessages.CMD_ERROR_NOT_MEMBER)
			return
		}

		if (!memberContainer.getRank(sender).isAbove(memberContainer.getRank(target))) {
			Chat.tell(sender, EMessages.CMD_ERROR_TOO_LOW_TO_KICK)
			return
		}

		faction.getMemberContainer().removeMember(target)
		target.setFaction(null)
		Chat.tell(sender, EMessages.CMD_KICK_SENDER_MESSAGE, target)
		Chat.tell(target, EMessages.CMD_KICK_TARGET_MESSAGE, faction.getName())
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_PLAYER_KICKED, target.getPlayerName(), sender.name)
	}

}