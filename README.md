# micronaut-quickstart
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstart/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)
![Java CI with Gradle](https://github.com/ElinaValieva/micronaut-quickstarts/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)


## Google Cloud Platform (GCP) 
**Prerequisites:** `Google Cloud Platform` account. To create your `GCP` project and make it ready for using `App Engine`, in particular, those commands:
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
