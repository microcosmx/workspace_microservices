
rest url:
http://account-service:12343/welcome
return String:
"Welcome to [ Accounts Service ] !"

build:
mvn clean package

docker:
docker build -t my/account-service .
docker run -p 12343:12343 --name account-service my/account-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-service