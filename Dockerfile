FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-trades.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=mysql", "/spring-trades.jar"]