# Fault Reproduction of Train Ticket System.
## F4 : ts-error-SSL

**Description**

F4 is fault of SSL offloading which leads to the response time for some requests is very long.

**Approach to Fault Reproduce**

In our system, we apply SSL to every service. When we send requests about some complex logic, we will 
find that the response time is very long. In this scenario, the faults occurs.
