FROM openjdk:17-alpine

WORKDIR /app

COPY target/recruitment-service.jar recruitment-service.jar

ENTRYPOINT ["java", "-jar", "recruitment-service.jar"]

EXPOSE 8008