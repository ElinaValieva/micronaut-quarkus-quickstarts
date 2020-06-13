---
layout: page
title: Quarkus
---

**Quarkus** *positioned as "SUPERSONIC SUBATOMIC JAVA" with Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.
Let's create a simple "hello-world" app with multiple platform deployment(OpenShift/Kubernetes/Amazon/Google Cloud Platform) by using simple gradle tasks.*

*Source: [quarkus project](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/quarkus)*

&nbsp;

## Application ‍🚀
Create your first Kotlin application from [code.quarkus.io](https://code.quarkus.io/) with `Gradle` build tools.

Setup necessary dependency and Kotlin plugin in `build.gradle`:
```groovy
plugins {
    id 'org.jetbrains.kotlin.jvm' version "1.3.72"
}

dependencies {
    implementation 'io.quarkus:quarkus-resteasy'
    implementation 'io.quarkus:quarkus-smallrye-health'
    implementation 'io.quarkus:quarkus-config-yaml'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}
```

Create a simple controller:
```kotlin
@Path("/hello")
class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        return "Hello from Quarkus!"
    }
}
```

&nbsp;

## Docker support 🐳
Setup `container-image-docker` dependency in `build.gradle`:
```groovy
dependencies {
    implementation 'io.quarkus:quarkus-container-image-docker'
}
```
Set in `resources/application.yaml` property for enable image build in `quarkusBuild` command:
```yaml
quarkus:
  container-image:
    name: quarkus-quickstart
    tag: latest
    registry: docker.io
    username: <username>
    password: <password>
    push: true
```
Run in command-line: `./gradlew quarkusBuild` or with credentials options:
```
./gradlew quarkusBuild  -Dquarkus.container-image.username=<USERNAME> \
                        -Dquarkus.container-image.password=<PASSWORD> \
                        -Dquarkus.container-image.push=true
```

&nbsp;

## Google Cloud Platform (GCP) 🚩
Prerequisites: Google Cloud Platform account.

### Create project
Create project directly in your GCP or by using command-line tool:

```batch
gcloud projects create PROJECT_ID --set-as-default

gcloud app create --project=PROJECT_ID
```
### Plugin setup
For deploying to GCP used [`appengine-gradle-plugin`](https://github.com/GoogleCloudPlatform/gradle-appengine-plugin).

Setup plugin configuration in project `build.gradle`:
```groovy
buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath 'com.google.cloud.tools:appengine-gradle-plugin:2.2.0'
    }
}

apply plugin: "com.google.cloud.tools.appengine-appyaml"

appengine {
    stage.artifact =
            "${buildDir}/libs/${project.name}-${project.version}.jar"

    deploy {
        stopPreviousVersion = true
        promote = true
        projectId = GCLOUD_CONFIG
        version = GCLOUD_CONFIG
    }

}
```

Parameter `GCLOUD_CONFIG` *(it's `PROJECT_ID` from previous step)* you could set in `build.gradle` directly or in `gradle.properties`.

### Appengine setup
In your project create file: `src/main/appengine/app.yaml`:
```yaml
runtime: java11
instance_class: F4
entrypoint: 'java -agentpath:/opt/cdbg/cdbg_java_agent.so=--log_dir=/var/log
                  -jar <JAR_FILE_NAME>.jar'
```

### Deploy task
Execute gradle-command:
```batch
./gradlew appengineDeploy
```
Browse your application:
```batch
gcloud app browse
```

&nbsp;

## Kubernetes/OpenShift 🚩
Prerequisites: `OC/Kubernetes Client` - already logged in.

For deploying to Kubernetes/OpenShift used my plugin [`k8s_aws_plugin`](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin),
which used templates or file configuration list (supported `.json` and `.yaml` formats) and image-streams for deployment.

### Dependency
Setup kubernetes dependency in `build.gradle`:
```groovy
dependencies {
    implementation 'io.quarkus:quarkus-kubernetes'
}
```
Set in `resources/application.yaml` property for enable build templates for  openshift and kubernetes:
```yaml
quarkus:
  kubernetes:
    deployment-target: kubernetes, openshift
    labels:
      app: quarkus
```
### Plugin setup
Setup plugin configuration in project `build.gradle`:
```groovy
plugins {
                    ...
    id "com.elvaliev.k8s_aws_plugin" version "1.0.4"
}

 apply plugin: "com.elvaliev.k8s_aws_plugin"

 kubernetes {
    image = 'elvaliev/quarkus-quickstart'
 }

 openshift {
    image = 'elvaliev/quarkus-quickstart'
 }
```
> Note: Don't need to specify templates - plugin will recognize it from `build/kubernetes` folder.

> Note: Plugin support command options, it's not necessary to specify extensions "openshift" and "kubernetes"

### Deploy tasks
For deploying to Openshift by using extensions: `./gradlew openshiftDeploy` or by using command line options:
```batch
./gradlew openshiftDeploy --image=elvaliev/quarkus-quickstart
```
For deploying to Kubernetes by using extensions: `./gradlew kubernetesDeploy` or by using command line options:
```batch
./gradlew kubernetesDeploy --image=elvaliev/quarkus-quickstart
```

&nbsp;

## AWS Lambda 🚩
Prerequisites: `SAM Client` - already logged in.

### Dependency
Setup aws dependency in project `build.gradle`:

```groovy
dependencies {
    implementation 'io.quarkus:quarkus-amazon-lambda-http'
}
```

### Plugin setup
For deploying to Kubernetes/OpenShift used my plugin [`k8s_aws_plugin`](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin).
This plugin runs [sam commands from quarkus tutorial](https://quarkus.io/guides/amazon-lambda-http) to simulating and deploying Lambda.

Define `AWS_BUCKET_NAME` and `AWS_STACK_NAME` in `gradle.properties`.

Setup plugin configuration in project `build.gradle`:
```groovy
plugins {
                    ...
    id "com.elvaliev.k8s_aws_plugin" version "1.0.4"
}

apply plugin: "com.elvaliev.k8s_aws_plugin"

aws {
        template = "sam.jvm.yaml"
        bucket = AWS_BUCKET_NAME
        stack = AWS_STACK_NAME
}
```
> Note: Plugin support command options, it's not necessary to specify extension "aws"

### Deploy tasks
Simulate Amazon Lambda Deployment: `./gradlew awsLocal` or by using command-line options:
```batch
./gradlew awsLocal --template="sam.jvm.yaml")
```

This will start a docker container that mimics Amazon’s Lambda’s deployment environment.
Once the environment started you can invoke the example lambda in your browser by going to `http://127.0.0.1:3000` and in the console you’ll see startup messages from the lambda.

Deploy to AWS: `./gradlew awsPackage` or by using command-line options:
```batch
./gradlew awsPackage --template="sam.jvm.yaml" \
                     --bucket=AWS_BUCKET_NAME \
                     --stack=AWS_STACK_NAME
```
