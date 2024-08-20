FROM openjdk:17-jdk-slim
LABEL maintainer="sogutemir"
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
