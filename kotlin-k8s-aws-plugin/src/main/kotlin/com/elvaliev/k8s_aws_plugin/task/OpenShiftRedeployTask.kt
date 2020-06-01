package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class OpenShiftRedeployTask : DeployTask() {

    @Input
    lateinit var application: String

    @Input
    lateinit var image: String

    @TaskAction
    fun run() {
        checkForClient(Client.oc)
        println("Redeploy application $application from docker image $image")
        executeCommand("oc tag $image $application:latest")
    }
}