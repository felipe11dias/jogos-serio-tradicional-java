# Stage 1: Builda a aplicação 
FROM maven:3.8.4-jdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src src/
RUN mvn clean package

# Stage 2: Executa a aplicação 
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
