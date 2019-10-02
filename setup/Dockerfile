FROM openjdk:12
ADD target/lib /lib
ADD target/setup-1.0-SNAPSHOT.jar /app.jar
ENV PORT=8080

CMD java -Dspring.profiles.active=inmemory,default -Dserver.port=$PORT -jar app.jar
#docker  run -it --rm --hostname localhost -e PORT=8989 --name flow -p 8989:8989 flow