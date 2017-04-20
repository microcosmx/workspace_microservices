FROM java:8-jre
MAINTAINER jichao <13302010019@fudan.edu.cn>

ADD ./target/contacts-query-service-0.1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/contacts-query-service-0.1.0.jar"]

EXPOSE 12347