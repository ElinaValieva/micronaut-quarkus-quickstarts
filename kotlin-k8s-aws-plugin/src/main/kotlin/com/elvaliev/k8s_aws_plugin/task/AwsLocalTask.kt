package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class AwsLocalTask : DeployTask() {

    @Input
    lateinit var samTemplate: String

    @TaskAction
    fun run() {
        checkForClient(Client.sam)
        executeCommand("sam local start-api --template $samTemplate")
    }
}