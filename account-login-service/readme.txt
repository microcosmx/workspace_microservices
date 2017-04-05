
rest url:
http://account-login-service:12345/welcome
return String:
"Welcome to [ Account Login Service ] !"

build:
mvn clean package

docker:
docker build -t my/account-login-service .
docker run -p 12345:12345 --name account-login-service my/account-login-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-login-service


Interface:
    (1)login
        Path:   http://account-login-service:12345/login
        Method: Post - LoginInfo.java: A class that save the required login information.
                           String phoneNum: Login By Phone Number.
                           String password: Password of the account.
        Return: null for login fail, Account.java for login success.