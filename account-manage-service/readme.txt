
rest url:
http://account-service:12343/welcome
return String:
"Welcome to [ Accounts Service ] !"

build:
mvn clean package

docker:
docker build -t my/account-service .
docker run -p 12343:12343 --name account-service my/account-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-service

Interface:

    (1)Find a Account By Phone Num
        Path: http://account-service:12343/findAccount/{phoneNum}
        Method: Get
        Return: null for account-not-found, Account.java for finding success.

    (2)Save the Account Information
        Path: http://account-service:12343/saveAccountInfo
        Method: PUT - Account.java
        Return: null for account-not-found, Account.java for success.

    (3)Change Password
        Path: http://account-service:12343/changePassword
        Method: PUT - NewPasswordInfo.java
                          UUID id: The account Id you will change.
                          String oldPassword: The old password of the account.
                          String newPassword: The new password of the account.
        Return: null for account-not-found & wrong-password, Account.java for success.
