FROM openjdk:17-alpine

WORKDIR /app

COPY target/payroll-service.jar payroll-service.jar

ENTRYPOINT ["java", "-jar", "payroll-service.jar"]

EXPOSE 8101