package com.elvaliev.k8s_aws_plugin.extension

import groovy.lang.GroovyObjectSupport
import org.gradle.api.tasks.Input

open class KubernetesExtension : GroovyObjectSupport() {

    @Input
    var application: String = ""

    @Input
    var path: String = ""

    @Input
    var image: String = ""

    @Input
    var port: Int = 8080
}