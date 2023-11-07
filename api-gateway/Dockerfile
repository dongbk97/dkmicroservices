FROM openjdk:17-alpine

WORKDIR /app

COPY target/api-gateway.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]

EXPOSE 8003