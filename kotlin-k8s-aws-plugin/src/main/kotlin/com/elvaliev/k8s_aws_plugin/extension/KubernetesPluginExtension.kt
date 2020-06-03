package com.elvaliev.k8s_aws_plugin.extension

open class KubernetesPluginExtension {

    var application: String = "undefined"

    var path: String = "undefined"

    var image: String = "undefined"

    var port: String = "8080"

    open fun print(): String {
        return "Application = $application, template = $path, image = $image, port = $port"
    }
}