package com.elvaliev.k8s_aws_plugin.extension

open class KubernetesPluginExtension {

    var application: String? = null

    var path: String? = null

    var image: String? = null

    var port: String? = null

    open fun print(): String {
        var info = ""
        application?.let { info += "Application = $application " }
        path?.let { info += "Template = $path " }
        image?.let { info += "Image = $image " }
        port?.let { info += "Port = $port" }
        return info
    }
}