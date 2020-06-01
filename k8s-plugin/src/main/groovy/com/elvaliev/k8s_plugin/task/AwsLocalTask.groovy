package com.elvaliev.k8s_plugin.task


import org.gradle.api.tasks.TaskAction

class AwsLocalTask extends DeployTask {

    def samTemplate

    @TaskAction
    def run() {
        checkForClient(Client.sam)
        executeCommand("sam local start-api --template ${samTemplate}")
    }
}
