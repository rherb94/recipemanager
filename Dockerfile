FROM openjdk:10-jre-slim

MAINTAINER Ryan Herb "rherb94@gmail.com"

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY target/recipemanager-0.0.1-RELEASE.jar webapp.jar

CMD ["java", "-jar","webapp.jar"]