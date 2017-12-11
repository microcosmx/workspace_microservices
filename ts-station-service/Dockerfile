FROM java:8-jre
FROM envoyproxy/envoy:latest
MAINTAINER Chenjie Xu <15212010021@fudan.edu.cn>

ADD ./target/ts-station-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-station-service-1.0.jar"]
RUN apt-get update && apt-get -q install -y \
    curl
CMD /usr/local/bin/envoy -c /etc/service-envoy.json --service-cluster station
