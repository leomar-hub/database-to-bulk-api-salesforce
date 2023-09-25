# Use a imagem base oficial do OpenJDK
FROM openjdk:11-jre-slim

# Define o diretório de trabalho no contêiner como /app
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o contêiner e renomeia para app.jar
COPY target/demo-integracao-mysql-salesforce.jar app.jar

# Expõe a porta 8080, que é a porta padrão para aplicativos Spring Boot
EXPOSE 8080

# Define o comando de entrada para iniciar o aplicativo Spring Boot com java -jar app.jar
CMD ["java", "-jar", "app.jar"]
