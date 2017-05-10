
build:
mvn -Dmaven.test.skip=true clean package

run:
java -jar target/gs-accessing-data-mongodb-0.1.0.jar

docker:
docker run -d --name my-mongo mongo
docker build -t my/gs-accessing-data-mongodb .
docker run --name my-gs-accessing-data-mongodb --link my-mongo:mongo-local my/gs-accessing-data-mongodb
(mongo-local is in config file: resources/application.yml)

docker run -p 12342:12342 --name ts-login-service --link register-mongo:mongo-local --link ts-sso-service:ts-sso-service my/ts-login-service



single mongodb:
cd mongo-cluster
docker-compose up
docker run -d -p 27017:27017 --name my-mongo mongo

mongo-cluster:
cd docker-mongo-cluster
docker-compose up
first time run: 
./initial
clear mongodb cluster data: 
./reset 
login in mongo-cluster docker container:
docker exec -it dockermongocluster_mongos1_1 mongo --port 21017