FROM openjdk:11.0.11
MAINTAINER "Rafael Scane"
ADD target/sboot-builders-customer-application-0.0.1.jar ./application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "application.jar"]