package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class OpenshiftRedeployTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "application", description = "Application name")
    var application: String? = project.name

    @Input
    @Optional
    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>")
    var dockerImage: String? = null

    @TaskAction
    fun run() {

        val extension = project.extensions.findByName(PluginConstant.Openshift) as? KubernetesPluginExtension
        val app = parseValue(extension?.application, application, "application")
        val image = parseValue(extension?.image, dockerImage, "image")

        checkForClient(Client.oc)
        executeCommand("oc start-build $app --from-dir build\\libs\\${project.name}-${project.version}.jar --follow")
        executeCommand("oc tag $image $app:latest")
    }
}