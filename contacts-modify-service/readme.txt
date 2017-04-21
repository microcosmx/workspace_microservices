
rest url:
http://query-service:12332/welcome
return String:
"Welcome to [ Contacts Service ] !"

build:
mvn clean package

docker:
docker build -t my/query-service .
docker run -p 12332:12332 --name query-service my/query-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	query-service

