FROM openjdk:8-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} i6a206.jar
ENTRYPOINT ["java","-jar","/i6a206.jar"]