
rest url:
http://train

build:
mvn clean package

docker:
docker build -t my/ts-train-service .
docker run -d -p 14567:14567 --name ts-train-service my/ts-train-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-train-service