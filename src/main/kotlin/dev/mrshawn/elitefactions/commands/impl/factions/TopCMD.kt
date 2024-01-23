package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.extensions.doReplacements
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.command.CommandSender

@CommandAlias("top")
class TopCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands.top")
) {

	override fun execute(sender: CommandSender, args: Array<String>) {
		val topFactions = FactionManager.getFactions().sortedByDescending { it.getPower() }

		val listMessage = MessagesFile.get(EMessages.CMD_TOP_MESSAGE_LIST) as List<*>
		listMessage.forEach { line ->
			val lineString = line.toString()
			// replace all instances of {n} with faction at index n from topFactions
			lineString.replace(Regex("\\{\\d+}")) {
				val index = it.value.replace("{", "").replace("}", "").toInt()
				if (index < topFactions.size) {
					getEntryForFaction(topFactions[index])
				} else {
					"null"
				}
			}
			Chat.tell(sender, lineString)
		}
	}

	private fun getEntryForFaction(faction: Faction): String {
		return doReplacements(MessagesFile.getString(EMessages.CMD_TOP_MESSAGE_ENTRY) ?: "null", faction.getName(), faction.getPower(), faction.getMaxPower())
	}

}