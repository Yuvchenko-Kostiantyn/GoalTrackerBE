FROM maven:3.6.3-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean install

FROM openjdk:11-jdk
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar"]
CMD ["-Dspring.profiles.active=prod", "/app.jar"]
