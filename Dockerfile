FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

RUN mvn clean package -DskipTests

FROM openjdk:21

ARG DB_NAME
ARG DB_USER
ARG DB_PASS
ARG DB_URL
ARG DB_DRIVER_CLASS_NAME
ARG DB_HIBERNATE_DIALECT

ENV DB_NAME=${DB_NAME} \
    DB_USER=${DB_USER} \
    DB_PASS=${DB_PASS} \
    DB_URL=${DB_URL} \
    DB_DRIVER_CLASS_NAME=${DB_DRIVER_CLASS_NAME} \
    DB_HIBERNATE_DIALECT=${DB_HIBERNATE_DIALECT}

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
