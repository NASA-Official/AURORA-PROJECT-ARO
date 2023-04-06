FROM openjdk:11 as build

ARG JAR_FILE=./build/libs/*.jar

ADD ${JAR_FILE} batch.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/batch.jar"]
