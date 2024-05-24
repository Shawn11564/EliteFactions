package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasFaction
import dev.mrshawn.elitefactions.commands.enhancements.preconditions.hasPermissible
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.extensions.runLater
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.CValues
import dev.mrshawn.elitefactions.files.ConfigFile
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandCompletion
import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("invite")
class InviteCMD: MCommand(
	Precondition.Builder()
		.hasPermission("elitefactions.commands.invite")
		.hasPermissible(PermissibleAction.INVITE)
		.hasFaction(true)
		.build()
) {

	@CommandCompletion("@players")
	@CommandExecutor
	fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_INVITE_USAGE)
			return
		}

		val target = Bukkit.getPlayer(args[0])
		if (target == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_NOT_A_PLAYER)
			return
		}

		val targetFPlayer = FPlayer.get(target)
		if (targetFPlayer.hasFaction()) {
			Chat.tell(sender, EMessages.CMD_ERROR_TARGET_IN_FACTION)
			return
		}

		val faction = FPlayer.get(sender).getFaction()
		val memberContainer = faction.getMemberContainer()

		if (memberContainer.isInvited(target.uniqueId)) {
			Chat.tell(sender, EMessages.CMD_ERROR_ALREADY_INVITED)
			return
		}

		val taskID = ({
			if (memberContainer.isInvited(target.uniqueId)) {
				memberContainer.uninvite(target.uniqueId)
				Chat.tell(target, EMessages.CMD_INVITE_TIMEOUT_MESSAGE, faction.getName())
			}
		}).runLater((ConfigFile.getInt(CValues.FACTION_INVITE_TIMEOUT) ?: 60) * 20L)
		memberContainer.invite(target.uniqueId, taskID)
		Chat.tell(sender, EMessages.CMD_INVITE_SENT_MESSAGE, target.name)
		Chat.tell(target, EMessages.CMD_INVITE_RECEIVED_MESSAGE, faction.getName())
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_PLAYER_INVITED, target.name, sender.name)
	}

}