package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.annotations.CommandCompletion
import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.enhancements.Preconditions
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.entity.Player

@CommandAlias("test")
class TestCMD: FactionCommand(
	Preconditions.Builder().isPlayer(true).build()
) {

	@CommandCompletion("@nothing @boolean")
	@CommandExecutor
	fun execute(player: Player, num: Int, boolean: Boolean) {
		Chat.tell(player, "Your number is $num and your boolean is $boolean!")
	}

}