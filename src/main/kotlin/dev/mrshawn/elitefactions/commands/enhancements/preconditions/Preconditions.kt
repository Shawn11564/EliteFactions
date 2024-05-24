package dev.mrshawn.elitefactions.commands.enhancements.preconditions

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.commands.preconditions.Precondition
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HasFactionPrecondition(
	private val value: Boolean = true
): Precondition {
	override fun check(commandSender: CommandSender): Boolean {
		return commandSender is Player && FPlayer.get(commandSender).hasFaction() == value
	}
	override fun failMessage(): String {
		return if (value) {
			EMessages.PRECONDITIONS_ERROR_NOT_IN_FACTION.getMessage()
		} else {
			EMessages.PRECONDITIONS_ERROR_IN_FACTION.getMessage()
		}
	}
}

class HasPermissible(
	private val permissibleAction: PermissibleAction
): Precondition {
	override fun check(commandSender: CommandSender): Boolean {
		return commandSender is Player && FPlayer.get(commandSender).getFaction().getRankContainer().hasPermission(
			FPlayer.get(commandSender).getFaction().getMemberContainer().getRank(FPlayer.get(commandSender)) ?: Rank.GUEST,
			permissibleAction
		)
	}
	override fun failMessage(): String {
		return EMessages.PRECONDITIONS_ERROR_NOT_ALLOWED.getMessage()
	}
}

fun Precondition.Builder.hasFaction(value: Boolean = true): Precondition.Builder {
	addPrecondition(HasFactionPrecondition(value))
	return this
}

fun Precondition.Builder.notInFaction(): Precondition.Builder {
	addPrecondition(HasFactionPrecondition(false))
	return this
}

fun Precondition.Builder.hasPermissible(permissibleAction: PermissibleAction): Precondition.Builder {
	addPrecondition(HasPermissible(permissibleAction))
	return this
}