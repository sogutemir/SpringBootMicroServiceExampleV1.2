FROM openjdk:17-jdk-slim
LABEL maintainer="sogutemir"
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "product-service.jar"]
