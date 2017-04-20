
rest url:
http://order-service:12031/welcome
return String:
"Welcome to [ Accounts Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-service .
docker run -p 12031:12031 --name order-service my/order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-service

Interface:
    (1)Query An Order
          Path: http://order-alter-service:12031/findOrders/{accountId}
          Method: GET
          Return: ArrayList of the orders belongs to the accoung
    (2)Create New Orders.
          Path: http://order-alter-service:12031/createNewOrders
          Method: POST
          Return: Order.java you created.
    (2)Modify Order Information
          Path: http://order-alter-service:12031/createNewOrders
          Method: POST
          Return: null for order-not-found, Order.java for success.