package dev.ddost.polytools

sealed class ScriptResult {
    data class Success(val value: String) : ScriptResult()
    data class Failure(val message: String) : ScriptResult()
}
