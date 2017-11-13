# Fault Reproduction of Train Ticket System.
## F1 : ts-error-process-seq

**Description**

F1 is a fault that occurs in using asynchronous tasks.
If two business events have before-after relationship, which means that 
the 2nd event must complete after the completion of the 1st event, we must use message 
sequence control when we trying to apply the technique of asynchronous tasks to process these two events. 
In this scenario, if we send asynchronous messages without message sequence control, this 
kind of fault may occur.


**Approach to Fault Reproduce**

We reproduce this fault in the ticket cancellation process in our Train Ticket System. 
In our ticket cancellation process, our system return the ticket fee to user account firstly and 
then set the ticket status to CANCEL. The event of refunding and the event of setting ticket
status are doing asynchronously. If the first event is delayed due to some reason, the ticket status 
will be set to CANCEL before the user get the refund, the the refunding process will fail. In 
this scenario, this fault occurs.
