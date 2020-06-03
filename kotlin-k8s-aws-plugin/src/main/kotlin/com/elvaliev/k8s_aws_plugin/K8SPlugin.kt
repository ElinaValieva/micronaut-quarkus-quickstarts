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
import com.elvaliev.k8s_aws_plugin.task.AwsTask
import com.elvaliev.k8s_aws_plugin.task.KubernetesTask
import com.elvaliev.k8s_aws_plugin.task.OpenshiftTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class K8SPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val awsExtension = project.extensions.create(Aws, AwsPluginExtension::class.java)

        val awsLocal = project.task(AwsLocal)
        awsLocal.group = Aws
        awsLocal.description = AwsTaskDescription
        awsLocal.doLast {
            AwsTask(project).buildLambda(awsExtension)
        }

        val awsPackage = project.task(AwsPackage)
        awsPackage.group = Aws
        awsPackage.description = AwsTaskDescription
        awsPackage.doLast {
            AwsTask(project).packageLambda(awsExtension)
        }

        val kubernetesExtension = project.extensions.create(Kubernetes, KubernetesPluginExtension::class.java)

        val kubernetesDeploy = project.task(KubernetesDeploy)
        kubernetesDeploy.group = Kubernetes
        kubernetesDeploy.description = KubernetesTaskDescription
        kubernetesDeploy.doLast {
            KubernetesTask(project).deploy(kubernetesExtension)
        }

        val kubernetesRedeploy = project.task(KubernetesRedeploy)
        kubernetesRedeploy.group = Kubernetes
        kubernetesRedeploy.description = KubernetesTaskDescription
        kubernetesRedeploy.doLast {
            KubernetesTask(project).redeploy(kubernetesExtension)
        }

        val openShiftExtension = project.extensions.create(Openshift, KubernetesPluginExtension::class.java)

        val openshiftDeploy = project.task(OpenshiftDeploy)
        openshiftDeploy.group = Openshift
        openshiftDeploy.description = OpenshiftTaskDescription
        openshiftDeploy.doLast {
            OpenshiftTask(project).deploy(openShiftExtension)
        }

        val openshiftRedeploy = project.task(OpenshiftRedeploy)
        openshiftRedeploy.group = Openshift
        openshiftRedeploy.description = OpenshiftTaskDescription
        openshiftRedeploy.doLast {
            OpenshiftTask(project).redeploy(openShiftExtension)
        }

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