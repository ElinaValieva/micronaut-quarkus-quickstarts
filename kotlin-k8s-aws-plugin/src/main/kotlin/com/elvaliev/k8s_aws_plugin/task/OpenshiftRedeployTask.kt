package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class OpenshiftRedeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(PluginConstant.Openshift) as KubernetesPluginExtension
        println("Start task: ${extension.print()}")
        checkForClient(Client.oc)
        println("Redeploy application ${extension.application} from docker image ${extension.image}")
        executeCommand("oc tag ${extension.image} ${extension.application}:latest")
    }
}