FROM java:8-jre
MAINTAINER jay <jayZhou5@gmail.com>

ADD ./target/dashboard.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/dashboard.jar"]

EXPOSE 6000