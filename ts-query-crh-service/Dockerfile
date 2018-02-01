FROM java:8-jre


ADD ./target/querycrh-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/querycrh-service.jar"]

EXPOSE 17001