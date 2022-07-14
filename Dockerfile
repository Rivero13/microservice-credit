FROM openjdk:11
VOLUME /tmp
ADD ./target/microservice-credit-0.0.1-SNAPSHOT.jar microservice-credit.jar
ENTRYPOINT ["java", "-jar", "/microservice-credit.jar"]