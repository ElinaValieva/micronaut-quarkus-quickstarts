# Micronaut Quickstart Project
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstart/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)
![Update Docker Hub Description](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Update%20Docker%20Hub%20Description/badge.svg?branch=master)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/google/cloud/tools/jib/com.google.cloud.tools.jib.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=gradle)](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg) ](https://kotlinlang.org/)
[![Micronaut](https://img.shields.io/badge/micronaut-2.0.0-yellow.svg)](https://micronaut.io/)
> Simple `hello-world` project with different platform deployment using gradle tasks: 

```bash
Gradle Tasks
  │
  └─────── appengine - Deployment to Google Cloud Platform
  |             |       ..
  │             └──── appengineDeploy
  │      
  └─────── aws - Deployment to AWS Lambda
  │             ├──── deploy 
  |             |       ..
  │             └──── invoke
  │      
  └─────── k8s - Deployment to Kubernetes and OpenShift
                ├──── kubernetesDeploy
                └──── openshiftDeploy
```
&nbsp;

## How to start 🐳
```shell
docker run elvaliev/micronaut-quickstart -p8090:8090

curl https://localhost:8090/greeting
```

&nbsp;
## Google Cloud Platform (GCP) 🚩
**Prerequisites:** `Google Cloud Platform` account. Define `appengine/app.yaml` with entry point. 

For deploying to `GCP` used [gradle appengine plugin](https://github.com/GoogleCloudPlatform/gradle-appengine-plugin). To create your `GCP` project and make it ready for using `App Engine`, in particular, those commands:
```shell
gcloud projects create PROJECT_ID --set-as-default

gcloud app create --project=PROJECT_ID
```

For deploying to `GCP` define your `PROJECT_ID` in `gradle.properties` from command line arguments `GCLOUD_CONFIG`: 

```gradle
./gradlew appengineDeploy -PGCLOUD_CONFIG=PROJECT_ID
```
Browse application:
```
gcloud app browse
```
&nbsp;

## OpenShift 🚩
**Prerequisites:** `OpenShift Client` - login with client. Application template (see more `k8s/openshift.yaml`. 

For creating and deploying on changes application on `OpenShift` used gradle plugin [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin). These scripts could be used directly or run as a gradle tasks (see `k8s` group in gradle tasks).

For deploying (creating a new application or creating new build) on Openshift: 
```gradle
./gradlew openshiftDeploy
```
&nbsp;

## Kubernetes 🚩
**Prerequisites:** `Kubernetes Client` - login with client. Application template (see more `k8s/kubernetes.yaml`.

For creating and deploying on changes application on `Kubernetes` used gradle plugin [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin). These scripts could be used directly or run as a gradle tasks (see `k8s` group in gradle tasks).

For deploying (creating a new application or creating new build) on Kubernetes: 
```gradle
./gradlew kubernetesDeploy
```
&nbsp;

## AWS Lambda 🚩
**Prerequisites:** `AWS Client` - login with client. Two approaches: using [GraalVM image packaging](https://github.com/micronaut-guides/micronaut-function-graalvm-aws-lambda-gateway) and Serverless Functions.

### Serverless Functions
For deploying serverless functions used [gradle aws plugin](https://github.com/classmethod/gradle-aws-plugin). Define `AWS_ROLE` and `AWS_REGION` in `gradle.properties`. 
> For using aws funtions necessary to enable parameter `enableAws` as `true` in `gradle.properties` and then run `./gradlew build -PenableAws=true`

Deploying to AWS Lambda: 
```gradle
./gradlew deploy
```

Invoke Lambda: 
```gradle
./gradlew invoke
```

Delete function:
```gradle
./gradlew deleteFunction
```

Update alias: 
```gradle
./gradlew updateAlias
```


### GraalVM image
For creating and deploying on changes application as `Amazom Lambda` prepared script `deploy.sh` and Dockerfile in `deployment/aws` directory. Run script with yours `ROLE_NAME` and `S3_BUCKET` arguments:
```shell
./deploy ROLE_NAME S3_BUCKET
```