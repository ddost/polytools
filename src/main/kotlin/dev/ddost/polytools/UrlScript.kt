package dev.ddost.polytools

import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object UrlEncodeScript : PolyToolScript {
    override val name: String = "URL: Encode"
    override fun execute(input: String): ActionResult {
        return ActionResult.ReplaceText(
            URLEncoder.encode(input, StandardCharsets.UTF_8.name())
        )
    }
}

object UrlDecodeScript : PolyToolScript {
    override val name: String = "URL: Decode"
    override fun execute(input: String): ActionResult {
        return ActionResult.ReplaceText(
            URLDecoder.decode(input, StandardCharsets.UTF_8.name())
        )
    }
}
