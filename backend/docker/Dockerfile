FROM gradle:9.2.1-jdk21-jammy AS builder
WORKDIR /opt/app

COPY settings.gradle ./
COPY build.gradle ./
COPY gradle gradle/
COPY gradlew .
COPY backend-app/build.gradle backend-app/build.gradle
COPY backend-api/build.gradle backend-api/build.gradle

COPY backend-app/src backend-app/src
COPY backend-api/src backend-api/src

RUN chmod +x gradlew && \
    ./gradlew backend-app:build -x test

FROM eclipse-temurin:21-jre-jammy
WORKDIR /opt/app
COPY --from=builder /opt/app/backend-app/build/libs/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /opt/app/*.jar"]