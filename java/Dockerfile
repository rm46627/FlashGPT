#Build stage

FROM gradle:7.4-jdk17-alpine AS BUILD
WORKDIR /usr/app/
COPY . .
RUN mv src/main/resources/docker/application.properties src/main/resources/application.properties
#RUN gradle bootRun
ENTRYPOINT exec gradle bootRun
# Package stage

#FROM eclipse-temurin:17-jdk-alpine
#ENV JAR_NAME=app.jar
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#COPY --from=BUILD $APP_HOME .
#EXPOSE 8080
#ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME 