FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
ENTRYPOINT ["java","-jar","/product-service.jar"]