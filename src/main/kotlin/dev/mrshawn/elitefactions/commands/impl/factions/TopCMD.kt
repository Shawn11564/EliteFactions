package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandExecutor
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.extensions.doReplacements
import dev.mrshawn.elitefactions.files.CValues
import dev.mrshawn.elitefactions.files.ConfigFile
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.MessagesFile
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.command.CommandSender

@CommandAlias("top")
class TopCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.top")
	)
) {

	@CommandExecutor
	fun execute(sender: CommandSender, args: Array<String>) {
		val topFactions = FactionManager.getFactions().sortedByDescending { it.getPower() }

		val listMessage = MessagesFile.get(EMessages.CMD_TOP_MESSAGE_LIST) as List<*>
		listMessage.forEach { line ->
			var lineString = line.toString()
			val originalLine = lineString
			val pattern = "\\{(\\d+)}".toPattern()
			val matcher = pattern.matcher(lineString)
			var foundMatch = false

			// replace all instances of {n} with faction at index n from topFactions
			while (matcher.find()) {
				foundMatch = true
				val index = matcher.group(1).toInt()
				if (index < topFactions.size) {
					val faction = topFactions[index]
					val replacement = getEntryForFaction(faction)
					lineString = lineString.replace("{${index}}", replacement)
				}
			}

			if (lineString != originalLine && ConfigFile.getBoolean(CValues.FACTION_TOP_TRIM_EMPTY) != false || !foundMatch) {
				Chat.tell(sender, lineString)
			}
		}
	}

	private fun getEntryForFaction(faction: Faction): String {
		return doReplacements(MessagesFile.getString(EMessages.CMD_TOP_MESSAGE_ENTRY) ?: "null", faction.getName(), faction.getPower(), faction.getMaxPower())
	}

}