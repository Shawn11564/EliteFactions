package dev.mrshawn.elitefactions.commands.impl.factions

import dev.mrshawn.elitefactions.commands.impl.factions.admin.AdminCMD
import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.annotations.CommandExecutor
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition
import org.bukkit.command.CommandSender

@CommandAlias("faction|f")
class BaseCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands")
	)
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
			LeaveCMD(),
			OpenCMD(),
			KickCMD(),
			HomeCMD(),
			SetHomeCMD()
		)
	}

	@CommandExecutor
	fun execute(sender: CommandSender) {
		HelpCMD.execute(sender)
	}

}