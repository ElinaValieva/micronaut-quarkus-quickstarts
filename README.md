# Micronaut Quickstart Project
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstart/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)
![Update Docker Hub Description](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Update%20Docker%20Hub%20Description/badge.svg?branch=master)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/google/cloud/tools/jib/com.google.cloud.tools.jib.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=gradle)](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg) ](https://kotlinlang.org/)
> Simple `hello-world` project with different platform deployment

&nbsp;
## Google Cloud Platform (GCP) 🚩
**Prerequisites:** `Google Cloud Platform` account. 

To create your `GCP` project and make it ready for using `App Engine`, in particular, those commands:
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

For creating and deploying on changes application on `OpenShift` prepared platform-independent scripts and application template in `src/main/openshift`. These scripts could be used directly or run as a gradle tasks.

For creating a new application on Openshift: 
```gradle
./gradle deployOpenshift
```
For redeploying application on Openshift: 
```gradle
./gradlew redeployOpenshift
```
&nbsp;

## Kubernetes 🚩
**Prerequisites:** `Kubernetes Client` - login with client. 

For creating and deploying on changes application on `Kubernetes` prepared platform-independent scripts and application template in `src/main/kubernetes`. These scripts could be used directly or run as a gradle tasks.

For creating a new application on Kubernetes: 
```gradle
./gradle deployKubernetes
```
For redeploying application on Kubernetes: 
```gradle
./gradlew redeployKubernetes
```

## AWS Lambda 🚩
**Prerequisites:** `AWS Client` - login with client. 

For creating and deploying on changes application as `Amazom Lambda` prepared platform-independent scripts and application template in `src/main/aws`. These scripts could be used directly or run as a gradle tasks. For [aws function used GraalVM image packaging](https://github.com/micronaut-guides/micronaut-function-graalvm-aws-lambda-gateway). 

For running Lambda Locally via SAM: 
```gradle
./gradlew runAws
```
Deploying to AWS Lambda: 
```gradle
./gradlew deployAws
```

