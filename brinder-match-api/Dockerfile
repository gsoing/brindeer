FROM maven:3.8.4-openjdk-17 AS build

ENV MAVEN_CONFIG ""
# Définition du répertoire de travail
WORKDIR /app

# Copie du fichier pom.xml et Maven Wrapper
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Résolution des dépendances Maven
RUN ./mvnw dependency:resolve

# Copie du code source et construction du projet
COPY src src
RUN ./mvnw package

# Deuxième étape de construction pour l'artifact final
FROM maven:3.8.4-openjdk-17
WORKDIR /app
COPY --from=build /app/target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]