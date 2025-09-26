FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app

COPY applications/app-service/build/libs/*.jar Capacidad.jar

USER appuser

EXPOSE 9003

ENTRYPOINT ["java", "-jar", "Capacidad.jar"]