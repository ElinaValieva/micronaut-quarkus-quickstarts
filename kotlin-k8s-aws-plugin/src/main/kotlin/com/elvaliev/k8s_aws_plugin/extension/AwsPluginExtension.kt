package com.elvaliev.k8s_aws_plugin.extension

open class AwsPluginExtension {

    var template: String? = null

    var bucket: String? = null

    var stack: String? = null

    open fun print(): String {
        var info = ""
        template?.let { info += "Template = $template " }
        bucket?.let { info += "Bucket = $bucket " }
        stack?.let { info += "Stack = $stack" }
        return info
    }
}