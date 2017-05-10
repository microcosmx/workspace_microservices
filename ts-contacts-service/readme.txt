
rest url:
http://contacts-query-service:12339/welcome
return String:
"Welcome to [ Contacts Query Service ] !"

build:
mvn clean package

docker:
docker build -t my/ts-contacts-service .
docker run -p 12339:12339 --name contacts-query-service my/contacts-query-service

docker run -p 12347:12347 --name ts-contacts-service --link contacts-mongo:contacts-mongo --link ts-sso-service:ts-sso-service my/ts-contacts-service


!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	contacts-query-service

