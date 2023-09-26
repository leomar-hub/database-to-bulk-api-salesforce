# Use a imagem base do OpenJDK 11 (ou a versão que você precisa)
FROM openjdk:8-jre-slim

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do seu projeto para o diretório de trabalho no contêiner
COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/demoIntegracaoMysqlSalesforce.jar /app/demoIntegracaoMysqlSalesforce.jar

# Comando para executar o aplicativo Java quando o contêiner for iniciado
CMD ["java", "-jar", "demoIntegracaoMysqlSalesforce.jar"]
