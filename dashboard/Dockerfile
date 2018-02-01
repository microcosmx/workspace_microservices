FROM java:8-jre


ADD ./target/dashboard.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/dashboard.jar"]

EXPOSE 10000