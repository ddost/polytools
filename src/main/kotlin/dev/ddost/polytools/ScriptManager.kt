package dev.ddost.polytools

object ScriptManager {
    val allScripts: List<PolyToolScript> = listOf(
        JsonFormatScript,
        JwtDecodeScript,
        HashScript("MD5"),
        HashScript("SHA-1"),
        HashScript("SHA-256"),
        Base64EncodeToolScript,
        Base64DecodeToolScript,
        ConvertJsonToYamlScript,
        ConvertYamlToJsonScript,
        UrlEncodeScript,
        UrlDecodeScript,
    ).sortedBy { it.name }
}
