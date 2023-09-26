FROM openjdk:17

#definindo a porta
ENV PORT=8080

WORKDIR /app

COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/demoIntegracaoMysqlSalesforce.jar /app/demoIntegracaoMysqlSalesforce.jar

EXPOSE $PORT

CMD ["java", "-jar", "-Dserver.port=$PORT", "demoIntegracaoMysqlSalesforce.jar"]