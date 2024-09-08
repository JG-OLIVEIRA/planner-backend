FROM openjdk:21

ARG DB_NAME
ARG DB_USER
ARG DB_PASS
ARG DB_URL
ARG DB_DRIVER_CLASS_NAME

ENV DB_NAME=${DB_NAME} \
    DB_USER=${DB_USER} \
    DB_PASS=${DB_PASS} \
    DB_URL=${DB_URL} \
    DB_DRIVER_CLASS_NAME=${DB_DRIVER_CLASS_NAME}

WORKDIR /app

COPY  target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
