FROM envoyproxy/envoy:latest
MAINTAINER Li Wenhai <16212010016@fudan.edu.cn>

RUN apt-get update && apt-get -q install -y \
    curl
CMD /usr/local/bin/envoy -c /etc/front-envoy.json --service-cluster front-proxy
