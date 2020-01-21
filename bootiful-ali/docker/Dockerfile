FROM openjdk:11

VOLUME /tmp

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

#MAINTAINER www.mspbots.ai
#
#ENV JAVA_OPTS="-Xms512m"
#
#COPY ./*.jar mspbots-wise.jar
#
#COPY ./*.yml application.yml

#RUN apt-get install -y --no-install-recommends fontconfig

COPY ./bootstrap.properties bootstrap.properties

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
