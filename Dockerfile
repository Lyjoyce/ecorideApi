# Étape 1 : Build de l'application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copie du pom.xml et téléchargement des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie du code source et build
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copie du .jar depuis l'image précédente
COPY --from=build /app/target/ecorideApi-0.0.1-SNAPSHOT.jar app.jar

# Port exposé (modifie selon besoin, par défaut 8080)
EXPOSE 8080

# Démarrage de l'application (profil via env SPRING_PROFILES_ACTIVE)
ENTRYPOINT ["java", "-jar", "app.jar"]
