package com.elvaliev.k8s_plugin.task

import org.gradle.api.tasks.TaskAction

class AwsDeployTask extends DeployTask {

    def samTemplate
    def s3Bucket
    def stackName

    @TaskAction
    def run() {
        checkForClient(Client.sam)
        executeCommand("sam package --template-file ${samTemplate} --output-template-file packaged.yaml --s3-bucket ${s3Bucket}")
        executeCommand("sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name ${stackName}")
    }
}
