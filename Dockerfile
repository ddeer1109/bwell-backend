FROM adoptopenjdk:11-jre-hotspot
COPY target/bwell-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
