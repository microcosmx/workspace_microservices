# Fault Reproduction of Train Ticket System.
## F18 : ts-error-redis

**Description**
This fault is caused by the error of redis in the operation

of getting the saved logined user data. When the user login,

the system alert the success information. However, the fail of

login token verification occur when user choose to look up his contacts.


**Approach to Fault Reproduce**
1. Run this version of application in the server with docker environment
2. Login with the username and password. User can login normally
3. Add a new contact. This operation should be successful
4. When booking a ticket, the display of contacts list fail. And the terminal
of server print the error information: "[ContactsService][VerifyLogin] Fail"

