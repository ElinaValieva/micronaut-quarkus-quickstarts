package com.elvaliev.k8s_plugin.task

import org.gradle.api.tasks.TaskAction

class OpenShiftDeployTask extends DeployTask {

    def application
    def image
    def templatePath

    @TaskAction
    def run() {
        checkForClient(Client.oc)
        println "Starting creating ${application} from docker registry ${image} by template from ${templatePath}"
        executeCommand("oc create -f ${templatePath} | oc apply -f-")
        executeCommand("oc tag ${image} ${application}:latest")
        executeCommand("oc expose svc/${application}")
    }
}
