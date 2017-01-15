#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Steven Minton, Craig Knoblock, Dan Kuokka and Jaime Carbonell

The PRODIGY System was designed and built by Steven Minton, Craig Knoblock,
Dan Kuokka and Jaime Carbonell.  Additional contributors include Henrik Nordin,
Yolanda Gil, Manuela Veloso, Robert Joseph, Santiago Rementeria, Alicia Perez, 
Ellen Riloff, Michael Miller, and Dan Kahn.

The PRODIGY system is experimental software for research purposes only.
This software is made available under the following conditions:
1) PRODIGY will only be used for internal, noncommercial research purposes.
2) The code will not be distributed to other sites without the explicit 
   permission of the designers.  PRODIGY is available by request.
3) Any bugs, bug fixes, or extensions will be forwarded to the designers. 

Send comments or requests to: prodigy@cs.cmu.edu or The PRODIGY PROJECT,
School of Computer Science, Carnegie Mellon University, Pittsburgh, PA 15213.
*******************************************************************************|#




; Operators

(setq *OPERATORS* '(


(PICK-UP
 (params (<ob1>))
 (preconds (and (object <ob1>)
		(clear <ob1>)
		(on-table <ob1>)
	        (arm-empty)))
 (effects ((del (on-table <ob1>))
	   (del (clear <ob1>))
	   (del (arm-empty))
	   (add (holding <ob1>)))))



(PUT-DOWN
 (params (<ob>))
 (preconds (and (object <ob>)
	        (holding <ob>)))
 (effects ((del (holding <ob>))
	   (add (clear <ob>))
	   (add (arm-empty))
	   (add (on-table <ob>)))))

(STACK
 (params (<sob> <sunderob>))
 (preconds (and (object <sob>)
		(object <sunderob>)
    	        (clear <sunderob>)
	        (holding <sob>)))
 (effects ((del (holding <sob>))
	   (del (clear <sunderob>))
	   (add (arm-empty))
	   (add (clear <sob>))
	   (add (on <sob> <sunderob>)))))


(UNSTACK
 (params (<us-ob> <underob>))
 (preconds
	  (and (object <us-ob>)
               (object <underob>)
	       (on <us-ob> <underob>)
	       (clear <us-ob>)
	       (arm-empty)))
 (effects ((del (on <us-ob> <underob>))
	   (del (clear <us-ob>))
	   (del (arm-empty))
	   (add (holding <us-ob>))
	   (add (clear <underob>)))))
    




		 ))

(setq *INFERENCE-RULES* '(


		       ))





