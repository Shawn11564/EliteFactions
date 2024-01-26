package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions
import dev.mrshawn.elitefactions.commands.impl.factions.admin.AdminCMD
import org.bukkit.command.CommandSender

@CommandAlias("faction|f")
class BaseCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands")
) {

	init {
		addSubcommands(
			AdminCMD(),
			HelpCMD(),
			InfoCMD(),
			WhoCMD(),
			TopCMD(),
			CreateCMD(),
			DisbandCMD(),
			RenameCMD(),
			DescriptionCMD(),
			InviteCMD(),
			UninviteCMD(),
			JoinCMD(),
			OpenCMD(),
			KickCMD()
		)
	}

	override fun execute(sender: CommandSender, args: Array<String>) {
		HelpCMD.execute(sender)
	}

}