package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class KubernetesDeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Kubernetes) as KubernetesPluginExtension
        println("Start task: ${extension.print()}")
        checkForClient(Client.kubectl)
        checkFile(extension.path)
        executeCommand("kubectl create deployment ${extension.application} --image=${extension.image}")
        executeCommand("kubectl create -f ${extension.path} --record --save-config")
        println("Expose ${extension.application} on port=${extension.port}")
        executeCommand("kubectl expose deployment ${extension.application} --type=LoadBalancer --port=${extension.port}")
    }

}