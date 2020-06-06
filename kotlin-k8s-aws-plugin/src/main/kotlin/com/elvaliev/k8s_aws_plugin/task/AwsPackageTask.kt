package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class AwsPackageTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "template", description = "Custom template file, as default used template.yaml")
    var samTemplate: String = "template.yaml"

    @Input
    @Optional
    @Option(option = "stack", description = "Stack name")
    var stackName: String? = null

    @Input
    @Optional
    @Option(option = "bucket", description = "Bucket name")
    var s3Bucket: String? = null

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Aws) as? AwsPluginExtension
        var template = parseValue(extension?.template, samTemplate, "template")
        val bucket = parseValue(extension?.bucket, s3Bucket, "bucket")
        val stack = parseValue(extension?.stack, stackName, "stack")
        println("${PluginConstant.ANSI_GREEN}Start task: Template = $template, Bucket = $bucket, Stack name = $stack${PluginConstant.ANSI_RESET}")
        checkForClient(Client.sam)
        template?.let {
            template = retrieveFile(it)
            executeCommand("sam package --template-file $template --output-template-file packaged.yaml --s3-bucket $bucket")
            executeCommand("sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name $stack")
        }
    }
}