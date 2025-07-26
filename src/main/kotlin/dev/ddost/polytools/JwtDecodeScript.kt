package dev.ddost.polytools

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.JwtException
import java.util.Base64
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.jsonwebtoken.io.Decoders
import java.nio.charset.StandardCharsets

object JwtDecodeScript : PolyToolScript {
    override val name: String = "JWT: Decode"
    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun execute(input: String): ActionResult {
        return try {
            val parts = input.split('.')
            if (parts.size < 2) return ActionResult.Error("Invalid JWT: not enough parts.")
            val headerJson = decodePart(parts[0])
            val payloadJson = decodePart(parts[1])
            val formattedResult = listOf(
                "--- HEADER ---",
                headerJson,
                "--- PAYLOAD ---",
                payloadJson
            ).joinToString("\n\n")
            // Возвращаем приказ "показать всплывающее окно"!
            ActionResult.ShowPopup("JWT Decoded", formattedResult)
        } catch (e: JwtException) {
            ActionResult.Error("Invalid JWT: ${e.message}")
        } catch (e: Exception) {
            ActionResult.Error("Error decoding JWT: ${e.message}")
        }
    }

    private fun decodePart(part: String): String {
        val decodedBytes = Decoders.BASE64URL.decode(part)
        val jsonString = String(decodedBytes, StandardCharsets.UTF_8)
        // Форматируем JSON для красивого вывода
        return gson.toJson(JsonParser.parseString(jsonString))
    }
}
