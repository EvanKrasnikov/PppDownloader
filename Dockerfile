FROM openjdk:8u252-jdk

COPY src src
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml

RUN ./mvnw clean install
