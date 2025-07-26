package dev.ddost.polytools

import java.security.MessageDigest

class HashScript(private val algorithm: String) : PolyToolScript {
    override val name: String = "Hash: $algorithm"
    override fun execute(input: String): ScriptResult {
        return try {
            val digest = MessageDigest.getInstance(algorithm)
            val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
            val hexString = hashBytes.joinToString("") { "%02x".format(it) }
            ScriptResult.Success(hexString)
        } catch (e: Exception) {
            ScriptResult.Failure("Hashing error: ${e.message}")
        }
    }
}
