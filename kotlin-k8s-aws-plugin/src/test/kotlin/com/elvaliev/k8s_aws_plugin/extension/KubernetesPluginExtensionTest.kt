package com.elvaliev.k8s_aws_plugin.extension

import org.junit.Assert.assertEquals
import org.junit.Test

internal class KubernetesPluginExtensionTest {

    @Test
    fun print() {
        val extension = KubernetesPluginExtension()
        extension.port = 8088.toString()
        extension.application = "app"
        assertEquals("Application = ${extension.application} Port = ${extension.port}", extension.print())
    }
}