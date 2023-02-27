# docker-kubernetese-exercise

## intro



## excercise one

two containerized(dockerized) application using [dotnet 7](../../tree/master/E01/) and [spring 3](../../tree/master/E01-spring/)

the application is a simple http service with simple logging facility and port setting

* /hello: returns Hello + \<name query param value\>; Hello Stranger if value not provided
* /auther: returns author's name hardcoded!

## excercise two

application orchestration configuration in k8s platform using kubernetse manifests and kind cluster configuration [separately](../../tree/master/E02-kind-cluster/) and in [springboot application](../../tree/master/E01-spring/)
