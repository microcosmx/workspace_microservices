
rest url:
http://contacts-service:12332/welcome
return String:
"Welcome to [ Contacts Service ] !"

build:
mvn clean package

docker:
docker build -t my/contacts-service .
docker run -p 12332:12332 --name contacts-service my/contacts-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	contacts-service