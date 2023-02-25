# Instructions

## dependencies

The Http Service is based on ```gradle 7.6```, ```jdk 17``` and ```springboot 3.0.1``` packages. Usage is also possible on top of ```gradle:7.6.0-jdk17-alpine``` (for compiling) and ```eclipse-temurin:17-jdk-alpine``` (for runtime) docker images.

## development

```bash
gradle bootJar
```

## production

```bash
gradle clean build
java -jar e01 build/libs/e01-0.0.1-SNAPSHOT.jar
```

## container

```bash
docker build -t e01-spring:0.0.1 -f Dockerfile .
docker run -p <port>:<port> e01-spring:0.0.1
```

## http port setting

* default on 8080
* ```SERVER_PORT``` variable, e.g.: SERVER_PORT=8080 java -jar e01-0.0.1-SNAPSHOT.jar
* ```-Dserver.port``` [java]command line option, e.g.: java -Dserver.port=8080 -jar e01-0.0.1-SNAPSHOT.jar
* ```--server.port``` [application]command line option, e.g.: java -jar e01-0.0.1-SNAPSHOT.jar --server.port=8080

## logs

Application logs are colorfully written in standard output. Besides, application logging of the service is stored in ``/var/log/e01-spring`` directory.
