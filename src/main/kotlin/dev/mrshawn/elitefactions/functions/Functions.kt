package dev.mrshawn.elitefactions.functions

@JvmName("andThenNullable")
inline fun <T> T?.andThen(block: (T) -> Unit): T? {
	if (this != null) {
		block(this)
	}
	return this
}

inline fun <T> T.andThen(block: (T) -> Unit): T {
	if (this != null) {
		block(this)
	}
	return this
}