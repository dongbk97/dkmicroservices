FROM openjdk:17-alpine

WORKDIR /app

COPY target/common-shared.jar common-shared.jar

ENTRYPOINT ["java", "-jar", "common-shared.jar"]