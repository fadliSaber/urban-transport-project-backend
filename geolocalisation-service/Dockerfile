FROM openjdk:17-alpine
EXPOSE 8086
ARG JAR_FILE=target/geolocalisation-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]