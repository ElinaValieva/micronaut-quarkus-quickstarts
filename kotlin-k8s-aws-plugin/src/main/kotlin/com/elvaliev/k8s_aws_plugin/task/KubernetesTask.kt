package com.elvaliev.k8s_aws_plugin.task

import com.elvaliev.k8s_aws_plugin.PluginConstant.Companion.Kubernetes
import com.elvaliev.k8s_aws_plugin.extension.KubernetesPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class KubernetesTask : DeployDefaultTask() {

    @Input
    @Optional
    @Option(option = "path", description = "Custom template file, as default used kubernetes.yaml")
    var templatePath: String = "kubernetes.yaml"

    @Input
    @Optional
    @Option(option = "image", description = "Docker registry reference: <user_name>/<image_name>:<tag>")
    var dockerImage: String? = null


    @TaskAction
    fun run() {
        val extension = project.extensions.findByName(Kubernetes) as? KubernetesPluginExtension
        val template = parseValue(extension?.path, templatePath, "template")
        val image = parseValue(extension?.image, dockerImage, "image")
        checkForClient(Client.kubectl)
        template?.let {
            val kubernetesTemplate = getKubernetesTemplate(it)
            val app = parseValue(kubernetesTemplate.application, project.name, "application")
            val port = parseValue(kubernetesTemplate.port, "8080", "port")

            when (checkDeployments("kubectl get deployment $app")) {
                true -> buildDeployment(app, image)
                false -> createDeployment(app, image, template, port)
            }
        }
    }

    private fun createDeployment(
        app: String?,
        image: String?,
        template: String?,
        port: String?
    ) {
        executeCommand("kubectl create deployment $app --image=$image", continueOnError = true)
        executeCommand("kubectl create -f $template --record --save-config", continueOnError = true)
        executeCommand("kubectl expose deployment $app --type=LoadBalancer --port=$port", continueOnError = true)
        executeCommand("kubectl get routes $app -o jsonpath --template={.spec.host}")
    }

    private fun buildDeployment(app: String?, image: String?) {
        executeCommand("kubectl set image deployment/$app  $app=$image")
    }
}