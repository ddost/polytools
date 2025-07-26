package dev.ddost.polytools

import java.util.Base64

object Base64EncodeToolScript : PolyToolScript {
    override val name: String = "Base64: Encode"
    override fun execute(input: String): ActionResult {
        return ActionResult.ReplaceText(
            Base64.getEncoder().encodeToString(input.toByteArray())
        )
    }
}

object Base64DecodeToolScript : PolyToolScript {
    override val name: String = "Base64: Decode"
    override fun execute(input: String): ActionResult {
        return try {
            val cleanInput = input.replace("\\s".toRegex(), "")
            val decodedBytes = Base64.getDecoder().decode(cleanInput)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            ActionResult.ReplaceText(decodedString)
        } catch (_: IllegalArgumentException) {
            ActionResult.Error("Invalid Base64 string.")
        } catch (e: Exception) {
            ActionResult.Error("An unexpected error occurred during decoding: ${e.message}")
        }
    }
}
