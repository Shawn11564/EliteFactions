package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("join")
class JoinCMD: FactionCommand(
	Preconditions.Builder()
		.hasPermission("elitefactions.commands.join")
		.notInFaction(true)
		.build()
) {

	@CommandCompletion("@pending-invites")
	override fun execute(sender: Player, args: Array<String>) {
		if (args.isEmpty()) {
			Chat.tell(sender, EMessages.CMD_JOIN_USAGE)
			return
		}

		val faction = FactionManager.getFaction(args[0])
		if (faction == null) {
			Chat.tell(sender, EMessages.CMD_ERROR_FACTION_DOESNT_EXIST)
			return
		}

		if (!faction.isOpen() && !faction.getMemberContainer().isInvited(sender.uniqueId)) {
			Chat.tell(sender, EMessages.CMD_ERROR_NOT_INVITED)
			return
		}

		val fPlayer = FPlayer.get(sender)
		faction.getMemberContainer().addMember(fPlayer)
		fPlayer.setFaction(faction)

		Chat.tell(sender, EMessages.CMD_JOIN_MESSAGE, faction.getName())
		Chat.tell(faction, EMessages.FACTIONS_ALERTS_PLAYER_JOINED, sender.name)
	}

}