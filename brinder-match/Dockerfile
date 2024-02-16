FROM gradle:latest as compile
ADD . /home/source/java
WORKDIR /home/source/java

RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/brinder-profile-api-1.0.0.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/source/java/build/libs/brinder-profile-api-1.0.0.jar"]
