FROM openjdk:8-jdk-alpine

EXPOSE 8100

RUN apk add --no-cache ttf-dejavu
##teste


ARG DATASOURCE_HOSTV="jdbc:postgresql://10.0.12.48:5432/dbsilverD"
ENV DATASOURCE_HOST=$DATASOURCE_HOSTV

ARG DATASOURCE_USERNAMEV="henrique.borba.prestador"
ENV DATASOURCE_USERNAME=$DATASOURCE_USERNAMEV

ARG DATASOURCE_PASSWORDV="d67uij3d"
ENV DATASOURCE_PASSWORD=$DATASOURCE_PASSWORDV

COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]