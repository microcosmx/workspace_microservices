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

