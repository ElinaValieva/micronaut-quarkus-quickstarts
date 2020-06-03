package com.elvaliev.k8s_aws_plugin

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsLocal
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsPackage
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesRedeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Openshift
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftRedeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftTaskDescription
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import com.elvaliev.k8s_aws_plugin.task.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class K8SPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(Aws, AwsPluginExtension::class.java)
        project.extensions.create(Kubernetes, KubernetesPluginExtension::class.java)
        project.extensions.create(Openshift, KubernetesPluginExtension::class.java)

        val awsDeployTask = project.tasks.create(AwsLocal, AwsLocalTask::class.java)
        awsDeployTask.group = Aws
        awsDeployTask.description = AwsTaskDescription

        val awsPackage = project.tasks.create(AwsPackage, AwsPackageTask::class.java)
        awsPackage.group = Aws
        awsPackage.description = AwsTaskDescription

        val kubernetesDeploy = project.tasks.create(KubernetesDeploy, KubernetesDeployTask::class.java)
        kubernetesDeploy.group = Kubernetes
        kubernetesDeploy.description = KubernetesTaskDescription

        val kubernetesRedeploy = project.tasks.create(KubernetesRedeploy, KubernetesRedeployTask::class.java)
        kubernetesRedeploy.group = Kubernetes
        kubernetesRedeploy.description = KubernetesTaskDescription

        val openshiftDeploy = project.tasks.create(OpenshiftDeploy, OpenshiftDeployTask::class.java)
        openshiftDeploy.group = Openshift
        openshiftDeploy.description = OpenshiftTaskDescription

        val openshiftRedeploy = project.tasks.create(OpenshiftRedeploy, OpenshiftRedeployTask::class.java)
        openshiftRedeploy.group = Openshift
        openshiftRedeploy.description = OpenshiftTaskDescription

        project.allprojects {
            val build = tasks.getByName("build")
            tasks.all {
                if (arrayListOf(Kubernetes, Openshift, Aws).contains(group)) {
                    dependsOn(build)
                }
            }
        }
    }
}