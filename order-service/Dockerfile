FROM openjdk:17-alpine
LABEL authors="Raju"
ADD target/order-service-0.0.1-SNAPSHOT.jar order-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT [ "java","-jar","order-service-0.0.1-SNAPSHOT.jar"]