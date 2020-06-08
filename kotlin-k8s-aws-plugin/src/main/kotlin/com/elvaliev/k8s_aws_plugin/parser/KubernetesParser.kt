package com.elvaliev.k8s_aws_plugin.parser

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.gradle.api.GradleException
import java.io.File

class KubernetesParser {
    // TODO: combine two approaches, port for openshift, flag for template - process

    fun parseFile(filePath: String): KubernetesTemplate {
        val configurations = parseFileConfigs(filePath)
            .filter { fileInput -> fileInput.isNotEmpty() }
            .map { fileInput ->
                configureObjectMapper().readValue(fileInput, OpenshiftConfiguration::class.java)
            }
        return parseConfigurations(configurations)
    }

    fun parseTemplate(filePath: String): KubernetesTemplate? {
        val mapper = configureObjectMapper()
        val openshiftTemplate = mapper.readValue(
            readFileContent(filePath),
            OpenshiftTemplate::class.java
        )
        val dictionary = openshiftTemplate.parameters?.associateBy({ "{${it.name}}" }, { it.value })
        return openshiftTemplate.objects?.let { parseConfigurations(it, dictionary) }
    }

    private fun configureObjectMapper() = ObjectMapper(YAMLFactory())
        .enable(JsonParser.Feature.ALLOW_YAML_COMMENTS)
        .enable(JsonParser.Feature.IGNORE_UNDEFINED)


    private fun parseConfigurations(
        configurations: List<OpenshiftConfiguration>,
        dictionary: Map<String, String?>? = null
    ): KubernetesTemplate {
        val kubernetesTemplate = KubernetesTemplate()

        configurations.forEach {
            if (it.kind == null)
                throw GradleException("Unsupported file. Check that file correspond to valid template format")

            if (it.kind == "DeploymentConfig" || it.kind == "Deployment") {
                kubernetesTemplate.imageStreamApplication = wrap(dictionary, getImage(it))
                kubernetesTemplate.application = wrap(dictionary, getApplicationName(it))
            }

            if (it.kind == "Service")
                kubernetesTemplate.port = wrap(dictionary, getTargetPort(it))
        }

        return kubernetesTemplate
    }

    private fun wrap(dictionary: Map<String, String?>?, value: String?): String? {
        val key = value?.replace("$", "")
        if (key != null && key.startsWith("{").and(key.endsWith("}"))) {
            if (dictionary == null)
                throw GradleException("Provided template doesn't have valid parameter reference")
            return dictionary[key]
        }
        return value
    }

    private fun readFileContent(filePath: String) =
        File(filePath).inputStream().readBytes().toString(Charsets.UTF_8)

    private fun getImage(openshiftConfiguration: OpenshiftConfiguration): String? {
        return openshiftConfiguration.spec?.triggers?.find { trigger -> trigger.type.equals("ImageChange") }?.imageChangeParams?.from?.name
    }

    private fun getApplicationName(openshiftConfiguration: OpenshiftConfiguration): String? {
        return openshiftConfiguration.metaData?.name
    }

    private fun parseFileConfigs(fileName: String): List<String> {
        val file = File(fileName)
        var fileContent = file.inputStream().readBytes().toString(Charsets.UTF_8)
        if (file.extension == "json")
            fileContent = fileContent.replace("}{", "}---{")
        return fileContent.split("---")
    }

    private fun getTargetPort(openshiftConfiguration: OpenshiftConfiguration): String? {
        return openshiftConfiguration.spec?.ports?.find { openshiftPort -> openshiftPort.name.equals("http") }?.targetPort
    }

    open class KubernetesTemplate {
        var application: String? = null

        var imageStreamApplication: String? = null

        var port: String? = null
    }
}