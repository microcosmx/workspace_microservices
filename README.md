# Fault Reproduction of Train Ticket System.
## F5 : ts-error-cross-timeout-status-chance

**Description**

F5 is such kind of fault that caused by the resource competition of multiple request.
Sometimes a microservice has its maximum number of connections. Suppose we have 3 services,
A, B, C. A and B both call C for some specific information. If A send some requests to C, but before
that B has sent some requests to C and these requests are too complex to process at a short time, then 
the requests from A must wait until the requests from B completed, leading to a timeout exception.

**Approach to Fault Reproduce**

?

This fault is reproduced in ticker reserve process. The service duty for High-Speed Train Ticket Reserve
only support processing 2 requests at the same time. We firstly send a complex request and then send two 
tickets reserve requests. In this secnario, the 2nd requests must waiting for the first complex request complete,
which lead to a timeout failure.
