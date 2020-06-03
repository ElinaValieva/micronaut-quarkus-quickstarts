package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Openshift
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.TaskAction

open class OpenshiftDeployTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Openshift) as KubernetesPluginExtension
        println("Start task: ${extension.print()}")
        checkForClient(Client.oc)
        checkFile(extension.path)
        println("Starting creating ${extension.application} from docker registry ${extension.image} by template from ${extension.path}")
        executeCommand("oc create -f ${extension.path} | oc apply -f-")
        executeCommand("oc tag ${extension.image} ${extension.application}:latest")
    }
}