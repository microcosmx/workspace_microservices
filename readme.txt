
build:
mvn clean package

docker-compose:
docker-compose build
docker-compose up -d
docker-compose down



clean:
docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi
