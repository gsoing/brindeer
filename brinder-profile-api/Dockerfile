FROM adoptopenjdk/openjdk11

RUN mkdir /app

COPY "build/libs/brinder-profile-api-1.0.0-SNAPSHOT.jar" /app/brinder-profile-api-1.0.0-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/brinder-profile-api-1.0.0-SNAPSHOT.jar"]