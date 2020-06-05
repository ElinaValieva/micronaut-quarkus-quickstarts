plugins {
    maven
    `java-gradle-plugin`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.12.0"
}

group = "com.elvaliev"
version = "1.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly(localGroovy())
    compileOnly(gradleApi())
    testImplementation("junit:junit:4.13")
}

gradlePlugin {
    (plugins) {
        register("k8sPlugin") {
            id = "com.elvaliev.k8s_aws_plugin"
            description = "Plugin to deploying Kubernetes/Openshift Application"
            displayName = "Kubernetes/OpenShift deployment plugin"
            implementationClass = "com.elvaliev.k8s_aws_plugin.K8SPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/ElinaValieva/micronaut-quickstarts"
    vcsUrl = "https://github.com/ElinaValieva/micronaut-quickstarts.git"
    tags = listOf("openshift", "kubernetes", "aws", "serverless")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}