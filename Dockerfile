Dockerfile
Copy code
# Estágio 1: Construir o aplicativo Spring Boot com Java
FROM maven:3.8.4 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Estágio 2: Construir a imagem final
FROM openjdk:11-jre-slim

# Defina variáveis de ambiente para a configuração do banco de dados PostgreSQL
ENV POSTGRES_DB=jogos_serios_tradicionais_5cd4
ENV POSTGRES_USER=root
ENV POSTGRES_PASSWORD=Q2OCkY0DwcR61R4mhAeSd32j0JbQnxJ2

# Atualize o sistema e instale o utilitário curl
RUN apt-get update && apt-get install -y curl

# Baixe e instale o PostgreSQL JDBC Driver
RUN mkdir -p /usr/share/man/man1
RUN apt-get install -y default-jre-headless
RUN apt-get install -y libpostgresql-jdbc-java

# Copie o arquivo JAR do seu aplicativo Spring Boot para a imagem
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar /app.jar

# Copie o arquivo SQL de inicialização do banco de dados para a imagem
COPY src/main/resources/static/jogos-serios-tradicionais.sql /docker-entrypoint-initdb.d/

# Instale o servidor Nginx
# RUN apt-get install -y nginx

# Exponha a porta em que o aplicativo Spring Boot será executado
# EXPOSE 8080

# Comando de inicialização do aplicativo Spring Boot
CMD ["java", "-jar", "/app.jar"]