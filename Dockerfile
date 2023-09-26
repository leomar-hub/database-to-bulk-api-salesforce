FROM openjdk:8-jre-slim

WORKDIR /app

COPY demoIntegracaoMysqlSalesforce.jar /app/demoIntegracaoMysqlSalesforce.jar

CMD ["java", "-jar", "demoIntegracaoMysqlSalesforce.jar"]