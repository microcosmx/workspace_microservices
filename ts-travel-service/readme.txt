
rest url:
http://travel

build:
mvn clean package

docker:
docker build -t my/ts-travel-service .
docker run -d -p 12346:12346 --name ts-travel-service my/ts-travel-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-travel-service