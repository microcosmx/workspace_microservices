
build:
mvn clean package

docker-compose:
docker-compose build
docker-compose up -d
docker-compose down