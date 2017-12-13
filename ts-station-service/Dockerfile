FROM envoyproxy/envoy:latest
MAINTAINER Chenjie Xu <15212010021@fudan.edu.cn>

RUN apt-get update && apt-get -q install -y \
    curl
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

ADD ./target/ts-station-service-1.0.jar /app/
ADD ./start_service.sh /usr/local/bin/start_service.sh
RUN chmod u+x /usr/local/bin/start_service.sh
#CMD /usr/local/bin/envoy -c /etc/service-envoy.json --service-cluster station
ENTRYPOINT /usr/local/bin/start_service.sh
