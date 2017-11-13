# Fault Reproduction of Train Ticket System.
## F3 : ts-error-dockerJVM

**Description**

F3 is a kind of fault caused by imporper configurations of Docker and JVM.

**Approach to Fault Reproduce**

In our Train Ticket System, the maximum memory size order-service(processing orders of High-speed train) 
has been configured, but the same configuration is not configured in 
oder-other-service(processing order about normal speed train). When both of two microservices
is out-of-memory, the order-service will restart before throws the exception. In this secenario,
we will observe that the order-service is unavailable periodically.

