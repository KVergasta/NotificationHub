# Etapa 1: Build (Mantém igual)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Execução (Ajustada para fixar a porta 8000)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 1. Avisa a Render que a porta usada é a 8000
EXPOSE 8000

# 2. Força o Java a injetar a porta 8000 na inicialização do Spring
ENTRYPOINT ["java", "-Dserver.port=8000", "-jar", "app.jar"]