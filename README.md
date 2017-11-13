# Fault Reproduction of Train Ticket System.
## F7 : ts-error-external-normal

**Description**

F1 is a fault that occurs in a third-party service.
A microservices-based system always invoke a third-party service. Developers always knows nothing 
about the implementation details about this kind of service. When a failure occurs in a third-party
service, a wrong answer will return, or no response, and we cannot trace what happened behind a 
third-party service. 


**Approach to Fault Reproduce**

In our Train Ticket System, we use an external payment service as a third-party service which is 
implemented in Javascript. When we invoke this service, there may be some long delay in payment service,
leading to a timeout exception in ticket system. 
