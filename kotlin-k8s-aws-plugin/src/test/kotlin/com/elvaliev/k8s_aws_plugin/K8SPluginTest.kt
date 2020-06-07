package com.elvaliev.k8s_aws_plugin

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Aws
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsLocal
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsPackage
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.AwsTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesGroup
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.KubernetesTaskDescription
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftDeploy
import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.OpenshiftTaskDescription
import com.elvaliev.k8s_aws_plugin.task.AwsLocalTask
import com.elvaliev.k8s_aws_plugin.task.AwsPackageTask
import com.elvaliev.k8s_aws_plugin.task.KubernetesTask
import com.elvaliev.k8s_aws_plugin.task.OpenshiftTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.*
import org.junit.Test


internal class K8SPluginTest {

    private var tasks: TaskContainer? = null

    init {
        val pluginProject: Project = ProjectBuilder.builder().build()
        pluginProject.pluginManager.apply("com.elvaliev.k8s_aws_plugin")
        tasks = pluginProject.tasks
    }

    @Test
    fun testForTaskEntityClass() {
        assertNotNull(tasks)
        assertTrue(tasks?.getByName(KubernetesDeploy) is KubernetesTask)
        assertTrue(tasks?.getByName(OpenshiftDeploy) is OpenshiftTask)
        assertTrue(tasks?.getByName(AwsLocal) is AwsLocalTask)
        assertTrue(tasks?.getByName(AwsPackage) is AwsPackageTask)
    }

    @Test
    fun testForGroup() {
        assertNotNull(tasks)
        assertEquals(KubernetesGroup, tasks?.getByName(KubernetesDeploy)?.group)
        assertEquals(KubernetesGroup, tasks?.getByName(OpenshiftDeploy)?.group)
        assertEquals(Aws, tasks?.getByName(AwsLocal)?.group)
        assertEquals(Aws, tasks?.getByName(AwsPackage)?.group)
    }

    @Test
    fun testForDescription() {
        assertNotNull(tasks)
        assertEquals(KubernetesTaskDescription, tasks?.getByName(KubernetesDeploy)?.description)
        assertEquals(OpenshiftTaskDescription, tasks?.getByName(OpenshiftDeploy)?.description)
        assertEquals(AwsTaskDescription, tasks?.getByName(AwsLocal)?.description)
        assertEquals(AwsTaskDescription, tasks?.getByName(AwsPackage)?.description)
    }
}