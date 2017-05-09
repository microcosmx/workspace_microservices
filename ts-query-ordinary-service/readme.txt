
rest url:
http://queryordinary

return the information about the ticket,include:id,trainNumber,departureStation,arrivalStation,departureTime,
arrivalTime,starting and destination.the query should include three information:starting,destination and date.
query in json:
{"starting":�Ϻ�,"destination":"̫ԭ","date":"2017-03-21T21:47:04.432+0800"}
ps: date formate:"yyyy-MM-dd'T'HH:mm:ss.SSSZ"
return json:
[{"id":"58d12e5f88d07cbbc7a8eda0","trainNumber":"Z268","departureStation":"�Ϻ�","arrivalStation":"̫ԭ��","departureTime":1490107254000,"arrivalTime":1490154023000,"destination":"̫ԭ","starting":"�Ϻ�"},{"id":"58d12e9188d07cbbc7a8eda1","trainNumber":"Z196","departureStation":"�Ϻ�","arrivalStation":"̫ԭ","departureTime":1490107254000,"arrivalTime":1490240423000,"destination":"̫ԭ","starting":"�Ϻ�"}]

build:
mvn clean package

docker:
docker build -t my/queryordinary-service .
docker run -p 18001:18001 --link queryordinary-service:queryordinary-service my/queryordinary-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	queryordinary-service