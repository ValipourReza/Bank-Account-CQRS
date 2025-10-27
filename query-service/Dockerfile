FROM openjdk:17-jdk-slim
WORKDIR /app
COPY query-service/target/query-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]