FROM java:8-jre
MAINTAINER Chenjie Xu <saberlilydian@163.com>

ADD ./target/ts-payment-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-payment-service.jar"]

EXPOSE 19001