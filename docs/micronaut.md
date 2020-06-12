---
layout: page
title: Micronaut
---

**Micronaut** *positioned as a modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless applications.
Let's create a simple "hello-world" app with multiple platform deployment(OpenShift/Kubernetes/Amazon/Google Cloud Platform) by using simple gradle tasks.*

&nbsp;

## Application
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

## Google Cloud Platform (GCP)
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
entrypoint: 'java -agentpath:/opt/cdbg/cdbg_java_agent.so=--log_dir=/var/log -jar <JAR_FILE_NAME>.jar'
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

## Kubernetes/OpenShift
For deploying to Kubernetes/OpenShift used my plugin [`k8s_aws_plugin`](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin),
which used templates or file configuration list (supported `.json` and `.yaml` formats).
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
