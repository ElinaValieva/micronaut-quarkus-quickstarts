package com.elvaliev.k8s_aws_plugin.extension

import org.junit.Assert.assertEquals
import org.junit.Test

internal class AwsPluginExtensionTest {

    @Test
    fun print() {
        val extension = AwsPluginExtension()
        extension.template = "templateTest"
        extension.bucket = "bucketTest"
        assertEquals("Template = ${extension.template} Bucket = ${extension.bucket} ", extension.print())
    }
}