# Fault Reproduction of Train Ticket System.
## F13 : ts-error-queue

**Description**

F13 is a fault that occurs in a usage scenario involving multiple requests. A user may click
and send many requests shortly. However, due to some reasons like network congestion, some requests
have a delay in execution, leading to the unexpected execution order of these requests. As a result,
the user will get a wrong response, then the fault occurs.

**Approach to Fault Reproduce**

In our Train Ticket System, we send 3 requests to reserve ticket, pay for a ticket, and cancel a ticket, respectively. 
Each of the requests uses message queue to communicate with other microservices. 
Sometimes, a user clicks too fast even the previous request has not completed. 
In this case, the ticket cancellation process may completed before the ticket payment, 
leading to a failure. This fault is similar to F1. The difference is that F1 only involves a single request with many
asynchronous tasks, while F13 involves multiple requests.
