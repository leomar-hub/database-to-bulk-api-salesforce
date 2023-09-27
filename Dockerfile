FROM openjdk:18-jdk

#definindo a porta
ENV PORT=8090

WORKDIR /app

COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/spring-boot-mf-0.0.1-SNAPSHOT.jar /app/spring-boot-mf-0.0.1-SNAPSHOT.jar

EXPOSE $PORT

CMD ["java", "-jar", "-Dserver.port=$PORT", "spring-boot-mf-0.0.1-SNAPSHOT.jar"]

