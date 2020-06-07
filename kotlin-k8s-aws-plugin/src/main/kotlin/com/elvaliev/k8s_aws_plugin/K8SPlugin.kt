package com.elvaliev.k8s_aws_plugin

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsLocal
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsPackage
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesGroup
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Openshift
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftTaskDescription
import com.elvaliev.k8s_aws_plugin.extension.AwsPluginExtension
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import com.elvaliev.k8s_aws_plugin.task.AwsLocalTask
import com.elvaliev.k8s_aws_plugin.task.AwsPackageTask
import com.elvaliev.k8s_aws_plugin.task.KubernetesTask
import com.elvaliev.k8s_aws_plugin.task.OpenshiftTask
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

        val kubernetesDeploy = project.tasks.create(KubernetesDeploy, KubernetesTask::class.java)
        kubernetesDeploy.group = KubernetesGroup
        kubernetesDeploy.description = KubernetesTaskDescription

        val openshiftDeploy = project.tasks.create(OpenshiftDeploy, OpenshiftTask::class.java)
        openshiftDeploy.group = KubernetesGroup
        openshiftDeploy.description = OpenshiftTaskDescription

        project.allprojects {
            tasks.findByName("build")?.let {
                tasks.all {
                    if (arrayListOf(Kubernetes, Aws).contains(group) && !project.buildDir.exists()) {
                        dependsOn(it)
                    }
                }
            }
        }
    }
}