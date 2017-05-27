
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


queue:
docker run -d -p 5672:5672 -p 15672:15672 --name rest-service-queue rabbitmq:management
http://localhost:15672


zipkin:
docker run -d -p 9411:9411 --name myzipkin openzipkin/zipkin
http://zipkin:9411/
http://172.16.0.1:9411/
http://10.141.212.25:9411/


docker ui:
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://10.141.212.22:9000/


swarm:
docker tag my-service-cluster/rest-service-end 10.141.212.25:5555/my-rest-service-end
docker tag my-service-cluster/rest-service-1 10.141.212.25:5555/my-rest-service-1
docker tag my-service-cluster/rest-service-2 10.141.212.25:5555/my-rest-service-2
docker tag my-service-cluster/rest-service-3 10.141.212.25:5555/my-rest-service-3
docker tag my-service-cluster/rest-service-4 10.141.212.25:5555/my-rest-service-4
docker tag my-service-cluster/rest-service-5 10.141.212.25:5555/my-rest-service-5
docker tag my-service-cluster/rest-service-6 10.141.212.25:5555/my-rest-service-6

docker push 10.141.212.25:5555/my-rest-service-end
docker push 10.141.212.25:5555/my-rest-service-1
docker push 10.141.212.25:5555/my-rest-service-2
docker push 10.141.212.25:5555/my-rest-service-3
docker push 10.141.212.25:5555/my-rest-service-4
docker push 10.141.212.25:5555/my-rest-service-5
docker push 10.141.212.25:5555/my-rest-service-6

docker stack deploy --compose-file=docker-compose.yml my-compose-swarm
docker stack ls
docker stack services my-compose-swarm
docker stack ps my-compose-swarm
docker stack rm my-compose-swarm


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






