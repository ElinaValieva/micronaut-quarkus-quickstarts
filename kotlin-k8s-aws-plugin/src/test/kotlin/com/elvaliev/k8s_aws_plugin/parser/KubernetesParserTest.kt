package com.elvaliev.k8s_aws_plugin.parser

import org.gradle.api.GradleException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Ignore
import org.junit.Test

class KubernetesParserTest {

    @Test
    fun parseKubernetesFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("kubernetes.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseFile(path)
            println(kubernetesTemplate.toString())
            assertEquals("quarkus-quickstart", kubernetesTemplate.application)
            assertEquals("8090", kubernetesTemplate.port)
        }
    }

    @Test
    fun parseOpenshiftFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("openshift.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseFile(path)
            assertEquals("quarkus-quickstart", kubernetesTemplate.application)
            assertEquals("quarkus-quickstart:1.0.0", kubernetesTemplate.imageStreamApplication)
        }
    }

    @Test
    fun parseJsonFormatKubernetesFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("kubernetes.json")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseFile(path)
            println(kubernetesTemplate.toString())
            assertEquals("quarkus-quickstart", kubernetesTemplate.application)
            assertEquals("8090", kubernetesTemplate.port)
        }
    }

    @Test
    fun parseJsonFormatOpenshiftFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("openshift.json")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseFile(path)
            println(kubernetesTemplate.toString())
            assertEquals("quarkus-quickstart", kubernetesTemplate.application)
            assertEquals("quarkus-quickstart:1.0.0", kubernetesTemplate.imageStreamApplication)
        }
    }

    @Test(expected = GradleException::class)
    fun parseWrongFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("wrong.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            kubernetesParser.parseFile(path)
        }
    }
}