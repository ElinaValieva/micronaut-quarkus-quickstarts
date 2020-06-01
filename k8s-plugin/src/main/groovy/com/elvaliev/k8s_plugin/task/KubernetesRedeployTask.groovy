package com.elvaliev.k8s_plugin.task

import org.gradle.api.tasks.TaskAction

class KubernetesRedeployTask extends DeployTask {

    def application
    def image
    def port

    @TaskAction
    def run() {
        checkForClient(Client.kubectl)
        println "Redeploy application ${application} from docker image ${image}"
        executeCommand("kubectl set image deployment ${application}  ${application}=${port}")
    }
}
