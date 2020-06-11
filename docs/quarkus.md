---
layout: page
title: Quarkus
---
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
