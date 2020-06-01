package com.elvaliev.k8s_plugin.task

import org.gradle.api.tasks.TaskAction

class OpenShiftRedeployTask extends DeployTask {

    def application
    def image

    @TaskAction
    def run() {
        checkForClient(Client.oc)
        println "Redeploy application ${application} from docker image ${image}"
        executeCommand("oc tag ${image} ${application}:latest")
    }
}
