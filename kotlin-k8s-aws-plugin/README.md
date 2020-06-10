## Gradle Plugin to Deploying application on Kubernetes/OpenShift/Aws Lambda
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![K*S_AWS_Plugin](https://img.shields.io/badge/gradle%20plugin-1.0.3-blue.svg)](https://plugins.gradle.org/plugin/com.elvaliev.k8s_aws_plugin)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg) ](https://kotlinlang.org/)

> From `1.0.3` plugin support command options and plugin extensions. 

> Creation and build deployment for application by using templates(as a template or element listing) for Kubernetes and OpenShift using one common command. 

## Prerequisites :exclamation:
- `OC` - for deploying to OpenShift
- `Kubectl` - for deploying to Kubernetes
- `SAM CLI` - for deploying to Amazon

&nbsp;
## Usage :hammer:
Groovy using the plugins DSL:
```groovy
plugins {
  id "com.elvaliev.k8s_aws_plugin" version "1.0.3"
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
    classpath "gradle.plugin.com.elvaliev:k8s_aws_plugin:1.0.3"
  }
}

apply plugin: "com.elvaliev.k8s_aws_plugin"
```
&nbsp;
## Kubernetes :triangular_flag_on_post:

|Extension & Options|Description|
|--|--|
|`template`|*optional* - path to your kubernetes `yaml` template or configurations file. As default plugin used file with name `kubernetes.yaml` from your project directory or `build/kubernetes` dicrectory. For overriding purposes - define your template in the project root.|
|`image`|docker registry reference with format `<docker_registry>/<user_name>/<image>:<tag>`|

#### Using extensions:
Configure kubernetes extension in your `gradle.build`:
```groovy
kubernetes {
    template = 'k8s/kubernetes.yml'
    image = 'elvaliev/micronaut-quickstart'
}
```
Execute gradle task: **`./gradlew kubernetesDeploy`**
&nbsp;

#### Using command options
Execute gradle task: **`./gradlew kubernetesDeploy --template=k8s/kubernetes.yml --image=elvaliev/micronaut-quickstart`**

&nbsp;
## OpenShift :triangular_flag_on_post:

|Extension & Options|Description|
|--|--|
|`template`|*optional* - path to your openshift `yaml` template or configurations file. As default plugin used file with name `openshift.yaml` from your project directory or `build/kubernetes` dicrectory. For overriding purposes - define your template in the project root.|
|`image`|docker registry reference with format `<docker_registry>/<user_name>/<image>:<tag>`|
|`jar`|*optional* - path to your jar. As default used path from `libs`|

#### Using extensions:
Configure openshift extension in your `gradle.build`:
```groovy
openshift {
    template = 'k8s/openshift.yml'
    image = 'elvaliev/micronaut-quickstart'
}
```
Execute gradle task: **`./gradlew openshiftDeploy`**

&nbsp;

#### Using command options
Execute gradle task: **`./gradlew openshiftDeploy --template=k8s/kubernetes.yml --image=elvaliev/micronaut-quickstart`**

&nbsp;
## AWS Lambda :triangular_flag_on_post:

|Extension Parameters or Command Options|Description|
|--|--|
|`template`|*optional* - path to your template. As default plugin used file with name `template.yaml` from your project directory. For overriden perpose - define your template in project root.|
|`bucket`|bucket name|
|`stack`|stack name|

#### Using extensions:
Configure aws extension in your `gradle.build`:
```groovy
aws {
    template = "build\\sam.jvm.yaml"
    bucket = AWS_BUCKET_NAME
    stack = AWS_STACK_NAME
}
```
Start your lambda: **`./gradlew awsLocal`** - it's similar as `sam local start-api` command. 

This will start a docker container that mimics Amazon’s Lambda’s deployment environment. Once the environment is started you can invoke the example lambda in your browser by going to http://127.0.0.1:3000 and in the console you’ll see startup messages from the lambda.

Package your lambda: **`./gradlew awsPackage`**

&nbsp;

#### Using command options:
Start your lambda: **`./gradlew awsLocal --template=build/sam.jvm.yaml`**

Package your lambda: **`./gradlew awsPackage --template=build/sam.jvm.yaml --bucket = AWS_BUCKET_NAME --stack = AWS_STACK_NAME`**

This plugin runs [sam commands](https://quarkus.io/guides/amazon-lambda-http) to simulating and deploying Lambda.
