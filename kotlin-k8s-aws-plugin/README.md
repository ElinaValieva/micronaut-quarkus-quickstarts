## Gradle Plugin to Deploying application on Kubernetes/OpenShift/Aws Lambda
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![Gradle Plugin Release](https://img.shields.io/badge/gradle%20plugin-1.0.2-blue.svg)](https://plugins.gradle.org/plugin/com.elvaliev.k8s_aws_plugin)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg) ](https://kotlinlang.org/)

## Prerequisites :exclamation:
- `OC Client` - for deploying to OpenShift
- `Kubectl` - for deploying to Kubernetes
- `SAM CLI` - for deploying to Amazon

&nbsp;
## Usage :hammer:
Groovy using the plugins DSL:
```groovy
plugins {
  id "com.elvaliev.k8s_aws_plugin" version "1.0.2"
}
```

Using legacy plugin application:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.elvaliev:k8s_aws_plugin:1.0.2"
  }
}

apply plugin: "com.elvaliev.k8s_aws_plugin"
```
&nbsp;
## Kubernetes :triangular_flag_on_post:

|Parameters|Description|
|--|--|
|`path`|path to your kubernetes `yaml` template|
|`application`|name of your application|
|`image`|docker registry reference|
|`port`|exposed port|
### Kubernetes Deploy
Configure kubernetes extension in your `gradle.build`:
```groovy
kubernetes {
    path = 'k8s/kubernetes.yml'
    image = 'elvaliev/micronaut-quickstart'
    application = 'micronaut-quickstart'
    port = 8090
}
```
**Execute gradle task: `./gradlew kubernetesDeploy`**
### Kubernetes Redeploy
Configure kubernetes extension in your `gradle.build`:
```groovy
kubernetes {
    application = 'micronaut-quickstart'
    image = 'elvaliev/micronaut-quickstart'
}
```
**Execute gradle task: `./gradlew kubernetesRedeploy`**

&nbsp;
## OpenShift :triangular_flag_on_post:

|Parameters|Description|
|--|--|
|`path`|path to your openshift `yaml` template|
|`application`|name of your application|
|`image`|docker registry reference|

### OpenShift Deploy
Configure openshift extension in your `gradle.build`:
```groovy
openshift {
    path = 'k8s/openshift.yml'
    application = 'micronaut-quickstart'
    image = 'elvaliev/micronaut-quickstart'
}
```
**Execute gradle task: `./gradlew openshiftDeploy`**
### OpenShift Redeploy
Configure openshift extension in your `gradle.build`:
```groovy
openshift {
    application = 'micronaut-quickstart'
    image = 'elvaliev/micronaut-quickstart'
}
```
**Execute gradle task: `./gradlew openshiftRedeploy`**

&nbsp;
## AWS Lambda :triangular_flag_on_post:

|Parameters|Description|
|--|--|
|`samTemplate`|path to your template|
|`s3Bucket`|bucket name|
|`stackName`|stack name|

### AWS Lambda
Configure aws extension in your `gradle.build`:
```groovy
aws {
    samTemplate = file("${project.buildDir}\\sam.jvm.yaml")
    s3Bucket = AWS_BUCKET_NAME
    stackName = AWS_STACK_NAME
}
```

**Start your lambda:** `./gradlew awsLocal` - it's similar as `sam local start-api` command

**Package your lambda:** './gradlew awsPackage`