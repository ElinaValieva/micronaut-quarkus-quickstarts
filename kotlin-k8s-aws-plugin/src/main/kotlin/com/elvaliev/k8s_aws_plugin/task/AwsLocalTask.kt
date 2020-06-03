package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_GREEN
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_RED
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_RESET
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.TimeUnit

open class AwsLocalTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Aws) as AwsPluginExtension
        println("${ANSI_GREEN}Start task: ${extension.print()}${ANSI_RESET}")
        checkForClient(Client.sam)
        extension.samTemplate?.let { checkFile(it) }

        val arg = createCommandLineArgs("sam local start-api --template ${extension.samTemplate}")
        val process = ProcessBuilder(arg).directory(project.projectDir).start()

        val bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
        process.waitFor(5, TimeUnit.SECONDS)
        while (bufferedReader.ready()) {
            val line = bufferedReader.readLine().replace("(Press CTRL+C to quit)", "")
            println("${ANSI_GREEN}$line${ANSI_RESET}")
        }

        println("${ANSI_RED}\n\n >> KEY PRESS TO QUIT${ANSI_RESET}")
        val scanner = Scanner(System.`in`)
        if (scanner.hasNext())
            process.destroy()
    }
}