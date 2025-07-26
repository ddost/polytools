package dev.ddost.polytools

interface PolyToolScript {
    val name: String
    fun execute(input: String): ActionResult
}
