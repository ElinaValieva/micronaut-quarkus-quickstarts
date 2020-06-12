---
layout: page
title: Micronaut
---

**Micronaut** *positioned as a modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless applications.
Let's create a simple "hello-world" app with multiple platform deployment(OpenShift/Kubernetes/Amazon/Google Cloud Platform) by using simple gradle tasks.*

*Source: [micronaut project](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/micronaut)*

&nbsp;

## Application ‍🚀
Create your first Kotlin application:
```batch
mn create-app com.micronaut.complete --lang=kotlin
```

With a simple controller:

```kotlin
@Controller
class GreetingController {

    @Get(uris = ["/hello", "/greeting"])
    fun greeting(): String {
        return "Hello from Micronaut!"
    }
}
```

&nbsp;

## Docker support 🐳
Setup `jib` for docker support in project `build.gradle`:
```groovy
plugins {
    id 'com.google.cloud.tools.jib' version '2.3.0'
}

jib {
    to {
        image = 'elvaliev/micronaut-quickstart'
        auth {
            username = DOCKERHUB_USERNAME
            password = DOCKERHUB_PASSWORD
        }
    }
}
```
Run in command line: `.gradlew jib`

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
### Template setup
Define in your project file for OpenShift and/or Kubernetes deployment as a "configuration list":
```yaml
    ...
---
apiVersion: "v1"
kind: "Service"
metadata:
  ...
  name: "micronaut-quickstart"
spec:
  ports:
  - name: "http"
    port: 8090
    targetPort: 8090
    ...
```
Or "template":
```yaml
apiVersion: v1
kind: Template
metadata:
  name: my-template
objects:
- kind: DeploymentConfig
  apiVersion: v1
  metadata:
      labels:
      ...
```
### Plugin setup
Setup plugin configuration in project `build.gradle`:
```groovy
plugins {
                    ...
    id "com.elvaliev.k8s_aws_plugin" version "1.0.3"
}

 apply plugin: "com.elvaliev.k8s_aws_plugin"

 kubernetes {
    template = 'k8s/kubernetes.yml'
    image = 'elvaliev/micronaut-quickstart'
 }

 openshift {
    template = 'k8s/openshift.yml'
    image = 'elvaliev/micronaut-quickstart'
 }
```
> Note: If you named template as "openshift" and "kubernetes" in project root - don't need to specify templates. Plugin will recognize it.

> Note: Plugin support command options, it's not necessary to specify extensions "openshift" and "kubernetes"

### Deploy tasks
For deploying to Openshift by using extensions: `./gradlew openshiftDeploy` or by using command line options:
```batch
./gradlew openshiftDeploy --template=k8s/openshift.yml --image=elvaliev/micronaut-quickstart
```
For deploying to Kubernetes by using extensions: `./gradlew kubernetesDeploy` or by using command line options:
```batch
./gradlew kubernetesDeploy --template=k8s/kubernetes.yml --image=elvaliev/micronaut-quickstart
```

&nbsp;

## AWS Lambda 🚩
Prerequisites: `AWS Client` - already logged in.

### Dependency
Setup aws dependency in project `build.gradle`:

```groovy
dependencies {
    implementation "io.micronaut:micronaut-function-aws"
}
```
### Serveless function
For deploying as AWS Lambda setup serveless-function:
```kotlin
@FunctionBean("hello-micronaut")
class HelloKotlinFunction : Supplier<String> {

    override fun get(): String {
        return "Hello from Serverless Micronaut Functions!"
    }
}
```
### Plugin setup
For deploying serverless functions used [gradle aws plugin](https://github.com/classmethod/gradle-aws-plugin). Define `AWS_ROLE` and `AWS_REGION` in `gradle.properties`.

Setup plugin configuration in project `build.gradle`:
```groovy
import com.amazonaws.services.lambda.model.InvocationType
import com.amazonaws.services.lambda.model.Runtime
import jp.classmethod.aws.gradle.lambda.*


plugins {
    ...
    id "jp.classmethod.aws.lambda" version "0.41"
}
apply plugin: 'jp.classmethod.aws.lambda'

aws {
    profileName = "default"
    region = AWS_REGION
}

lambda {
    region = AWS_REGION
}

task zip(type: Zip) {
    from "function/"
    destinationDir file("build")
}

mainClassName = "io.micronaut.function.executor.FunctionApplication"

task deploy(type: AWSLambdaMigrateFunctionTask, dependsOn: shadowJar) {
    functionName = "hello-micronaut"
    handler = "io.micronaut.function.aws.MicronautRequestStreamHandler"
    role = AWS_ROLE
    runtime = Runtime.Java8
    zipFile = shadowJar.archivePath
    memorySize = 256
    timeout = 60
}

task invoke(type: AWSLambdaInvokeTask) {
    functionName = "hello-micronaut"
    invocationType = InvocationType.RequestResponse
    payload = ''
    doLast {
        println "Lambda function result: " + new String(invokeResult.payload.array())
    }
}
```
### Deploy tasks
Deploy to AWS Lambda: `./gradlew deploy` and invoke Lambda: `./gradlew invoke`.

