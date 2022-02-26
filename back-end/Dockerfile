# backend/Dockerfile
# 사용한 openjdk 버전에 맞는 값을 입력해주세요.
FROM openjdk:8-jdk-alpine

# jar 파일 경로는 직접 입력해주세요.
COPY /build/libs/ssafy-web-project-1.0-SNAPSHOT.jar app.jar

# 배포용 properties 실행 명령어
ENTRYPOINT ["java","-jar","app.jar","--spring.config.name=application-prod"]


# 만약 배포용 properties를 사용하지 않는다면
# Default properties 실행 명령어
# ENTRYPOINT ["java","-jar","app.jar"]