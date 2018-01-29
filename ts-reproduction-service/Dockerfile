FROM java:8-jre


ADD ./target/ts-reproduction-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-reproduction-service-1.0.jar"]

EXPOSE 19867