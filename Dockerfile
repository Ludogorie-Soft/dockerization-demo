FROM openjdk:21
COPY ./target/Dockerization-0.0.1-SNAPSHOT.jar Dockerizarion-SNAPSHOT.jar
CMD ["java","-jar","Dockerization-0.0.1-SNAPSHOT.jar"]