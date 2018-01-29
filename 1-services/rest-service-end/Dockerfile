FROM java:8-jre


ADD ./target/rest-service-end-0.1.0.jar /app/
CMD ["java", "-server", "-Xmx1200m", "-jar", "/app/rest-service-end-0.1.0.jar"]

EXPOSE 16000