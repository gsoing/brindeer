FROM gradle:7.4.2-jdk17
WORKDIR /home/source/java
COPY . .
RUN gradle publishToMavenLocal --no-daemon
RUN ls -la ~/.gradle/caches
