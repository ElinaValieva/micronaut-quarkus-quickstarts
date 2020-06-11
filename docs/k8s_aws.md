---
layout: page
title: K8S AWS Plugin
---
> k8s_aws_plugin - Gradle Plugin to Deploying application on Kubernetes/OpenShift/Aws Lambda

## Prerequisites ❗
- `OC Client` - for deploying to OpenShift
- `Kubectl` - for deploying to Kubernetes
- `SAM CLI` - for deploying to Amazon

## Usage 🔨
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
