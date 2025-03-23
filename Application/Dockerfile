FROM openjdk:21-jdk-slim
LABEL authors="Hubert Miklas"

WORKDIR /app/

COPY . .

RUN ./gradlew build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./build/libs/Application-all.jar"]