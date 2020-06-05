package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Openshift
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class OpenshiftDeployTask : DeployDefaultTask() {

    @Option(option = "application", description = "Application name")
    var application: String? = null

    @Option(option = "path", description = "Custom template file, as default used openshift.yaml")
    var templatePath: String = "openshift.yaml"

    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>")
    var dockerImage: String? = null

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Openshift) as? KubernetesPluginExtension
        val app = parseValue(extension?.application, application, "application")
        val template = parseValue(extension?.path, templatePath, "template")
        val image = parseValue(extension?.image, dockerImage, "image")
        println("${PluginConstant.ANSI_GREEN}Start task: Application: $app, Template: $template, Image = $image${PluginConstant.ANSI_RESET}")
        checkForClient(Client.oc, "version")
        template?.let {
            checkFile(template)
            executeCommand("oc create -f $template | oc apply -f-")
            executeCommand("oc tag $image $app:latest")
        }
    }


}