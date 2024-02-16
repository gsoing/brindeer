FROM gradle:latest as compile
COPY . /app
WORKDIR /app
RUN ls
USER root
RUN chown -R gradle /app
USER gradle
RUN gradle clean build

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=compile "/app/build/libs/brinder-match-0.0.1-SNAPSHOT.jar" /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "brinder-match-0.0.1-SNAPSHOT.jar"]