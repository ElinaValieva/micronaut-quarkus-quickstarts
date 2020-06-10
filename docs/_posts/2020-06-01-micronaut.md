---
layout: post
title: Micronaut QuickStart Project
categories: junk
author:
- Elina Valieva
meta: "Springfield"
---
> Simple `hello-world` project with different platform deployment using gradle tasks: 

{% highlight bash %}
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
{% endhighlight %}


[jekyll-docs]: http://jekyllrb.com/docs/home
[jekyll-gh]:   https://github.com/jekyll/jekyll
[jekyll-talk]: https://talk.jekyllrb.com/
