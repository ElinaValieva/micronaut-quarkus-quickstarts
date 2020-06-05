package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class OpenshiftRedeployTask : DeployDefaultTask() {

    @Option(option = "application", description = "Application name")
    var application: String? = null

    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>")
    var dockerImage: String? = null

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(PluginConstant.Openshift) as? KubernetesPluginExtension
        val app = parseValue(extension?.application, application, "application")
        val image = parseValue(extension?.image, dockerImage, "image")
        println("${PluginConstant.ANSI_GREEN}Start task: Application: $app, Image = $image${PluginConstant.ANSI_RESET}")
        checkForClient(Client.oc, "version")
        executeCommand("oc tag $image $app:latest")
    }
}