
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

Interface:
    (1)Create A New Contact
        Path: http://query-service:12332/createNewContacts
        Method: Post - AddContactsInfo.java
                           UUID accountId: This contact belongs to which account
                           String name: The name of the contact
                           int documentType
                           String documentNumber
                           String phoneNumber
        Return: null for contact already exists, Contact.java for success

    (2)Modify A Contact
        Path: http://query-service:12332/saveContactsInfo
        Method: Put - Contact.java
        Return: null for contact-not-found, Contact.java for modify success.