
rest url:
http://order-alter-service:12344/welcome
return String:
"Welcome to [ Order Alter Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-alter-service .
docker run -p 12344:12344 --name order-alter-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-alter-service