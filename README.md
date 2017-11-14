# Fault Reproduction of Train Ticket System.
## F2 : ts-error-repoortUI

**Description**

This fault is caused by booking more than one tickets in a short period of time, and their results return out of order. 

A booking message will be reserved in a list until its result returns.

So if a booking result returned and found more than one message from the same user remained in the list, the terminal will throw an exception. 

**Approach to Fault Reproduce**

1. Run this version of application in the server with docker environment
2. Login with the username and password. User can login normally.
3. Select one ticket and click the "Book" button twice quickly.There will be a certain probability of triggering this fault.