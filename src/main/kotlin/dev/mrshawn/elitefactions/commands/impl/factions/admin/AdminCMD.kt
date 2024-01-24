package dev.mrshawn.elitefactions.commands.impl.factions.admin

import dev.mrshawn.elitefactions.annotations.CommandAlias
import dev.mrshawn.elitefactions.commands.FactionCommand
import dev.mrshawn.elitefactions.commands.conditions.Preconditions

@CommandAlias("admin")
class AdminCMD: FactionCommand(
	Preconditions.permission("elitefactions.commands.admin")
) {

	init {
		addSubcommands(
			ReloadCMD()
		)
	}

}