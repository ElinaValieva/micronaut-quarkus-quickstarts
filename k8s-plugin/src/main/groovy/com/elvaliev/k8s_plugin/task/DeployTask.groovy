package com.elvaliev.k8s_plugin.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException

class DeployTask extends DefaultTask {

    enum Client {
        kubectl, oc, sam
    }

    private static Iterable<String> osAdaptiveCommand(String command) {
        def newCommands = []
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            newCommands = ['cmd', '/c']
        }

        newCommands.add(command)
        return newCommands
    }

    def checkForClient(client) {
        println "Client ${client}"
        try {
            executeCommand("${client} --help")
        } catch (ignored) {
            throw new GradleException("Client ${client} are not recognized. Check that ${client} intalled.")
        }
    }

    def executeCommand(command) {
        println(command)
        try {
            project.exec {
                commandLine osAdaptiveCommand(command)
            }
        } catch (Exception e) {
            throw new GradleException("Error was occupied: ${e}")
        }
    }
}
