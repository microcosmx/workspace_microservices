FROM java:8-jre


ADD ./target/ts-example-second-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-example-second-service-1.0.jar"]

EXPOSE 12389