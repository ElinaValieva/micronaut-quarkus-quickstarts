package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class OpenShiftDeployTask : DeployTask() {

    @Input
    lateinit var application: String

    @Input
    lateinit var image: String

    @Input
    lateinit var templatePath: String

    @TaskAction
    fun run() {
        checkForClient(Client.oc)
        println("Starting creating $application from docker registry $image by template from $templatePath")
        executeCommand("oc create -f $templatePath | oc apply -f-")
        executeCommand("oc tag $image $application:latest")
        executeCommand("oc expose svc/$application")
    }
}