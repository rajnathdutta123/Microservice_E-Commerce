FROM openjdk:17-alpine
LABEL authors="Raju"
ADD target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT [ "java","-jar","api-gateway-0.0.1-SNAPSHOT.jar"]