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



;; no vars in the operators -- yg 10/19/87

; Operators

(setq *OPERATORS* '(


(UNSTACK
 (params (<ob> <underob>))
 (preconds
	  (and (object <ob>)
               (object <underob>)
	       (on <ob> <underob>)
	       (clear <ob>)
	       (arm-empty)))
 (effects ((del (on <ob> <underob>))
	   (del (clear <ob>))
	   (add (holding <ob>))
	   (add (clear <underob>)))))

(PICK-UP
 (params (<ob>))
 (preconds (and (object <ob>)
		(clear <ob>)
		(on-table <ob>)
	        (arm-empty)))
 (effects ((del (on-table <ob>))
	   (del (clear <ob>))
	   (add (holding <ob>)))))

    

(PUT-DOWN
 (params (<ob>))
 (preconds (and (object <ob>)
	        (holding <ob>)))
 (effects ((del (holding <ob>))
	   (add (clear <ob>))
	   (add (on-table <ob>)))))


(STACK
 (params (<ob> <underob>))
 (preconds (and (object <ob>)
		(object <underob>)
    	        (clear <underob>)
	        (holding <ob>)))
 (effects ((del (holding <ob>))
	   (del (clear <underob>))
	   (add (clear <ob>))
	   (add (on <ob> <underob>)))))

		 ))

(setq *INFERENCE-RULES* '(


(INFER-ARM-EMPTY
 (params nil)
 (preconds
     (~ (exists (<ob>) (holding <ob>))))
 (effects ((add (arm-empty)))))

		       ))





