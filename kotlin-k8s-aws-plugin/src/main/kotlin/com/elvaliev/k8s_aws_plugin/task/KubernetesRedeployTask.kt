package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class KubernetesRedeployTask : DeployTask() {

    @Input
    lateinit var application: String

    @Input
    lateinit var image: String

    @Input
    lateinit var port: String

    @TaskAction
    fun run() {
        checkForClient(Client.kubectl)
        println("Redeploy application $application from docker image $image")
        executeCommand("kubectl set image deployment $application  $application=$port")
    }
}