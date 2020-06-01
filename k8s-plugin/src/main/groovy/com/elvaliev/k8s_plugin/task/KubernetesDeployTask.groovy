package com.elvaliev.k8s_plugin.task

import org.gradle.api.tasks.TaskAction

class KubernetesDeployTask extends DeployTask {

    def application
    def image
    def templatePath
    def port

    @TaskAction
    def run() {
        checkForClient(Client.kubectl)
        println "Starting creating ${application} from docker registry ${image} by template from ${templatePath} "
        executeCommand("kubectl create deployment ${application} --image=${image}")
        executeCommand("kubectl create -f ${templatePath} --record --save-config")
        println "Expose ${application} on port=${port}"
        executeCommand("kubectl expose deployment ${application} --type=LoadBalancer --port=${port}")
    }
}
