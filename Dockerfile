# escolher a imagem pra rodar a aplicacao.
FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/api-bussolaweb-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} api-bussola.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/api-bussola.jar"]
