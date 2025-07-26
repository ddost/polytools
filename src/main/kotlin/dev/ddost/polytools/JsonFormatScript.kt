package dev.ddost.polytools

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object JsonFormatScript : PolyToolScript {
    override val name: String = "JSON: Format"
    private val gson = GsonBuilder().setPrettyPrinting().create()
    override fun execute(input: String): ScriptResult {
        return try {
            val jsonElement = JsonParser.parseString(input)
            ScriptResult.Success(gson.toJson(jsonElement)) // Оборачиваем в Success
        } catch (e: Exception) {
            ScriptResult.Failure("Invalid JSON: ${e.message}") // Оборачиваем в Failure
        }
    }
}
