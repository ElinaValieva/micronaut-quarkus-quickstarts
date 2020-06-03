package com.elvaliev.k8s_aws_plugin.extension

open class AwsPluginExtension {

    var samTemplate: String = "undefined"

    var s3Bucket: String = "undefined"

    var stackName: String = "undefined"

    open fun print(): String {
        return "Template = $samTemplate, bucket = $s3Bucket, stack = $stackName"
    }
}