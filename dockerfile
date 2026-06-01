# Etapa 1: Build (Mantém igual)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Execução (Ajustada para leitura dinâmica de portas)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Removemos o EXPOSE fixo para que a Render gerencie a porta dinamicamente
CMD ["java", "-jar", "app.jar"]