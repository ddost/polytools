package dev.ddost.polytools

import java.security.MessageDigest

class HashScript(private val algorithm: String) : PolyToolScript {
    override val name: String = "Hash: $algorithm"
    override fun execute(input: String): ActionResult {
        return try {
            val digest = MessageDigest.getInstance(algorithm)
            val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
            val hexString = hashBytes.joinToString("") { "%02x".format(it) }
            ActionResult.ReplaceText(hexString)
        } catch (e: Exception) {
            ActionResult.Error("Hashing error: ${e.message}")
        }
    }
}
