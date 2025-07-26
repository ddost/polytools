package dev.ddost.polytools

import java.util.Base64

object Base64EncodeToolScript : PolyToolScript {
    override val name: String = "Base64: Encode"
    override fun execute(input: String): ScriptResult {
        return ScriptResult.Success(
            Base64.getEncoder().encodeToString(input.toByteArray())
        )
    }
}

object Base64DecodeToolScript : PolyToolScript {
    override val name: String = "Base64: Decode"
    override fun execute(input: String): ScriptResult {
        return try {
            // Убираем переносы строк, которые могут мешать декодированию
            val cleanInput = input.replace("\\s".toRegex(), "")

            // Пытаемся декодировать
            val decodedBytes = Base64.getDecoder().decode(cleanInput)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            // Если все прошло успешно, оборачиваем результат в Success
            ScriptResult.Success(decodedString)
        } catch (e: IllegalArgumentException) {
            // Если входная строка не является валидной Base64, JRE бросит это исключение.
            // Мы его ловим и возвращаем Failure с сообщением для пользователя.
            ScriptResult.Failure("Invalid Base64 string.")
        } catch (e: Exception) {
            // Ловим любые другие непредвиденные ошибки
            ScriptResult.Failure("An unexpected error occurred during decoding: ${e.message}")
        }
    }
}
