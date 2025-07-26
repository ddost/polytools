package dev.ddost.polytools

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ConvertJsonToYamlScript : PolyToolScript {
    override val name: String = "Convert: JSON to YAML"

    private val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    private val yamlMapper = YAMLMapper()

    override fun execute(input: String): ActionResult {
        return try {
            val jsonNode = jsonMapper.readTree(input)
            val yamlString = yamlMapper.writeValueAsString(jsonNode)
            ActionResult.ReplaceText(yamlString)
        } catch (e: Exception) {
            ActionResult.Error("Invalid JSON: ${e.localizedMessage}")
        }
    }
}

object ConvertYamlToJsonScript : PolyToolScript {
    override val name: String = "Convert: YAML to JSON"

    private val yamlMapper = YAMLMapper()
    private val jsonMapper = ObjectMapper()
        .registerModule(KotlinModule())
        .enable(SerializationFeature.INDENT_OUTPUT)

    override fun execute(input: String): ActionResult {
        return try {
            val yamlObject = yamlMapper.readValue(input, Any::class.java)
            val jsonString = jsonMapper.writeValueAsString(yamlObject)
            ActionResult.ReplaceText(jsonString)
        } catch (e: Exception) {
            ActionResult.Error("Invalid YAML: ${e.localizedMessage}")
        }
    }
}
