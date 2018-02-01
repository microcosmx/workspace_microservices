FROM java:8-jre


ADD ./target/queryordinary-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/queryordinary-service.jar"]

EXPOSE 18001