
rest url:
http://verificationCode
return an image which contains verification code,like:
2468,which is an image

build:
mvn clean package

docker:
docker build -t my/verificationCode-service .
docker run -p 16001:16001 --link verificationCode-service:verificationCode-service my/verificationCode-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	verificationCode-service