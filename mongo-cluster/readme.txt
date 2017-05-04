
build:
mvn clean package

run:
java -jar target/gs-accessing-data-mongodb-0.1.0.jar

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