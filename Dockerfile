FROM gradle:8.10-jdk21 AS build
WORKDIR /workspace

COPY gradlew settings.gradle* build.gradle* gradle.properties* ./
COPY gradle ./gradle
RUN chmod +x gradlew || true
RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache tzdata curl && \
    ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone && \
    addgroup -S app && adduser -S app -G app

USER app
WORKDIR /app

COPY --from=build /workspace/build/libs/*SNAPSHOT*.jar app.jar
# (릴리스 버전 사용 시 위 라인 대신 아래 주석 해제)
# COPY --from=build /workspace/build/libs/*.jar app.jar

ENV TZ=Asia/Seoul \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UnlockExperimentalVMOptions -XX:+UseSerialGC" \
    SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar" ]
