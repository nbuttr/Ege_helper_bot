FROM openjdk:21-jdk-bullseye

COPY build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]