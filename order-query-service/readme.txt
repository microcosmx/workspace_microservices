
rest url:
http://order-query-service:14221/welcome
return String:
"Welcome to [ Order Query Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-query-service .
docker run -p 14221:14221 --name order-query-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-query-service

Interface:

    (1)Query An Order
        Path: http://order-query-service:14221/queryOrders
        Method: POST - QueryInfo.java
                           UUID accountId: The owner of the orders
                           Date travelDateStart: Travel date from
                           Date travelDateEnd: Travel date to
                           Date boughtDateStart: Bought date from
                           Date boughtDateEnd: Bought date to
                           int state: Which state of orders do you want.
                           boolean enableTravelDateQuery: use travel date search or not
                           boolean enableBoughtDateQuery: use bought date search or not
                           boolean enableStateQuery: use state search or not
        Return: ArrayList of the orders.

