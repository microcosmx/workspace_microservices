FROM java:8-jre
MAINTAINER Chenjie Xu <saberlilydian@163.com>

ADD ./target/verificationCode-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/verificationCode-service.jar"]

EXPOSE 16001