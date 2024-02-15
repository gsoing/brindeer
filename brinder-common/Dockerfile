FROM gradle:jdk17-alpine

WORKDIR /home/source/java
COPY . .
RUN gradle publishToMavenLocal --no-daemon
RUN ls -la ~/.gradle/caches

