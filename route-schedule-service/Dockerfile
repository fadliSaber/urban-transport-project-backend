FROM openjdk:17-alpine
EXPOSE 8083
ARG JAR_FILE=target/route-schedule-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]