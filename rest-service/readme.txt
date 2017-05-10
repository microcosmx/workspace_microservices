
rest url:
http://rest-travel.service:15000/greeting?name=jay
return json:
{"id":2,"content":"Hello, jay!"}

build:
mvn -Dmaven.test.skip=true clean package

docker:
docker build -t my/rest-travel.service .
docker run -p 15000:15000 --name my-rest-travel.service my/rest-travel.service

mongodb:
docker run -d -p 27017:27017 --name my-rest-travel.service-mongo mongo
docker run -p 15000:15000 --name my-rest-travel.service --link my-rest-travel.service-mongo:my-rest-travel.service-mongo my/rest-travel.service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	rest-travel.service
127.0.0.1	rest-client