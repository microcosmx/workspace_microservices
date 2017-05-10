
rest url:
http://rest-client:15001/hello?name=jay1
return json:
{"id":2,"content":"Hello, jay1!"}

build:
mvn -Dmaven.test.skip=true clean package

docker:
docker build -t my/rest-client .
docker run -p 15001:15001 --name my-rest-client --link rest-travel.service:rest-travel.service my/rest-client

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	rest-travel.service
127.0.0.1	rest-client