FROM openjdk:21-slim
WORKDIR /home/app/
COPY ./target/fusionIq-0.0.1-SNAPSHOT.jar /home/app/
CMD ["nohup", "java" , "-jar" , "fusionIq-0.0.1-SNAPSHOT.jar","&"]