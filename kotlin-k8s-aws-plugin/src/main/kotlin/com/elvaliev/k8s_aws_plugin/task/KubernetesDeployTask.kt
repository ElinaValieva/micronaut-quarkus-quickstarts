package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_GREEN
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_RESET
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class KubernetesDeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Kubernetes) as KubernetesPluginExtension
        println("${ANSI_GREEN}Start task: ${extension.print()}${ANSI_RESET}")
        checkForClient(Client.kubectl)
        extension.path?.let { checkFile(it) }
        executeCommand("kubectl create deployment ${extension.application} --image=${extension.image}")
        executeCommand("kubectl create -f ${extension.path} --record --save-config")
        extension.port?.let {
            executeCommand("kubectl expose deployment ${extension.application} --type=LoadBalancer --port=${extension.port}")
        }
    }

}