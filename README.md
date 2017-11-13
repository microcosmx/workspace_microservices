# Fault Reproduction of Train Ticket System.
## F11 : ts-error-bomupdate

**Description**

F11 is a fault due to the lack of sequence control. Note that such a fault may not always occur, making 
it difficult to analyze. Sometimes if a return value is too far from the normal value, the microsevice 
will recheck the correctness of the value and correct it. But this process is not always happen, making 
the result sometimes wrong but sometimes right.

**Approach to Fault Reproduce**

In out train ticket system, there are two microservices set the some value in the database in ticket cancellation
process. Due to the lack of control like F1, the two microservices may set the value in a wrong sequence. However,
the second microservice that set the value may recheck the value and correct the value. The recheck process is not always 
happen. If two microservices set the value in a wrong sequence but the recheck process is not executed, this fault occurs.
