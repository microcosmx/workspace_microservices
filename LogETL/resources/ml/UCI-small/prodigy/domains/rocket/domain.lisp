#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Manuela Veloso

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

(LOAD-ROCKET
 (params (<ob>))
 (preconds
  (and 
   (object <ob>)
   (at rocket <pos>)
   (at <ob> <pos>)))
 (effects 
  ((del (at <ob> <pos>))
   (add (inside <ob> rocket)))))

(UNLOAD-ROCKET
 (params (<ob> <pos>))
 (preconds
  (and
   (object <ob>)
   (place <pos>)
   (inside <ob> rocket)
   (at rocket <pos>)))
 (effects 
  ((del (inside <ob> rocket))
   (add (at <ob> <pos>)))))

(MOVE-ROCKET-FAST
 (params ())
 (preconds
  (at rocket A))
 (effects 
  ((del (at rocket A))
   (add (at rocket B)))))

(MOVE-ROCKET-SLOW
 (params ())
 (preconds
  (at rocket C))
 (effects 
  ((del (at rocket C))
   (add (at rocket B)))))
		 ))






(setf *INFERENCE-RULES* '())
