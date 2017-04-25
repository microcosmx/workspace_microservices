
rest url:
http://order-service:12031/welcome
return String:
"Welcome to [ Accounts Service ] !"

build:
mvn clean package

java:
java -Xmx200m -jar target/order-create-service-1.0.jar

docker:
docker build -t my/order-create-service .
docker run -d -p 12031:12031 --name order-create-service my/order-create-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-service

