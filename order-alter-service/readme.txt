
rest url:
http://order-alter-service:12032/welcome
return String:
"Welcome to [ Order Alter Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-alter-service .
docker run -p 12032:12032 --name order-alter-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-alter-service

Interface:

    (1)Find All Contacts of An Account
        Path: http://order-alter-service:12032/alterOrder
        Method: POST - OrderAlterInfo
                           UUID accountId
                           UUID previousOrderId: The order that will be canceled.
                           Order newOrderInfo: The order will be created after the previous order canceled.
        Return: null for old-order-not-found, Order.java for success.
