FROM openjdk:17-alpine

WORKDIR /app

COPY target/service-registry.jar service-registry.jar

ENTRYPOINT ["java", "-jar", "service-registry.jar"]

EXPOSE 8761