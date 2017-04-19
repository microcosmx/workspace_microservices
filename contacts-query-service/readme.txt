
rest url:
http://contacts-query-service:12339/welcome
return String:
"Welcome to [ Contacts Query Service ] !"

build:
mvn clean package

docker:
docker build -t my/contacts-query-service .
docker run -p 12339:12339 --name contacts-query-service my/contacts-query-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	contacts-query-service

Interface:

    (1)Find All Contacts of An Account
        Path: http://query-service:12332/findContacts/{accountIdStr}
        Method: Get
        Return: ArrayList<Contacts>
