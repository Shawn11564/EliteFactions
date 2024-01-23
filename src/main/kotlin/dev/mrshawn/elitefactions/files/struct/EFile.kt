package dev.mrshawn.elitefactions.files.struct

interface EFile {

	fun get(path: String): Any?

	fun get(path: PathEnum): Any?

	fun getString(path: String): String? {
		return get(path) as? String
	}

	fun getString(path: PathEnum): String? {
		return getString(path.getPath())
	}

	fun getInt(path: String): Int? {
		return get(path) as? Int
	}

	fun getInt(path: PathEnum): Int? {
		return getInt(path.getPath())
	}

	fun getDouble(path: String): Double? {
		return get(path) as? Double
	}

	fun getDouble(path: PathEnum): Double? {
		return getDouble(path.getPath())
	}

	fun getBoolean(path: String): Boolean? {
		return get(path) as? Boolean
	}

	fun getBoolean(path: PathEnum): Boolean? {
		return getBoolean(path.getPath())
	}

	fun set(path: String, value: Any?)

	fun has(path: String): Boolean

	fun reload()

	fun save()

	fun reconcile()

}