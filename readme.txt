
build:
mvn clean package

docker-compose:
docker-compose build
docker-compose up -d
docker-compose down

docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi
直达车票分配比例
