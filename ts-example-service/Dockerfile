FROM java:8-jre


ADD ./target/ts-example-service-1.0.jar /app/
CMD ["java", "-Xmx1200m", "-jar", "/app/ts-example-service-1.0.jar"]

EXPOSE 12388