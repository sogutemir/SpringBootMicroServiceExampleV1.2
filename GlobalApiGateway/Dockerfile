FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/GlobalApiGateway-0.0.1-SNAPSHOT.jar GlobalApiGateway.jar
ENTRYPOINT ["java","-jar","/GlobalApiGateway.jar"]