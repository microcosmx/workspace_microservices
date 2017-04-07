
rest url:
http://order-cancel-service:12033/welcome
return String:
"Welcome to [ Order Cancel Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-cancel-service .
docker run -p 12033:12033 --name order-cancel-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-cancel-service

Interface:

    (1)Cancel An Order
        Path: http://order-cancel-service:12033/cancelOrder
        Method: POST - CancelOrderInfo.java
                           UUID accountId: The owner of the order.
                           UUID orderId: The order that will be canceled.
        Return: null for order-not-found, Order.java for success.

