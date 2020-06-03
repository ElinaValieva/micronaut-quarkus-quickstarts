package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import org.gradle.api.tasks.TaskAction

open class AwsPackageTask : DeployDefaultTask() {

    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Aws) as AwsPluginExtension
        println(extension.print())
        checkForClient(Client.sam)
        checkFile(extension.samTemplate)
        executeCommand("sam package --template-file ${extension.samTemplate} --output-template-file packaged.yaml --s3-bucket ${extension.s3Bucket}")
        executeCommand("sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name ${extension.stackName}")
    }
}