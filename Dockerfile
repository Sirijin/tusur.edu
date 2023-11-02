FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY target/ваш-проект.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]