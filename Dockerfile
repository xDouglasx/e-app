FROM 172.21.149.244:8181/openjdk:8-jdk

LABEL maintainer = "Equifax Dublin - Data Catalog"

VOLUME /config
WORKDIR /app

ARG JAR_FILE
ADD target/${JAR_FILE} /app/douglas-test.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/douglas-test.jar"]