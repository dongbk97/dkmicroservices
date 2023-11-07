FROM openjdk:17-alpine

WORKDIR /app

COPY target/employee-service.jar employee-service.jar

ENTRYPOINT ["java", "-jar", "employee-service.jar"]

EXPOSE 8002