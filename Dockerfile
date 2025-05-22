# Étape 1 : Compilation de l'application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copie du pom.xml et récupération des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie du code source complet
COPY src ./src

# Compilation sans les tests
RUN mvn clean package -DskipTests -B

# Étape 2 : Image de runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copie du jar généré depuis l'étape build
# Remplace bien le nom par le bon jar exact, sinon utilise *.jar
COPY --from=build /app/target/*.jar app.jar

# Expose le bon port (ton app écoute sur 8082)
EXPOSE 8082

# Lancement de l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
