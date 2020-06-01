package com.elvaliev.k8s_aws_plugin.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class AwsDeployTask : DeployTask() {

    @Input
    lateinit var samTemplate: String

    @Input
    lateinit var s3Bucket: String

    @Input
    lateinit var stackName: String

    @TaskAction
    fun run() {
        checkForClient(Client.sam)
        executeCommand("sam package --template-file $samTemplate --output-template-file packaged.yaml --s3-bucket $s3Bucket")
        executeCommand("sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name $stackName")
    }
}