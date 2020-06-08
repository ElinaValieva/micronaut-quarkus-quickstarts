package com.elvaliev.k8s_aws_plugin.parser

import org.gradle.api.GradleException
import org.junit.Assert.assertEquals
import org.junit.Test

class KubernetesParserTest {

    @Test
    fun parseKubernetesFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("kubernetes.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseTemplate(path)
            assertEquals("quarkus-quickstart", kubernetesTemplate?.application)
            assertEquals("8090", kubernetesTemplate?.port)
        }
    }

    @Test
    fun parseOpenshiftFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("openshift.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseTemplate(path)
            assertEquals("quarkus-quickstart", kubernetesTemplate?.application)
            assertEquals("quarkus-quickstart:1.0.0", kubernetesTemplate?.imageStreamApplication)
        }
    }

    @Test
    fun parseJsonFormatKubernetesFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("kubernetes.json")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseTemplate(path)
            assertEquals("quarkus-quickstart", kubernetesTemplate?.application)
            assertEquals("8090", kubernetesTemplate?.port)
        }
    }

    @Test
    fun parseJsonFormatOpenshiftFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("openshift.json")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            val kubernetesTemplate = kubernetesParser.parseTemplate(path)
            assertEquals("quarkus-quickstart", kubernetesTemplate?.application)
            assertEquals("quarkus-quickstart:1.0.0", kubernetesTemplate?.imageStreamApplication)
        }
    }

    @Test
    fun parseYamlOpenshiftTemplate() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("template.yaml")?.path
        val kubernetesParser = KubernetesParser()
        path?.let {
            val kubernetesTemplate = kubernetesParser.parseTemplate(it)
            assertEquals("quarkus-blog-api", kubernetesTemplate?.application)
            assertEquals("blog-api:latest", kubernetesTemplate?.imageStreamApplication)
        }
    }

    @Test(expected = GradleException::class)
    fun parseWrongFile() {
        val path = KubernetesParserTest::class.java.classLoader.getResource("wrong.yaml")?.path
        path?.let {
            val kubernetesParser = KubernetesParser()
            kubernetesParser.parseTemplate(path)
        }
    }
}