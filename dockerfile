# Usa a imagem oficial do Java 21 como base para o build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]

# Usa uma imagem oficial que já vem com Maven e Java 21 juntos
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# O restante do seu Dockerfile (FROM eclipse-temurin:21-jre-alpine...) continua igual abaixo