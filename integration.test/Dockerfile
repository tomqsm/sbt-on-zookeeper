FROM openjdk:12
COPY target/integration.test-1.0-SNAPSHOT.jar /usr/myapp/app.jar
WORKDIR /usr/myapp
EXPOSE 8080

CMD java -jar app.jar