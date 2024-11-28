FROM alpine/java:21-jre
WORKDIR /home/app/
COPY ./target/fusionIq-0.0.1-SNAPSHOT.jar /home/app/
CMD ["java" , "-jar" , "fusionIq-0.0.1-SNAPSHOT.jar"]