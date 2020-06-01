package com.elvaliev.k8s_aws_plugin.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException

open class DeployTask : DefaultTask() {

    enum class Client {
        kubectl, oc, sam
    }

    fun checkForClient(client: Client) {
        println("Client $client")
        try {
            executeCommand("$client --help")
        } catch (e: Exception) {
            throw GradleException("Client $client are not recognized. Check that $client intalled.")
        }
    }

    fun executeCommand(command: String) {
        println(command)
        try {
            project.exec { e ->
                if (Os.isFamily(Os.FAMILY_WINDOWS))
                    e.commandLine("cmd", "/c", command)
                else
                    e.commandLine(command)
            }
        } catch (e: Exception) {
            println(e)
            throw GradleException("Error was occupied: $e")
        }
    }
}