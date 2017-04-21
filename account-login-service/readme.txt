
rest url:
http://account-login-service:12345/welcome
return String:
"Welcome to [ Account Login Service ] !"

build:
mvn clean package

docker:
docker build -t my/account-login-service .
docker run -p 12345:12345 --name account-login-service my/account-login-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-login-service

