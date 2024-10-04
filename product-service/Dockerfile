FROM openjdk:17-alpine
LABEL authors="Raju"
ADD target/product-service-0.0.1-SNAPSHOT.jar product-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT [ "java","-jar","product-service-0.0.1-SNAPSHOT.jar"]