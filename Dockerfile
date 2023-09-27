FROM openjdk:18-jdk

#definindo a porta
ENV PORT=8090

WORKDIR /app

COPY out/artifacts/demoIntegracaoMysqlSalesforce_jar/spring-boot-mf-0.0.1-SNAPSHOT.jar /app/spring-boot-mf-0.0.1-SNAPSHOT.jar

EXPOSE 8090

CMD ["java", "-jar", "-Dserver.port=8090", "spring-boot-mf-0.0.1-SNAPSHOT.jar"]

