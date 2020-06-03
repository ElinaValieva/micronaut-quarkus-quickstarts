package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class KubernetesRedeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(PluginConstant.Kubernetes) as KubernetesPluginExtension
        println("Start task: ${extension.print()}")
        checkForClient(Client.kubectl)
        println("Redeploy application ${extension.application} from docker image ${extension.image}")
        executeCommand("kubectl set image deployment ${extension.application}  ${extension.application}=${extension.port}")
    }
}