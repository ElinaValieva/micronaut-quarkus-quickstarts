package com.elvaliev.k8s_plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test

class KubernetesPluginTest {

    @Test
    void greeterPluginAddsGreetingTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('kubernetesCreate')
        Assert.assertNotNull(task)
    }
}
