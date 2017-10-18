FROM erkekin/tornado
MAINTAINER Li Wenhai <16212010016@fudan.edu.cn>

RUN mkdir -p /app
WORKDIR /app

ADD ./server.py /app/
CMD [ "python", "server.py" ]

EXPOSE 16101