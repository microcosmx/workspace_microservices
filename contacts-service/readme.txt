
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

Interface:

    (1)Find All Contacts of An Account
        Path: http://contacts-service:12332/findContacts/{accountIdStr}
        Method: Get
        Return: ArrayList<Contacts>

    (2)Create A New Contact
        Path: http://contacts-service:12332/createNewContacts
        Method: Post - AddContactsInfo.java
                           UUID accountId: This contact belongs to which account
                           String name: The name of the contact
                           int documentType
                           String documentNumber
                           String phoneNumber
        Return: null for contact already exists, Contact.java for success

    (3)Modify A Contact
        Path: http://contacts-service:12332/saveContactsInfo
        Method: Put - Contact.java
        Return: null for contact-not-found, Contact.java for modify success.