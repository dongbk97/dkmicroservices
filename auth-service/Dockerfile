FROM openjdk:17-alpine

WORKDIR /app

COPY target/auth-service.jar auth-service.jar

ENTRYPOINT ["java", "-jar", "auth-service.jar"]

EXPOSE 8004