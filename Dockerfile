FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/*.jar .

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

ENTRYPOINT ["java", "-jar", "chess-0.0.1-SNAPSHOT.jar"]