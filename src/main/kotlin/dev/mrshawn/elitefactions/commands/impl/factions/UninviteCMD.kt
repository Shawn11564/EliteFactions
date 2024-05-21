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
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("uninvite")
class UninviteCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.uninvite")
		.hasPermissible(PermissibleAction.INVITE)
		.hasFaction(true)
		.build()
) {

	@CommandCompletion("@invited-players")
	@CommandExecutor
	fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_UNINVITE_USAGE)
			return
		}

		val target = Bukkit.getPlayer(args[0])
		if (target == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_NOT_A_PLAYER)
			return
		}

		val faction = FPlayer.get(sender).getFaction()
		val memberContainer = faction.getMemberContainer()

		if (!memberContainer.isInvited(target.uniqueId)) {
			Chat.tell(sender, EMessages.CMD_ERROR_TARGET_NOT_INVITED)
			return
		}

		memberContainer.uninvite(target.uniqueId)
		Chat.tell(sender, EMessages.CMD_UNINVITE_SENDER_MESSAGE, target.name)
		Chat.tell(target, EMessages.CMD_UNINVITE_TARGET_MESSAGE, faction.getName())
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_PLAYER_UNINVITED, target.name, sender.name)
	}

}