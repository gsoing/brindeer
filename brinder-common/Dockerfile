FROM gradle:7.4.2-jdk17
# FROM gradle:jdk17-alpine doen't work on M1 Pro

WORKDIR /home/source/java
COPY . .
RUN gradle publishToMavenLocal --no-daemon
RUN ls -la ~/.gradle/caches

