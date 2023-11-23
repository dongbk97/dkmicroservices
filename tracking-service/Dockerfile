FROM openjdk:17-alpine

WORKDIR /app

COPY target/tracking-service.jar tracking-service.jar

ENTRYPOINT ["java", "-jar", "tracking-service.jar"]

EXPOSE 8007