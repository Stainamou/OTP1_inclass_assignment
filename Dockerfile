FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml mvnw* ./
COPY .mvn .mvn
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENV LANG C.UTF-8
ENTRYPOINT ["java","-jar","/app/app.jar"]