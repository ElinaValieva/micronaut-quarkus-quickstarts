package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.Project

open class OpenshiftTask(override val project: Project) : DeployDefaultTask(project) {

    open fun deploy(extension: KubernetesPluginExtension) {
        println("Start task: ${extension.print()}")
        checkForClient(Client.oc)
        checkFile(extension.path)
        println("Starting creating ${extension.application} from docker registry ${extension.image} by template from ${extension.path}")
        executeCommand("oc create -f ${extension.path} | oc apply -f-")
        executeCommand("oc tag ${extension.image} ${extension.application}:latest")
        executeCommand("oc expose svc/${extension.application}")
    }

    open fun redeploy(extension: KubernetesPluginExtension) {
        println("Start task: ${extension.print()}")
        checkForClient(Client.oc)
        println("Redeploy application ${extension.application} from docker image ${extension.image}")
        executeCommand("oc tag ${extension.image} ${extension.application}:latest")
    }
}