package dev.mrshawn.elitefactions

import dev.mrshawn.elitefactions.commands.FactionCommandManager
import dev.mrshawn.elitefactions.commands.impl.factions.BaseCMD
import dev.mrshawn.elitefactions.engine.factions.FactionManager
import dev.mrshawn.elitefactions.engine.factions.server.factions.ServerFactions
import dev.mrshawn.mlib.chat.Chat
import dev.mrshawn.mlib.selections.Selection
import dev.mrshawn.mlib.utilities.events.EventUtils
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

		fcm.registerCompletion("@factions") { FactionManager.getFactionNames() }
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