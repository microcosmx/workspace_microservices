FROM java:8-jre
MAINTAINER Chenjie Xu <saberlilydian@163.com>

ADD ./target/verificationCode-login.service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/verificationCode-login.service.jar"]

EXPOSE 16001