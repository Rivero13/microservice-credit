FROM openjdk:11.0.15-jdk-slim-buster
COPY "./target/microservice-credit.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]