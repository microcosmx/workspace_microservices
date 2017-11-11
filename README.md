
复旦大学软件工程实验室，微服务项目

该项目为购票微服务应用项目，包括40+微服务

技术：

java，spring boot，spring cloud

nodejs，express

python，dijango

go，webgo



------------------------------------------------------------------

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




------------------------------------------------------------------

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

......


app local registry：

docker push 10.141.212.25:5555/cluster-ts-ui-dashboard

......



deploy app （docker swarm）：

docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm



集群运行时监控界面：

docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer

http://10.141.211.161:9000




