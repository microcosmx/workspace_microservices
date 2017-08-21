
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
docker rm portainer-ui-local
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://10.141.212.22:9000/
http://10.141.211.161:9000



swarm:

test sample:
http://10.141.211.161/

build:
mvn clean package
docker-compose build
docker-compose up
docker swarm init --advertise-addr 10.141.211.161
docker swarm join-token manager
docker swarm join-token worker

ansible:
/etc/ansible/hosts
ansible docker-masters -a "/bin/echo hello" -u root
ansible docker-machines -a "/bin/echo hello" -u root
ansible docker-masters -a "docker pull mongo" -u root
ansible docker-machines -a "docker pull mongo" -u root
rabbitmq:management, openzipkin/zipkin:latest
docker pull 10.141.212.25:5555/cluster-ts-ui-dashboard

docker:
docker tag ts/ts-ui-dashboard 10.141.212.25:5555/cluster-ts-ui-dashboard
docker tag ts/ts-login-service 10.141.212.25:5555/cluster-ts-login-service
docker tag ts/ts-register-service 10.141.212.25:5555/cluster-ts-register-service
docker tag ts/ts-sso-service 10.141.212.25:5555/cluster-ts-sso-service
docker tag ts/ts-verification-code-service 10.141.212.25:5555/cluster-ts-verification-code-service
docker tag ts/ts-contacts-service 10.141.212.25:5555/cluster-ts-contacts-service
docker tag ts/ts-order-service 10.141.212.25:5555/cluster-ts-order-service
docker tag ts/ts-order-other-service 10.141.212.25:5555/cluster-ts-order-other-service
docker tag ts/ts-config-service 10.141.212.25:5555/cluster-ts-config-service
docker tag ts/ts-station-service 10.141.212.25:5555/cluster-ts-station-service
docker tag ts/ts-train-service 10.141.212.25:5555/cluster-ts-train-service
docker tag ts/ts-travel-service 10.141.212.25:5555/cluster-ts-travel-service
docker tag ts/ts-travel2-service 10.141.212.25:5555/cluster-ts-travel2-service
docker tag ts/ts-preserve-service 10.141.212.25:5555/cluster-ts-preserve-service
docker tag ts/ts-preserve-other-service 10.141.212.25:5555/cluster-ts-preserve-other-service
docker tag ts/ts-basic-service 10.141.212.25:5555/cluster-ts-basic-service
docker tag ts/ts-ticketinfo-service 10.141.212.25:5555/cluster-ts-ticketinfo-service
docker tag ts/ts-price-service 10.141.212.25:5555/cluster-ts-price-service
docker tag ts/ts-notification-service 10.141.212.25:5555/cluster-ts-notification-service
docker tag ts/ts-security-service 10.141.212.25:5555/cluster-ts-security-service
docker tag ts/ts-inside-payment-service 10.141.212.25:5555/cluster-ts-inside-payment-service
docker tag ts/ts-execute-service 10.141.212.25:5555/cluster-ts-execute-service
docker tag ts/ts-payment-service 10.141.212.25:5555/cluster-ts-payment-service
docker tag ts/ts-rebook-service 10.141.212.25:5555/cluster-ts-rebook-service
docker tag ts/ts-cancel-service 10.141.212.25:5555/cluster-ts-cancel-service
docker tag mongo 10.141.212.25:5555/cluster-ts-mongo
docker tag rabbitmq:management 10.141.212.25:5555/cluster-ts-rabbitmq-management
docker tag redis 10.141.212.25:5555/cluster-ts-redis
docker tag openzipkin/zipkin 10.141.212.25:5555/cluster-ts-openzipkin-zipkin


docker push 10.141.212.25:5555/cluster-ts-ui-dashboard
docker push 10.141.212.25:5555/cluster-ts-login-service
docker push 10.141.212.25:5555/cluster-ts-register-service
docker push 10.141.212.25:5555/cluster-ts-sso-service
docker push 10.141.212.25:5555/cluster-ts-verification-code-service
docker push 10.141.212.25:5555/cluster-ts-contacts-service
docker push 10.141.212.25:5555/cluster-ts-order-service
docker push 10.141.212.25:5555/cluster-ts-order-other-service
docker push 10.141.212.25:5555/cluster-ts-config-service
docker push 10.141.212.25:5555/cluster-ts-station-service
docker push 10.141.212.25:5555/cluster-ts-train-service
docker push 10.141.212.25:5555/cluster-ts-travel-service
docker push 10.141.212.25:5555/cluster-ts-travel2-service
docker push 10.141.212.25:5555/cluster-ts-preserve-service
docker push 10.141.212.25:5555/cluster-ts-preserve-other-service
docker push 10.141.212.25:5555/cluster-ts-basic-service
docker push 10.141.212.25:5555/cluster-ts-ticketinfo-service
docker push 10.141.212.25:5555/cluster-ts-price-service
docker push 10.141.212.25:5555/cluster-ts-notification-service
docker push 10.141.212.25:5555/cluster-ts-security-service
docker push 10.141.212.25:5555/cluster-ts-inside-payment-service
docker push 10.141.212.25:5555/cluster-ts-execute-service
docker push 10.141.212.25:5555/cluster-ts-payment-service
docker push 10.141.212.25:5555/cluster-ts-rebook-service
docker push 10.141.212.25:5555/cluster-ts-cancel-service
docker push 10.141.212.25:5555/cluster-ts-mongo
docker push 10.141.212.25:5555/cluster-ts-rabbitmq-management
docker push 10.141.212.25:5555/cluster-ts-redis
docker push 10.141.212.25:5555/cluster-ts-openzipkin-zipkin

docker network prune
docker network rm ingress
docker network create --ingress --driver overlay ingress

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
http://10.141.211.160:9000/
zipkin:
http://10.141.211.160:9411/
app:
http://10.141.211.160/




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

