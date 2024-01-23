package dev.mrshawn.elitefactions.files.struct.impl

import dev.mrshawn.elitefactions.EliteFactions
import dev.mrshawn.elitefactions.files.struct.EFile
import dev.mrshawn.elitefactions.files.struct.PathEnum
import dev.mrshawn.mlib.chat.Chat
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class YamlFile(
	private val javaFile: File,
	isResource: Boolean = false
): EFile {

	private var configuration: YamlConfiguration

	init {
		if (!javaFile.parentFile.exists()) javaFile.parentFile.mkdirs()
		if (!javaFile.exists()) {
			if (isResource) {
				EliteFactions.instance.saveResource(javaFile.name, false)
			} else {
				javaFile.createNewFile()
			}
		}

		configuration = YamlConfiguration.loadConfiguration(javaFile)
		reconcile()
	}



	override fun get(path: String): Any? {
		return configuration.get(path)
	}

	override fun get(path: PathEnum): Any? {
		return get(path.getPath())
	}

	override fun set(path: String, value: Any?) {
		configuration.set(path, value)
	}

	override fun has(path: String): Boolean {
		return configuration.isSet(path)
	}

	override fun reload() {
		save()
		configuration = YamlConfiguration.loadConfiguration(javaFile)
	}

	override fun save() {
		configuration.save(javaFile)
	}

	final override fun reconcile() {
		val savedResource = EliteFactions.instance.getResource(javaFile.name) ?: return
		val savedConfig = YamlConfiguration.loadConfiguration(savedResource.reader())
		var changed = false

		for (key in savedConfig.getKeys(true)) {
			if (!configuration.isSet(key)) {
				changed = true
				Chat.log("&aReconciling &e${javaFile.name} &afile with new key &e$key&a.")
				configuration.set(key, savedConfig.get(key))
			}
		}

		if (changed) reload()
	}

}