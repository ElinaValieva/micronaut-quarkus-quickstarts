package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class KubernetesDeployTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "application", description = "Application name")
    var application: String? = project.name

    @Input
    @Optional
    @Option(option = "path", description = "Custom template file, as default used kubernetes.yaml")
    var templatePath: String = "kubernetes.yaml"

    @Input
    @Optional
    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>")
    var dockerImage: String? = null

    @Input
    @Optional
    @Option(option = "port", description = "Application port, where should be exposed")
    var port: String? = null


    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Kubernetes) as? KubernetesPluginExtension
        val app = parseValue(extension?.application, application, "application")
        var template = parseValue(extension?.path, templatePath, "template")
        val image = parseValue(extension?.image, dockerImage, "image")
        val port = parseValue(extension?.port, port, "port")
        checkForClient(Client.kubectl)
        template?.let {
            template = retrieveFile(it)
            executeCommand("kubectl create deployment $app --image=$image", continueOnError = true)
            executeCommand("kubectl create -f $template --record --save-config", continueOnError = true)
            executeCommand("kubectl expose deployment $app --type=LoadBalancer --port=$port", continueOnError = true)
            executeCommand("kubectl get routes $app -o jsonpath --template={.spec.host}")
        }
    }
}