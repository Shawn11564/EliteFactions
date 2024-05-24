package dev.mrshawn.elitefactions.commands.impl.factions.admin

import dev.mrshawn.mlib.commands.MCommand
import dev.mrshawn.mlib.commands.annotations.CommandAlias
import dev.mrshawn.mlib.commands.preconditions.PermissionPrecondition

@CommandAlias("admin")
class AdminCMD: MCommand(
	listOf(
		PermissionPrecondition("elitefactions.commands.admin")
	)
) {

	init {
		addSubcommands(
			ReloadCMD(),
			AdminHomeCMD()
		)
	}

}