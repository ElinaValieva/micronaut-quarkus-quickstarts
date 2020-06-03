package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.Project

open class KubernetesTask(override val project: Project) : DeployDefaultTask(project) {

    open fun deploy(extension: KubernetesPluginExtension) {
        println("Start task: ${extension.print()}")
        checkForClient(Client.kubectl)
        checkFile(extension.path)
        executeCommand("kubectl create deployment ${extension.application} --image=${extension.image}")
        executeCommand("kubectl create -f ${extension.path} --record --save-config")
        println("Expose ${extension.application} on port=${extension.port}")
        executeCommand("kubectl expose deployment ${extension.application} --type=LoadBalancer --port=${extension.port}")
    }

    open fun redeploy(extension: KubernetesPluginExtension) {
        println("Start task: ${extension.print()}")
        checkForClient(Client.kubectl)
        println("Redeploy application ${extension.application} from docker image ${extension.image}")
        executeCommand("kubectl set image deployment ${extension.application}  ${extension.application}=${extension.port}")
    }
}