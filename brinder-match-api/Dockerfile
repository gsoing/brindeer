FROM brinder-common as compile
ADD . /home/source/java
WORKDIR /home/source/java

RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/application.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/application/java/application.jar"]