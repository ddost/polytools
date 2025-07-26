package dev.ddost.polytools

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.JwtException
import java.util.Base64

object JwtDecodeScript : PolyToolScript {
    override val name: String = "JWT: Decode"
    override fun execute(input: String): ScriptResult {
        try {
            val jwt = input.substringBeforeLast('.') + "." // Убираем подпись для парсинга
            val parsedJwt = Jwts.parserBuilder().build().parseClaimsJwt(jwt)
            val header = parsedJwt.header.map { "${it.key}: ${it.value}" }.joinToString("\n")
            val payload = parsedJwt.body.map { "${it.key}: ${it.value}" }.joinToString("\n")

            // Для красивого вывода можно отформатировать как JSON
            // (Это потребует Gson/Jackson, которые у нас уже могут быть)
            // Для простоты пока так:
            return ScriptResult.Success("--- HEADER ---\n$header\n\n--- PAYLOAD ---\n$payload")
        } catch (e: JwtException) {
            return ScriptResult.Failure("Invalid JWT: ${e.message}")
        } catch (e: Exception) {
            return ScriptResult.Failure("Error decoding JWT: ${e.message}")
        }
    }
}