FROM java:8-jre


ADD ./target/rest-service-2-0.1.0.jar /app/
CMD ["java", "-server", "-Xmx600m", "-jar", "/app/rest-service-2-0.1.0.jar"]

EXPOSE 16001