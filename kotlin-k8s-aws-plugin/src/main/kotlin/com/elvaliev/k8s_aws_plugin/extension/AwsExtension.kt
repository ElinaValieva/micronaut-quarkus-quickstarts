package com.elvaliev.k8s_aws_plugin.extension

import groovy.lang.GroovyObjectSupport
import org.gradle.api.tasks.Input

open class AwsExtension : GroovyObjectSupport() {

    @Input
    var samTemplate: String = ""

    @Input
    var s3Bucket: String = ""

    @Input
    var stackName: String = ""
}