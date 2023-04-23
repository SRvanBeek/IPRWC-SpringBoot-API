# define base docker mage
FROM openjdk:19
LABEL maintainer="hsleiden.nl"
ADD target/iprwc-0.0.1-SNAPSHOT.jar iprwc-api-docker.jar
ENTRYPOINT ["java", "-jar", "iprwc-api-docker.jar"]