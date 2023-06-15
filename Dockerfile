FROM eclipse-temurin:17

LABEL mentainer="db.ahmed93@gmail.com"

WORKDIR /app

COPY target/rest-api-0.0.1-SNAPSHOT.jar /app/blogapi-docker.jar

ENTRYPOINT ["java", "-jar", "blogapi-docker.jar"]

