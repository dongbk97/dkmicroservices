FROM openjdk:17-alpine

WORKDIR /app

COPY target/config-server.jar config-server.jar

ENTRYPOINT ["java", "-jar", "config-server.jar"]

EXPOSE 8088