FROM openjdk:17-alpine

WORKDIR /app

COPY target/attendance-service.jar attendance-service.jar

ENTRYPOINT ["java", "-jar", "attendance-service.jar"]

EXPOSE 8009