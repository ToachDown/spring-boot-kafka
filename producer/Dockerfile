FROM openjdk:11.0-jdk
COPY target/producer-1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]