package com.elvaliev.k8s_aws_plugin.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.gradle.api.GradleException
import java.io.File

class KubernetesParser {

    fun parseFile(filePath: String): KubernetesTemplate {
        val kubernetesTemplate = KubernetesTemplate()
        readFile(filePath).split("---")
                .filter { fileInput -> fileInput.isNotEmpty() }
                .forEach { fileInput ->
                    val mapper = ObjectMapper(YAMLFactory())
                    val deploymentConfig = mapper.readValue(fileInput, OpenshiftConfiguration::class.java)
                    if (deploymentConfig.kind == null)
                        throw GradleException("Unsupported file. Check that file correspond to valid template format")

                    if (deploymentConfig.kind == "DeploymentConfig" || deploymentConfig.kind == "Deployment") {
                        kubernetesTemplate.imageStreamApplication = getImage(deploymentConfig)
                        kubernetesTemplate.application = getApplicationName(deploymentConfig)
                    }

                    if (deploymentConfig.kind == "Service")
                        kubernetesTemplate.port = getTargetPort(deploymentConfig)
                }
        return kubernetesTemplate
    }

    private fun getImage(openshiftConfiguration: OpenshiftConfiguration): String? {
        return openshiftConfiguration.spec?.triggers?.find { trigger -> trigger.type.equals("ImageChange") }?.imageChangeParams?.from?.name
    }

    private fun getApplicationName(openshiftConfiguration: OpenshiftConfiguration): String? {
        return openshiftConfiguration.metaData?.name
    }

    private fun readFile(fileName: String): String {
        return File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
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