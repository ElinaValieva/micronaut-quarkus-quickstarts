# Quarkus Quickstart Project
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstart/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)
![Update Docker Hub Description](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Update%20Docker%20Hub%20Description/badge.svg?branch=master)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/google/cloud/tools/jib/com.google.cloud.tools.jib.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=gradle)](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg) ](https://kotlinlang.org/)
[![Quarkus](https://img.shields.io/badge/quarkus-1.5.1-yellow.svg)](https://quarkus.io/)
> Simple `hello-world` project with different platform deployment using gradle tasks: 

```bash
Gradle Tasks
  │
  └─────── appengine - Deployment to Google Cloud Platform
  |             |       ..
  │             └──── appengineDeploy
  │      
  └─────── aws - Deployment to AWS Lambda
  │             ├──── awsLocal 
  │             └──── awsPackage
  │      
  └─────── k8s - Deployment to Kubernetes and OpenShift
                ├──── kubernetesDeploy
                └──── openshiftDeploy
```
&nbsp;

## How to start 🐳
```shell
docker run elvaliev/quarkus-quickstart -p8090:8090

curl https://localhost:8090/hello
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
**Prerequisites:** `OpenShift Client` - login with client. 

For creating and deploying on changes application on `OpenShift` used gradle plugin [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin). These scripts could be used directly or run as a gradle tasks (see `k8s` group in gradle tasks).

For deploying (creating a new application or creating new build) on Openshift: 
```gradle
./gradlew openshiftDeploy
```
&nbsp;

## Kubernetes 🚩
**Prerequisites:** `Kubernetes Client` - login with client.

For creating and deploying on changes application on `Kubernetes` used gradle plugin [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin). These scripts could be used directly or run as a gradle tasks (see `k8s` group in gradle tasks).

For deploying (creating a new application or creating new build) on Kubernetes: 
```gradle
./gradlew kubernetesDeploy
```
&nbsp;

## AWS Lambda 🚩
**Prerequisites:** `SAM CLI` - login with client and `Docker`. 

For creating and package lambda used gradle plugin [k8s_aws_plugin](https://github.com/ElinaValieva/micronaut-quickstarts/tree/master/kotlin-k8s-aws-plugin). These scripts could be used directly or run as a gradle tasks (see `aws` group in gradle tasks). 

Simulate Amazon Lambda Deployment: 
```gradle
./gradlew awsLocal
```
This will start a docker container that mimics Amazon’s Lambda’s deployment environment. Once the environment is started you can invoke the example lambda in your browser by going to http://127.0.0.1:3000 and in the console you’ll see startup messages from the lambda. 

Deploy to AWS:
```gradle
./gradlew awsPackage
```

This plugin runs [sam commands](https://quarkus.io/guides/amazon-lambda-http) to simulating and deploying Lambda.
