package com.elvaliev.k8s_aws_plugin.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.GradleException
import org.gradle.api.Project

open class DeployDefaultTask(open val project: Project) {

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

    fun checkFile(filePath: String) {
        print(filePath)
        if (!project.file(filePath).exists())
            throw GradleException("File doesn't exist by provided path: $filePath")
    }

    fun executeCommand(command: String) {
        println(command)
        try {
            project.exec {
                if (Os.isFamily(Os.FAMILY_WINDOWS))
                    commandLine("cmd", "/c", command)
                else
                    commandLine(command)
            }
        } catch (e: Exception) {
            println(e)
            throw GradleException("Error was occupied: $e")
        }
    }
}