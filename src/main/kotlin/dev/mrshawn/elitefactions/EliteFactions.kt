package dev.mrshawn.elitefactions

import dev.mrshawn.elitefactions.commands.FactionCommandManager
import dev.mrshawn.elitefactions.commands.impl.factions.BaseCMD
import dev.mrshawn.elitefactions.commands.impl.factions.TestCMD
import dev.mrshawn.elitefactions.engine.factions.Faction
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.players.FPlayer
import dev.mrshawn.elitefactions.engine.factions.server.factions.ServerFactions
import dev.mrshawn.elitefactions.exceptions.ContextResolverFailedException
import dev.mrshawn.elitefactions.files.EMessages
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.selections.Selection
import dev.mrshawn.mlib.utilities.events.EventUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class EliteFactions: JavaPlugin() {

	companion object {
		lateinit var instance: EliteFactions
	}

	override fun onEnable() {
		instance = this
		Chat.setLogProvider(instance.name)

		if (!dataFolder.exists()) dataFolder.mkdir()

		initObjects()
		registerListeners()
		registerCommands()


	}

	override fun onDisable() {

	}

	private fun registerCommands() {
		val fcm = FactionCommandManager()

		fcm.registerCommand(BaseCMD())
		fcm.registerCommand(TestCMD())

		fcm.registerCompletion("@factions") { FactionManager.getFactionNames() }
		fcm.registerCompletion("@invited-players") {
			val memberContainer = FPlayer.get(it)?.getFaction()?.getMemberContainer() ?: return@registerCompletion emptyList<String>()
			memberContainer.getInvited().mapNotNull { uuid -> Bukkit.getPlayer(uuid)?.name }
		}
		fcm.registerCompletion("@pending-invites") {
			if (it is Player) {
				FactionManager.getFactions().filter { faction -> faction.isOpen() || faction.getMemberContainer().isInvited(it.uniqueId) }.map { faction ->  faction.getName() }
			} else {
				emptyList()
			}
		}
		fcm.registerCompletion("@faction-members") {
			if (it is Player) {
				FPlayer.get(it).getFaction().getMemberContainer().getMembers().mapNotNull { uuid -> Bukkit.getOfflinePlayer(uuid).name }
			} else {
				emptyList()
			}
		}

		fcm.registerContext(Player::class.java) { _, args -> Bukkit.getPlayer(args[0]) ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_PLAYER_NOT_FOUND) }
		fcm.registerContext(FPlayer::class.java) { _, args -> FPlayer.get(args[0]) ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_PLAYER_NOT_FOUND) }
		fcm.registerContext(Faction::class.java) { _, args -> FactionManager.getFaction(args[0]) ?: throw ContextResolverFailedException(EMessages.CMD_ERROR_FACTION_DOESNT_EXIST, args[0]) }
	}

	private fun registerListeners() {
		Selection.register(this)
		EventUtils.registerEvents(
			this,

		)
	}

	/**
	 * Access kotlin objects that we need the init to run on plugin load
	 */
	private fun initObjects() {
		ServerFactions
	}

}