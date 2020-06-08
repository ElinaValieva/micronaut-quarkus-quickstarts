package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_GREEN
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_RED
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.ANSI_RESET
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.TimeUnit

open class AwsLocalTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "template", description = "Custom template file, as default used template.yaml")
    var samTemplate: String = "template.yaml"

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Aws) as? AwsPluginExtension
        samTemplate = retrieveFile(parseValue(extension?.template, samTemplate, "template"))
        checkForClient(Client.sam)
        val arg = createCommandLineArgs("sam local start-api --template $samTemplate")
        val process = ProcessBuilder(arg).directory(project.projectDir).start()

        val bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
        process.waitFor(10, TimeUnit.SECONDS)
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