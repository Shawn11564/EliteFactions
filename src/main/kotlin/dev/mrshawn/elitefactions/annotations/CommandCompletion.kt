package dev.mrshawn.elitefactions.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class CommandCompletion(val completions: String)