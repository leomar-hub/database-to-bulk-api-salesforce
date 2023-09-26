FROM openjdk:18-jdk

#definindo porta
ENV PORT=8090

WORKDIR /app

COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/demoIntegracaoMysqlSalesforce.jar /app/demoIntegracaoMysqlSalesforce.jar

EXPOSE $PORT

CMD ["java", "-jar", "-Dserver.port=$PORT", "demoIntegracaoMysqlSalesforce.jar"]
