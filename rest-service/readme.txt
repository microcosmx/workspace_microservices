
rest url:
http://rest-service:15000/greeting?name=jay
return json:
{"id":2,"content":"Hello, jay!"}

build:
mvn clean package

docker:
docker build -t my/rest-service .
docker run -p 15000:15000 --name rest-service my/rest-service

mongodb:
docker run -d -p 27017:27017 --name my-rest-service-mongo mongo
docker run -p 15000:15000 --name my-rest-service --link my-rest-service-mongo:my-rest-service-mongo my/rest-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	rest-service
127.0.0.1	rest-client