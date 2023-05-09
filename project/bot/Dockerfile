FROM openjdk:17-alpine
WORKDIR /app
COPY ./target/bot-1.0-SNAPSHOT.jar ./spring-boot-application.jar

ENTRYPOINT ["java","-jar","./spring-boot-application.jar"]