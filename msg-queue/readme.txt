
queue:
docker-compose up

build:
mvn clean package

run:
java -jar target/gs-messaging-rabbitmq-0.1.0.jar