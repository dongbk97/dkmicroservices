FROM openjdk:17-alpine

WORKDIR /app

COPY target/location-service.jar location-service.jar

ENTRYPOINT ["java", "-jar", "location-service.jar"]

EXPOSE 8005