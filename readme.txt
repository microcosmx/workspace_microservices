



1. 本地运行环境：

本地打包命令：
mvn build：
mvn -Dmaven.test.skip=true clean package
docker-compose -f docker-compose.yml build

docker build:
docker-compose build
（docker-compose -f docker-compose.yml build）
docker-compose up -d
docker-compose down



微服务应用系统运行（单机）:
docker-compose -f docker-compose.yml up -d
docker-compose down
docker-compose logs -f



docker运行时监控:
docker rm portainer-ui-local
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://localhost:9000/

清理镜像:
docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi






2. 集群环境运行（docker swarm）:

build:
mvn clean package
docker-compose build
docker-compose up
docker swarm init --advertise-addr 10.141.211.161
docker swarm join-token manager
docker swarm join-token worker


app tag:
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


app local registry：
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


deploy app （docker swarm）：
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



集群运行时监控界面：
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://10.141.211.161:9000



测试：（selenium）
http://www.seleniumhq.org
docker run -d -p 4444:4444 -v /dev/shm:/dev/shm selenium/standalone-chrome:3.4.0-bismuth


