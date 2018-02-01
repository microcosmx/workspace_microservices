FROM java:8-jre


ADD ./target/payment-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/payment-service.jar"]

EXPOSE 19001