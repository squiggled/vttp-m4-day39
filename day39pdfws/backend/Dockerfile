FROM openjdk:21-bookworm AS builder
WORKDIR /src

COPY .mvn .mvn
COPY src src
COPY mvnw .
COPY pom.xml .

RUN ./mvnw package -DskipTests

FROM openjdk:21-bookworm

WORKDIR /app

COPY --from=builder src/target/day39pdfws-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
ENV GIPHY_KEY="placeholder"

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar