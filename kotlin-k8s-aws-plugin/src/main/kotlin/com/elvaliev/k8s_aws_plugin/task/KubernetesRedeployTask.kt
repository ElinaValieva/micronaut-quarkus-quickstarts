package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class KubernetesRedeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(PluginConstant.Kubernetes) as KubernetesPluginExtension
        println("${PluginConstant.ANSI_GREEN}Start task: ${extension.print()}${PluginConstant.ANSI_RESET}")
        checkForClient(Client.kubectl)
        extension.port?.let {
            executeCommand("kubectl set image deployment ${extension.application}  ${extension.application}=${extension.image}")
        }
    }
}