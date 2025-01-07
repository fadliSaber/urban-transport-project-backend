FROM openjdk:17-alpine
EXPOSE 8085
ARG JAR_FILE=target/messaging-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]