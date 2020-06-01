package com.elvaliev.k8s_aws_plugin

import com.elvaliev.k8s_aws_plugin.extension.AwsExtension
import com.elvaliev.k8s_aws_plugin.extension.KubernetesExtension
import com.elvaliev.k8s_aws_plugin.task.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class K8SPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val awsExtension = project.extensions.create("aws", AwsExtension::class.java)
        val kubernetesExtension = project.extensions.create("kubernetes", KubernetesExtension::class.java)
        val openShiftExtension = project.extensions.create("openshift", KubernetesExtension::class.java)

        project.tasks.register("awsPackage", AwsDeployTask::class.java) { aws ->
            aws.group = "aws"
            aws.description = "AWS Deployment"
            aws.samTemplate = awsExtension.samTemplate
            aws.s3Bucket = awsExtension.s3Bucket
            aws.stackName = awsExtension.stackName
        }

        project.tasks.register("awsLocal", AwsLocalTask::class.java) { aws ->
            aws.group = "aws"
            aws.description = "AWS Deployment"
            aws.samTemplate = awsExtension.samTemplate
        }

        project.tasks.register("kubernetesCreate", KubernetesDeployTask::class.java) { kubernetes ->
            kubernetes.group = "kubernetes"
            kubernetes.description = "Kubernates Deployment"
            kubernetes.application = kubernetesExtension.application
            kubernetes.image = kubernetesExtension.image
            kubernetes.templatePath = kubernetesExtension.path
            kubernetes.port = kubernetesExtension.port.toString()
        }

        project.tasks.register("deployKubernetes", KubernetesRedeployTask::class.java) { kubernetes ->
            kubernetes.group = "kubernetes"
            kubernetes.description = "Kubernates Redeployment"
            kubernetes.application = kubernetesExtension.application
            kubernetes.image = kubernetesExtension.image
            kubernetes.port = kubernetesExtension.port.toString()
        }

        project.tasks.register("openshiftCreate", OpenShiftDeployTask::class.java) { openshift ->
            openshift.group = "openshift"
            openshift.description = "OpenShift Deployment"
            openshift.application = openShiftExtension.application
            openshift.image = openShiftExtension.image
            openshift.templatePath = openShiftExtension.path
        }

        project.tasks.register("openshiftDeploy", OpenShiftRedeployTask::class.java) { openshift ->
            openshift.group = "openshift"
            openshift.description = "OpenShift Redeployment"
            openshift.application = openShiftExtension.application
            openshift.image = openShiftExtension.image
        }
    }
}