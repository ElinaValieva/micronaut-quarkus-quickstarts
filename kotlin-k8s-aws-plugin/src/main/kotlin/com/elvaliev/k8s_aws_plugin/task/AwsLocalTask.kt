package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import org.gradle.api.tasks.TaskAction

open class AwsLocalTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Aws) as AwsPluginExtension
        println(extension.print())
        checkForClient(Client.sam)
        checkFile(extension.samTemplate)
        executeCommand("sam local start-api --template ${extension.samTemplate}")
    }
}