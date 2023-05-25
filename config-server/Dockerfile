FROM eclipse-temurin:17-jre-alpine

RUN adduser -h /home/config --disabled-password config
USER config
WORKDIR /home/config
COPY ./build/libs/*.jar app.jar
COPY ./config ./config

USER config

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
