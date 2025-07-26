package dev.ddost.polytools

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object JsonFormatScript : PolyToolScript {
    override val name: String = "JSON: Format"
    private val gson = GsonBuilder().setPrettyPrinting().create()
    override fun execute(input: String): ActionResult {
        return try {
            val jsonElement = JsonParser.parseString(input)
            ActionResult.ReplaceText(gson.toJson(jsonElement))
        } catch (e: Exception) {
            ActionResult.Error("Invalid JSON: ${e.message}")
        }
    }
}
