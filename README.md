# workspace_microservices

this is a online-ticket application based on microservices architecture.

application dev is based on:
spring boot
spring cloud


runtime environment:
docker swarm



build: (in specific service folder)
mvn -Dmaven.test.skip=true clean package

build compose:
docker-compose -f docker-compose.yml build

run:
docker-compose -f docker-compose.yml up -d
docker-compose down



docker swarm:
https://docs.docker.com/compose/compose-file/#replicas
compose v3:
docker stack deploy --compose-file=docker-compose-swarm-v3.yml my-compose-swarm
docker service scale my-compose-swarm_rest-client=5
compose v2:
docker-compose -f docker-compose-swarm-v2.yml up -d

url:
http://10.141.212.22:15001/hello?name=jay