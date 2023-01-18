# Stage 1: build environment image
FROM gradle:7.6.0-jdk17-alpine as build-env
COPY . /src
WORKDIR /src
RUN gradle clean build

# Stage 2: runtime environment image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build-env "/src/build/libs/e01-0.0.1-SNAPSHOT.jar" .
ENTRYPOINT [ "java", "-jar", "e01-0.0.1-SNAPSHOT.jar"]