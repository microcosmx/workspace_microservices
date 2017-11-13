# Fault Reproduction of Train Ticket System.
## F10 : ts-error-normal

**Description**

F10 is simply a normal logic fault in one business process. 
This kind of fault occurs due to the defects in the implementation of a business process.


**Approach to Fault Reproduce**

In our Train Ticket System, the name and the ID Card number is required for booking a ticket.
In our society, one ID card number only mapping one name. Thus, if two user input two distinct
name but the document number is same, one of the user must be rejected. In our system, if one 
document number has been occupied by one name, another user inputs the same document number but 
another name, he/she still successfully gets a ticket. In this scenario, a logic fault occurs.
