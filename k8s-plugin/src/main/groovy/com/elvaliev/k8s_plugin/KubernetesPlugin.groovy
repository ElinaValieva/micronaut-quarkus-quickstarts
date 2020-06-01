package com.elvaliev.k8s_plugin

import com.elvaliev.k8s_plugin.extension.AwsExtension
import com.elvaliev.k8s_plugin.extension.KubernetesExtension
import com.elvaliev.k8s_plugin.task.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class KubernetesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def kubernetesExtension = project.extensions.create('kubernetes', KubernetesExtension)
        def openShiftExtension = project.extensions.create('openshift', KubernetesExtension)
        def awsExtension = project.extensions.create('aws', AwsExtension)


        project.tasks.register('kubernetesCreate', KubernetesDeployTask) {
            group = 'kubernetes'
            description = 'Kubernates Deployment'
            application = kubernetesExtension.application
            image = kubernetesExtension.image
            templatePath = kubernetesExtension.path
            port = kubernetesExtension.port
        }

        project.tasks.register('deployKubernetes', KubernetesRedeployTask) {
            group = 'kubernetes'
            description = 'Kubernates Redeployment'
            application = kubernetesExtension.application
            image = kubernetesExtension.image
            port = kubernetesExtension.port
        }

        project.tasks.register('openshiftCreate', OpenShiftDeployTask) {
            group = 'openshift'
            description = 'OpenShift Deployment'
            application = openShiftExtension.application
            image = openShiftExtension.image
            templatePath = openShiftExtension.path
        }

        project.tasks.register('openshiftDeploy', OpenShiftRedeployTask) {
            group = 'openshift'
            description = 'OpenShift Redeployment'
            application = openShiftExtension.application
            image = openShiftExtension.image
        }

        project.tasks.register('awsLocal', AwsLocalTask) {
            group = 'aws'
            description = 'AWS Deployment'
            samTemplate = awsExtension.samTemplate
        }

        project.tasks.register('awsPackage', AwsDeployTask) {
            group = 'aws'
            description = 'AWS Deployment'
            samTemplate = awsExtension.samTemplate
            s3Bucket = awsExtension.s3Bucket
            stackName = awsExtension.stackName
        }
    }
}
