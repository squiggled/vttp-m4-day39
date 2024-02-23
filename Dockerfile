FROM node:21 AS ng-builder

RUN npm i -g @angular/cli

WORKDIR /ngapp

COPY day39-form-frontend/package*.json .
COPY day39-form-frontend/angular.json .
COPY day39-form-frontend/tsconfig.* .
COPY day39-form-frontend/src src

RUN npm ci && ng build

# /ngapp/dist/frontend/browser/* -> container is now this

# Starting with this Linux server
FROM maven:3-eclipse-temurin-21 AS sb-builder

## Build the application
# Create a directory call /sbapp
# go into the directory cd /app
WORKDIR /sbapp

# everything after this is in /sbapp
COPY day39-form-backend/mvnw .
COPY day39-form-backend/mvnw.cmd .
COPY day39-form-backend/pom.xml .
COPY day39-form-backend/.mvn .mvn
COPY day39-form-backend/src src
COPY --from=ng-builder /ngapp/dist/day39-form-frontend src/main/resources/static



# Build the application
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app 

COPY --from=sb-builder /sbapp/target/day38ws-0.0.1-SNAPSHOT.jar app.jar

## Run the application
# Define environment variable 
ENV PORT=8080 

# Expose the port
EXPOSE ${PORT}

ENV S3_ACCESS_KEY=placeholder
ENV S3_SECRET_KEY=placeholder
ENV SPRING_DATA_MONGODB_URI=

# Run the program
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar