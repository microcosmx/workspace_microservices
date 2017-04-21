
rest url:
http://order-service:12031/welcome
return String:
"Welcome to [ Accounts Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-service .
docker run -p 12031:12031 --name order-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-service

