package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("kick")
class KickCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.kick")
		.hasPermissible(PermissibleAction.KICK)
		.hasFaction(true)
		.build()
) {

	@CommandCompletion("@faction-members")
	override fun execute(sender: Player, args: Array<String>) {
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

		if ((memberContainer.getRank(sender) ?: Rank.GUEST) <= (memberContainer.getRank(target) ?: Rank.GUEST)) {
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