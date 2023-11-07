FROM openjdk:17-alpine

WORKDIR /app

COPY target/department-service.jar department-service.jar

ENTRYPOINT ["java", "-jar", "department-service.jar"]

EXPOSE 8001