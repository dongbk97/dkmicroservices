FROM openjdk:17-alpine

WORKDIR /app

COPY target/notification-service.jar notification-service.jar

ENTRYPOINT ["java", "-jar", "notification-service.jar"]

EXPOSE 8005