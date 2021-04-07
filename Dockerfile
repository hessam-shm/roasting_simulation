FROM maven:3-alpine AS build
ENV TZ Europe/Vienna
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn -f ./pom.xml clean package -DskipTests

FROM openjdk:8-jdk-alpine
ENV TZ Europe/Vienna
WORKDIR /app
COPY --from=build /app/target/roasting-simulation-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
