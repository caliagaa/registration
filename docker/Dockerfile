FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.3_7

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

# Default copy (Gradle)
COPY target/registration-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

CMD /wait && java $JAVA_OPTS -jar /app.jar