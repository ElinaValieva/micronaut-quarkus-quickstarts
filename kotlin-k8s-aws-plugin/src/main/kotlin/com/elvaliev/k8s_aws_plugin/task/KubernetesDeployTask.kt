package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class KubernetesDeployTask : DeployTask() {

    @Input
    lateinit var application: String

    @Input
    lateinit var image: String

    @Input
    lateinit var templatePath: String

    @Input
    lateinit var port: String

    @TaskAction
    fun run() {
        checkForClient(Client.kubectl)
        println("Starting creating $application from docker registry $image by template from $templatePath")
        executeCommand("kubectl create deployment $application --image=$image")
        executeCommand("kubectl create -f $templatePath --record --save-config")
        println("Expose $application on port=$port")
        executeCommand("kubectl expose deployment $application --type=LoadBalancer --port=$port")
    }
}