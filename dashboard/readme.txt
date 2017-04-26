
rest url:
http://localhost:6000

build:
mvn clean package -Dmaven.test.skip=true

java:
java -jar target/dashboard.jar

docker:
docker build -t my/dashboard .
docker run -p 6000:6000 --name my-dashboard my/dashboard