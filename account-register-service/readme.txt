
rest url:
http://account-register-service:12344/welcome
return String:
"Welcome to [ Account Register Service ] !"

build:
mvn clean package

docker:
docker build -t my/account-register-service .
docker run -p 12344:12344 --name account-register-service my/account-register-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-register-service