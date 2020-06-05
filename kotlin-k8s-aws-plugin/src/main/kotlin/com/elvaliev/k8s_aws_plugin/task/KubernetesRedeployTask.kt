package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class KubernetesRedeployTask : DeployDefaultTask() {

    @Option(option = "application", description = "Application name")
    var application: String? = null

    @Option(option = "port", description = "Application port, where should be exposed")
    var port: String? = null

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(PluginConstant.Kubernetes) as? KubernetesPluginExtension
        val app = parseValue(extension?.application, application, "application")
        val port = parseValue(extension?.port, port, "port")
        println("${PluginConstant.ANSI_GREEN}Start task: Application: $app, Port = $port${PluginConstant.ANSI_RESET}")
        checkForClient(Client.kubectl)
        executeCommand("kubectl set image deployment $app  $app=$port")
    }
}