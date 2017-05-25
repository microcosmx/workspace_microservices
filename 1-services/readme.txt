
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


rest url:
http://localhost:16006/hello6?cal=50
http://rest-service-6:16006/hello6?cal=50



swarm:
docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm