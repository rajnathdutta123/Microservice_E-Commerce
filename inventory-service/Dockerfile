FROM openjdk:17-alpine
LABEL authors="Raju"
ADD target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT [ "java","-jar","inventory-service-0.0.1-SNAPSHOT.jar"]