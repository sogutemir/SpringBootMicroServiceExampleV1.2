FROM openjdk:17-jdk-slim
LABEL maintainer="sogutemir"
COPY target/GlobalApiGateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
