package dev.mrshawn.elitefactions.exceptions

import dev.mrshawn.elitefactions.files.struct.PathEnum

class ContextResolverFailedException(): Exception() {

	constructor(msg: PathEnum, vararg replacements: Any): this()

}