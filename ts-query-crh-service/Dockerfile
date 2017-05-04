FROM java:8-jre
MAINTAINER Chenjie Xu <saberlilydian@163.com>

ADD ./target/querycrh-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/querycrh-service.jar"]

EXPOSE 17001