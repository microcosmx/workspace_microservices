##################################################################
错误说明：
    在对订单进行支付，但站内余额不足的时候，会跳转到一个第三方的非java写的服务
    正常流程：站内余额不足，调用rest-external-service，这个服务返回true或者false，表示付款通过第三方的服务正常完成
    错误流程：站内余额不足，调用rest-external-service，这个服务在规定规定时间内不返回，导致调用超时
    触发流程：
    正常流程：控制台输出[Inside Payment][Turn to Outside Payment] 外部服务调用正常，页面返回支付成功
    错误流程：[Inside Payment][Turn to Outside Payment] 外部服务调用超时，页面返回支付失败
##################################################################
build:
mvn clean package

docker-compose:
docker-compose build
docker-compose up -d
docker-compose down





mvn repo:
https://repo1.maven.org/maven2/


clean images:
docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi


build:
mvn -Dmaven.test.skip=true clean package
docker-compose -f docker-compose.yml build


run:
docker-compose -f docker-compose.yml up -d
docker-compose down
docker-compose logs -f


rest url:
http://localhost:16006/hello6?cal=50
http://rest-service-6:16006/hello6?cal=50


rabbit mq queue:
docker run -d -p 5672:5672 -p 15672:15672 --name rest-service-queue rabbitmq:management
http://localhost:15672


zipkin:
docker run -d -p 9411:9411 --name myzipkin openzipkin/zipkin
http://zipkin:9411/
http://172.16.0.1:9411/
http://10.141.212.25:9411/
traces:
http://localhost:9411/api/v1/traces?annotationQuery=&endTs=1496377639992&limit=100&lookback=3600000&minDuration=&serviceName=rest-service-6&sortOrder=duration-desc&spanName=all
http://localhost:9411/api/v1/traces?annotationQuery=&minDuration=&serviceName=rest-service-6&sortOrder=duration-desc&spanName=all


redis:
docker run -d --name myredis -p 6379:6379 redis


docker ui:
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://10.141.212.22:9000/



swarm:

test sample:
http://10.141.211.164:16006/hello6?cal=60
http://10.141.211.164:16000/persist_get

build:
mvn clean package
docker-compose build

docker tag ts/ts-ui-dashboard 10.141.212.25:5555/cluster-ts-ui-dashboard
docker tag ts/ts-login-service 10.141.212.25:5555/cluster-ts-login-service
docker tag ts/ts-register-service 10.141.212.25:5555/cluster-ts-register-service

docker push 10.141.212.25:5555/cluster-ts-ui-dashboard
docker push 10.141.212.25:5555/cluster-ts-login-service
docker push 10.141.212.25:5555/cluster-ts-register-service

docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm
docker stack ls
docker stack services my-compose-swarm
docker stack ps my-compose-swarm
docker stack rm my-compose-swarm

docker service ls --format "{{.Name}}" | grep "rest-service" | xargs docker service rm
docker service ls --format "{{.Name}}" | xargs docker service rm
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker swarm leave --force
docker node ls
docker node rm 0pvy8v3sugtmcbqualswp1rv5

swarm ui:
http://10.141.211.164:9000/
zipkin:
http://10.141.211.164:9411/




selenium:
http://www.seleniumhq.org
docker run -d -p 4444:4444 -v /dev/shm:/dev/shm selenium/standalone-chrome:3.4.0-bismuth
10.141.212.21
10.141.212.23
10.141.212.24

monitoring:
ps auxw --sort=%cpu
ps auxw --sort=rss
ps auxw --sort=vsz

