FROM openjdk:8-jdk-alpine
MAINTAINER emiliano
VOLUME /tmp
EXPOSE 8085:8085

ADD build/libs/java-boilerplate-1.0.jar challenge.jar

ADD src/main/resources /tmp/src/main/resources

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/challenge.jar"]