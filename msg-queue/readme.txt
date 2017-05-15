
queue:
docker-compose up

build:
mvn clean package

run:
java -jar target/gs-messaging-rabbitmq-0.1.0.jar

docker run -d -p 5672:5672 -p 15672:15672 --name msg-queue rabbitmq:management