
rest url:
http://station

build:
mvn clean package

docker:
docker build -t my/ts-station-service .
docker run -d -p 12345:12345 --name ts-station-service my/ts-station-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-station-service