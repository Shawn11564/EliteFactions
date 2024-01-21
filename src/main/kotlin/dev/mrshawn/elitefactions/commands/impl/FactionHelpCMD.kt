package dev.mrshawn.elitefactions.commands.impl

import dev.mrshawn.elitefactions.annotations.CommandAliases
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.extensions.tell
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAliases("help|?")
class FactionHelpCMD: FactionCommand() {

	override fun execute(sender: Player, args: Array<String>) {
		Chat.tell(sender,
			"&7&m--------------------------",
			"&b&lEliteFactions Help",
			"&7&m--------------------------",
			"&b/f help &7- &fShows this help menu",
			"&b/f create <name> &7- &fCreates a faction",
			"&b/f invite <player> &7- &fInvites a player to your faction",
			"&b/f join <faction> &7- &fJoins a faction",
			"&b/f leave &7- &fLeaves your current faction",
			"&b/f claim &7- &fClaims the chunk you're standing in",
			"&b/f unclaim &7- &fUnclaims the chunk you're standing in",
			"&b/f unclaim all &7- &fUnclaims all of your faction's chunks",
			"&b/f map &7- &fShows a map of claimed chunks",
		)
	}

}