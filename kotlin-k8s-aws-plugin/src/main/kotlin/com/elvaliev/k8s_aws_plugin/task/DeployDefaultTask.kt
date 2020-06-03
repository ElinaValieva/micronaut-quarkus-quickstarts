package com.elvaliev.k8s_aws_plugin.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException

open class DeployDefaultTask : DefaultTask() {

    enum class Client {
        kubectl, oc, sam
    }

    fun checkForClient(client: Client) {
        try {
            executeCommand("$client --help")
        } catch (e: Exception) {
            throw GradleException("Client $client are not recognized. Check that $client intalled.")
        }
    }

    fun checkFile(filePath: String) {
        if (!project.file(filePath).exists())
            throw GradleException("File doesn't exist by provided path: $filePath")
    }

    fun executeCommand(command: String) {
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

    fun createCommandLineArgs(command: String): List<String> {
        val args = command.split(" ")

        if (Os.isFamily(Os.FAMILY_WINDOWS))
            return listOf("cmd", "/c") + args
        return args
    }
}