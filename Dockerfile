FROM alpine/java:21-jdk
RUN apk update && apk upgrade libexpat
WORKDIR /home/app/
COPY ./target/fusionIq-0.0.1-SNAPSHOT.jar /home/app/
CMD ["nohup", "java" , "-jar" , "fusionIq-0.0.1-SNAPSHOT.jar","&"]