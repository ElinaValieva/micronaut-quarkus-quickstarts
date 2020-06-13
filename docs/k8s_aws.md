---
layout: page
title: K8S AWS Plugin
---

*[k8s_aws_plugin](https://plugins.gradle.org/plugin/com.elvaliev.k8s_aws_plugin) - Gradle plugin to deploying application on Kubernetes/OpenShift/Aws Lambda*

*Source: [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin)*

&nbsp;

## Prerequisites ❗
- `OC` - for deploying to OpenShift
- `Kubectl` - for deploying to Kubernetes
- `SAM CLI` - for deploying to Amazon

&nbsp;

## Usage 🔨
Groovy using the plugins DSL:
```groovy
plugins {
  id "com.elvaliev.k8s_aws_plugin" version "1.0.4"
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
    classpath "gradle.plugin.com.elvaliev:k8s_aws_plugin:1.0.4"
  }
}

apply plugin: "com.elvaliev.k8s_aws_plugin"
```
&nbsp;
## Kubernetes 🚩

|Extension & Options|Description|
|--|--|
|`template`|*optional* - path to your kubernetes `yml`/`json` template or configurations file. As default plugin used file with name `kubernetes.yml` from your project directory or `build/kubernetes` dicrectory. For overriding purposes - define your template in the project root.|
|`image`|docker registry reference with format `<docker_registry>/<user_name>/<image>:<tag>`|

**Using extensions:**

Configure kubernetes extension in your `gradle.build`:
```groovy
kubernetes {
    template = 'k8s/kubernetes.yml'
    image = 'elvaliev/micronaut-quickstart'
}
```
Execute gradle task: `./gradlew kubernetesDeploy`

**Using command options:**
```batch
./gradlew kubernetesDeploy --template="k8s/kubernetes.yml" \
                           --image="elvaliev/micronaut-quickstart"
```

&nbsp;
## OpenShift 🚩

|Extension & Options|Description|
|--|--|
|`template`|*optional* - path to your openshift `yml`/`json` template or configurations file. As default plugin used file with name `openshift.yml` from your project directory or `build/kubernetes` dicrectory. For overriding purposes - define your template in the project root.|
|`image`|docker registry reference with format `<docker_registry>/<user_name>/<image>:<tag>`|
|`jar`|*optional* - path to your jar. As default used path from `libs`|

**Using extensions:**

Configure openshift extension in your `gradle.build`:
```groovy
openshift {
    template = 'k8s/openshift.yml'
    image = 'elvaliev/micronaut-quickstart'
}
```
Execute gradle task: `./gradlew openshiftDeploy`

**Using command options:**
```batch
./gradlew openshiftDeploy --template="k8s/kubernetes.yml" \
                          --image="elvaliev/micronaut-quickstart"
```

&nbsp;
## AWS Lambda 🚩

|Extension Parameters or Command Options|Description|
|--|--|
|`template`|*optional* - path to your template. As default plugin used file with name `template.yml` from your project directory. For overriden perpose - define your template in project root.|
|`bucket`|bucket name|
|`stack`|stack name|

**Using extensions:**

Configure aws extension in your `gradle.build`:
```groovy
aws {
    template = "sam.jvm.yaml"
    bucket = AWS_BUCKET_NAME
    stack = AWS_STACK_NAME
}
```
Start your lambda: `./gradlew awsLocal` - it's similar as `sam local start-api` command.

This will start a docker container that mimics Amazon’s Lambda’s deployment environment.
Once the environment started you can invoke the example lambda in your browser by going to `http://127.0.0.1:3000` and in the console you’ll see startup messages from the lambda.

Package your lambda: `./gradlew awsPackage`

**Using command options:**

Start your lambda:
```batch
./gradlew awsLocal --template="sam.jvm.yaml"
```

Package your lambda:
```batch
./gradlew awsPackage --template="sam.jvm.yaml" \
                     --bucket=AWS_BUCKET_NAME \
                     --stack=AWS_STACK_NAME
```

This plugin runs [sam commands](https://quarkus.io/guides/amazon-lambda-http) to simulating and deploying Lambda.
