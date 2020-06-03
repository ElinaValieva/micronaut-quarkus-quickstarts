package com.elvaliev.k8s_aws_plugin.extension

open class AwsPluginExtension {

    var samTemplate: String? = null

    var s3Bucket: String? = null

    var stackName: String? = null

    open fun print(): String {
        var info = ""
        samTemplate?.let { info += "Template = $samTemplate " }
        s3Bucket?.let { info += "Bucket = $s3Bucket " }
        stackName?.let { info += "Stack = $stackName" }
        return info
    }
}