package com.elvaliev.k8s_aws_plugin.extension

import org.junit.Assert.assertEquals
import org.junit.Test

internal class AwsPluginExtensionTest {

    @Test
    fun print() {
        val extension = AwsPluginExtension()
        extension.samTemplate = "templateTest"
        extension.s3Bucket = "bucketTest"
        assertEquals("Template = ${extension.samTemplate} Bucket = ${extension.s3Bucket} ", extension.print())
    }
}