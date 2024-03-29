package dev.mrshawn.elitefactions.commands.enhancements

import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.players.ranks.PermissibleAction
import dev.mrshawn.elitefactions.engine.factions.players.ranks.Rank
import dev.mrshawn.elitefactions.extensions.hasPermission
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.elitefactions.files.struct.PathEnum
import dev.mrshawn.elitefactions.functions.andThen
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Preconditions private constructor(builder: Builder) {

	companion object {
		fun empty() = Builder().build()
		fun permission(permission: String) = Builder().hasPermission(permission).build()
	}

	private var hasPermission: String? = builder.hasPermission
	private var hasPermissible: PermissibleAction? = builder.hasPermissible.andThen { if (it != null) isPlayer = true }
	private var isPlayer: Boolean = builder.isPlayer
	private var hasFaction: Boolean = builder.hasFaction.andThen { if (it) isPlayer = true }
	private var notInFaction: Boolean = builder.notInFaction

	fun check(sender: CommandSender): Pair<Boolean, PathEnum> {
		var cachedFPlayer: FPlayer? = null

		if (!sender.hasPermission(hasPermission)) return false to EMessages.PRECONDITIONS_ERROR_NO_PERMISSION
		if (isPlayer && sender !is Player) return false to EMessages.PRECONDITIONS_ERROR_NOT_PLAYER
		if (hasPermissible != null) {
			if (sender !is Player) return false to EMessages.PRECONDITIONS_ERROR_NOT_PLAYER
			if (cachedFPlayer == null) cachedFPlayer = FPlayer.get(sender.uniqueId)
			val faction = cachedFPlayer.getFaction()
			val memberContainer = faction.getMemberContainer()
			if (!memberContainer.isMember(cachedFPlayer)) return false to EMessages.PRECONDITIONS_ERROR_NOT_IN_FACTION
			if (!faction.getRankContainer().hasPermission(
				faction.getMemberContainer().getRank(cachedFPlayer) ?: Rank.GUEST, hasPermissible!!
			)) {
				return false to EMessages.FACTIONS_PERMISSIONS_NO_PERMISSION
			}
		}
		if (hasFaction) {
			if (sender !is Player) return false to EMessages.PRECONDITIONS_ERROR_NOT_PLAYER
			if (cachedFPlayer == null) cachedFPlayer = FPlayer.get(sender.uniqueId)
			if (cachedFPlayer.getFaction().isServerFaction()) return false to EMessages.PRECONDITIONS_ERROR_NOT_IN_FACTION
		}
		if (notInFaction) {
			if (sender !is Player) return false to EMessages.PRECONDITIONS_ERROR_NOT_PLAYER
			if (cachedFPlayer == null) cachedFPlayer = FPlayer.get(sender.uniqueId)
			if (!cachedFPlayer.getFaction().isServerFaction()) return false to EMessages.PRECONDITIONS_ERROR_IN_FACTION
		}

		return true to EMessages.PRECONDITIONS_SUCCESS
	}

	class Builder {
		var hasPermission: String? = null
		var hasPermissible: PermissibleAction? = null
		var isPlayer: Boolean = false
		var hasFaction: Boolean = false
		var notInFaction: Boolean = false

		fun hasPermission(hasPermission: String) = apply { this.hasPermission = hasPermission }
		fun hasPermissible(hasPermissible: PermissibleAction) = apply { this.hasPermissible = hasPermissible }
		fun isPlayer(isPlayer: Boolean) = apply { this.isPlayer = isPlayer }
		fun hasFaction(hasFaction: Boolean) = apply { this.hasFaction = hasFaction }
		fun notInFaction(notInFaction: Boolean) = apply { this.notInFaction = notInFaction }
		fun build() = Preconditions(this)
	}

}