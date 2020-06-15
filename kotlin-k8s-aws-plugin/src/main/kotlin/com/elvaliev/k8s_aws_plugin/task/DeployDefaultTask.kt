package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.parser.KubernetesParser
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

open class DeployDefaultTask : DefaultTask() {

    enum class Client {
        kubectl, oc, sam
    }

    fun checkForClient(client: Client, commandArg: String = "--help") {
        val process = ProcessBuilder(createCommandLineArgs("$client $commandArg"))
            .directory(project.projectDir).start()

        val bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
        process.waitFor(3, TimeUnit.SECONDS)
        while (bufferedReader.ready()) {
            process.destroy()
            println("${PluginConstant.ANSI_RED}${bufferedReader.readLine()}${PluginConstant.ANSI_RESET}")
            throw GradleException("Client $client are not recognized. Check that $client installed.")
        }

        process.destroy()
    }

    fun retrieveFile(
        fileName: String,
        extensions: ArrayList<String> = arrayListOf("yaml", "yml"),
        rootDir: String = "build"
    ): String {
        if (project.file(fileName).exists())
            return fileName

        var foundedFile = project.rootDir.listFiles()?.let {
            it.find { file ->
                file.name.contains(fileName) && extensions.contains(file.extension)
            }
        }

        if (foundedFile != null && foundedFile.exists())
            return foundedFile.name

        foundedFile = project.file(rootDir).listFiles()?.let {
            it.find { file -> file.name.contains(fileName) && extensions.contains(file.extension) }
        }

        if (foundedFile == null)
            throw GradleException("File doesn't exist by provided path: $fileName")

        return "$rootDir/${foundedFile.name}"
    }

    fun findJar(rootDir: String = "build/libs", extension: String = "jar"): String {
        if (project.file("$rootDir/${project.name}-${project.version}.$extension").exists())
            return "$rootDir/${project.name}-${project.version}.$extension"

        val jarFile = project.file(rootDir)
            .listFiles()?.let {
                it.find { file -> file.extension == extension && file.nameWithoutExtension.startsWith(project.name) }
            }
        return "$rootDir/${jarFile?.name}"
    }

    fun executeCommand(command: String, continueOnError: Boolean = false) {
        println(">> ${PluginConstant.ANSI_GREEN}$command${PluginConstant.ANSI_RESET}")
        try {
            project.exec {
                if (Os.isFamily(Os.FAMILY_WINDOWS))
                    commandLine("cmd", "/c", command)
                else
                    commandLine("sh", "-c", command)
            }
        } catch (e: Exception) {
            if (!continueOnError)
                throw GradleException("Error $e was occupied when executing $command")
        }
    }

    fun checkCommandExecution(command: String): Boolean {
        val process = ProcessBuilder(createCommandLineArgs(command))
            .directory(project.projectDir).start()

        val bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
        process.waitFor(3, TimeUnit.SECONDS)
        while (bufferedReader.ready()) {
            process.destroy()
            return false
        }

        process.destroy()
        return true
    }

    fun checkBinaryBuild(command: String): Boolean {
        val process = ProcessBuilder(createCommandLineArgs(command))
            .directory(project.projectDir).start()

        var bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
        process.waitFor(3, TimeUnit.SECONDS)
        while (bufferedReader.ready()) {
            process.destroy()
            return false
        }

        bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
        process.waitFor(3, TimeUnit.SECONDS)
        while (bufferedReader.ready()) {
            val sourceType = bufferedReader.readLine()
            process.destroy()
            return sourceType == "Binary"
        }

        return false
    }

    fun createCommandLineArgs(command: String): List<String> {
        val args = command.split(" ")

        if (Os.isFamily(Os.FAMILY_WINDOWS))
            return listOf("cmd", "/c") + args
        return listOf("sh", "-c") + args
    }

    fun parseValue(valueFromExtension: String?, valueFromCommandLine: String?, parameterName: String): String {
        return when (valueFromExtension == null) {
            true -> when (valueFromCommandLine == null) {
                true -> throw GradleException("$parameterName was not defined")
                else -> valueFromCommandLine
            }
            else -> valueFromExtension
        }
    }

    fun getKubernetesTemplate(template: String): KubernetesParser.KubernetesTemplate? {
        val openShiftParser = KubernetesParser()
        return openShiftParser.parseTemplate(template)
    }
}
