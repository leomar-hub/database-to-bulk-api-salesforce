# Use a imagem base do OpenJDK 11 (ou a versão que você precisa)
FROM openjdk:8-jre-slim

# Defina uma variável de ambiente para a porta
ENV PORT=8080

# Defina o diretório dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do seu projeto para o diretório de trabalho no conteiner
COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/demoIntegracaoMysqlSalesforce.jar /app/demoIntegracaoMysqlSalesforce.jar

# Comando para executar o aplicativo Java quando o contêiner for iniciado,
# usando a variável de ambiente para definir a porta
CMD ["java", "-jar", "-Dserver.port=${PORT}", "demoIntegracaoMysqlSalesforce.jar"]

