package com.elvaliev.k8s_aws_plugin

class PluginConstant {

    companion object {
        const val Aws = "aws"
        const val AwsLocal = "awsLocal"
        const val AwsPackage = "awsPackage"
        const val AwsTaskDescription = "Tasks for creating and packaging Amazon Lambda"
        const val Kubernetes = "kubernetes"
        const val KubernetesDeploy = "kubernetesDeploy"
        const val KubernetesTaskDescription = "Tasks for creating/redeploying application on Kubernetes"
        const val Openshift = "openshift"
        const val KubernetesGroup = "k8s"
        const val OpenshiftDeploy = "openshiftDeploy"
        const val OpenshiftTaskDescription = "Tasks for creating/redeploying application on Openshift"

        const val ANSI_RED = "\u001B[31m"
        const val ANSI_RESET = "\u001B[0m"
        const val ANSI_GREEN = "\u001B[32m"
    }
}