FROM amazoncorretto:11-alpine-jdk

VOLUME /tmp

COPY target/*.jar app.jar

CMD ["java", "-jar", "/app.jar"]