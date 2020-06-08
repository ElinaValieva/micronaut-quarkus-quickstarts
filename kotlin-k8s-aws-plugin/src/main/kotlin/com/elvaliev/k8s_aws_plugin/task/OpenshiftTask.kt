package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Openshift
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class OpenshiftTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "template", description = "Custom template file, as default used openshift.yaml")
    var templatePath: String = "openshift.yaml"

    @Input
    @Optional
    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>:<tag>")
    var dockerImage: String? = null

    @Input
    @Optional
    @Option(option = "jar", description = "Executable java jar as default build/libs/*.jar")
    var jar: String = "libs\\${project.name}-${project.version}.jar"

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Openshift) as? KubernetesPluginExtension
        templatePath = retrieveFile(parseValue(extension?.template, templatePath, "template"))
        dockerImage = parseValue(extension?.image, dockerImage, "image")
        jar = parseValue(extension?.jar, jar, "jar")
        checkForClient(Client.oc)
        val kubernetesTemplate = getKubernetesTemplate(templatePath)
        val app = parseValue(kubernetesTemplate?.application, project.name, "application")
        val imageStream = kubernetesTemplate?.imageStreamApplication
        when (checkDeployments("oc get  deploymentConfig $app")) {
            true -> buildDeployment(app, imageStream)
            false -> kubernetesTemplate?.isTemplate?.let { createDeployment(app, imageStream, it) }
        }
    }

    private fun createDeployment(
        app: String?,
        imageStream: String?,
        isTemplate: Boolean
    ) {
        if (isTemplate)
            executeCommand("oc process -f $templatePath | oc apply -f-")
        else
            executeCommand("oc create -f $templatePath", continueOnError = true)
        buildDeployment(app, imageStream)
        executeCommand("oc expose svc/$app", continueOnError = true)
        executeCommand("oc get route $app -o jsonpath --template={.spec.host}")
    }

    private fun buildDeployment(app: String?, imageStream: String?) {
        if (checkBinaryBuild("oc get buildConfig $app -o jsonpath --template={.spec.source.type}"))
            executeCommand("oc start-build $app --from-dir build\\$jar --follow")
        imageStream?.let {
            executeCommand("oc tag $dockerImage $imageStream")
        }
    }
}