# Fault Reproduction of Train Ticket System.
## F12 : ts-error-processes-seq-status(chance)

**Description**

F12 is one kind of faults that occurs due to the specific status of some microservice.
The word "status" has a wide range of meanings, for example, a global value in one microservice.
If a microservice is in some specific "status", the fault occurs. This kind of faults only occurs in 
some specific status of a microservice.

**Approach to Fault Reproduce**

?

In our train ticket system, one user cannot reserve more than 5 tickets in one hour. The system checks 
the number of not used tickets of the user before he/she successfully reserve a ticket. If the number of 
the tickets exceeds 5, the request for booking ticket should be denied. However, when the microservice
responsible for checking the number of the tickets stays on a pecific status, the microservice will cause
a failure by letting the request pass the checking.