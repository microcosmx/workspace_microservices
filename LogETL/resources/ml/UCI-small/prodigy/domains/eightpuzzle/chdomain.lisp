#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Oren Etzioni

The PRODIGY System was designed and built by Steven Minton, Craig Knoblock,
Dan Kuokka and Jaime Carbonell.  Additional contributors include Henrik Nordin,
Yolanda Gil, Manuela Veloso, Robert Joseph, Santiago Rementeria, Alicia Perez, 
Ellen Riloff, Michael Miller, and Dan Kahn.

This software is made available under the following conditions:
1) PRODIGY will only be used for internal, noncommercial research purposes.
2) The code will not be distributed to other sites without the explicit 
   permission of the designers.  PRODIGY is available by request.
3) Any bugs, bug fixes, or extensions will be forwarded to the designers. 

Send comments or requests to: prodigy@cs.cmu.edu or The PRODIGY PROJECT,
School of Computer Science, Carnegie Mellon University, Pittsburgh, PA 15213.
*******************************************************************************|#

; ***** EIGHT PUZZLE    May 23 '88

(setq *OPERATORS* '(


(LEFT
     (params      (<p>))
     (preconds   (and (vnei <x> <z>) (iv <y>)
                      (place <z> <y> <p>)
		      (pla <x> <y>)
		      ))
     (effects   ((del (pla <x> <y>))
                 (del (place <z> <y> <p>))
		 (add (place <x> <y> <p>))
		 (add (pla <z> <y>)))))

(RIGHT
     (params      (<p>))
     (preconds   (and (vnei <z> <x>) (iv <y>)
                      (place <z> <y> <p>)
		      (pla <x> <y>)
		      ))
     (effects   ((del (pla <x> <y>))
                 (del (place <z> <y> <p>))
		 (add (place <x> <y> <p>))
		 (add (pla <z> <y>)))))

(UP
     (params      (<p>))
     (preconds   (and (hnei <y> <z>) (ih <x>)
                      (place <x> <z> <p>)
		      (pla <x> <y>)
		      ))
     (effects   ((del (pla <x> <y>))
                 (del (place <x> <z> <p>))
		 (add (place <x> <y> <p>))
		 (add (pla <x> <z>)))))

(DOWN
     (params      (<p>))
     (preconds   (and (hnei <z> <y>) (ih <x>)
                      (place <x> <z> <p>)
		      (pla <x> <y>)
		      ))
     (effects   ((del (pla <x> <y>))
                 (del (place <x> <z> <p>))
		 (add (place <x> <y> <p>))
		 (add (pla <x> <z>)))))

))

(setq *INFERENCE-RULES* nil)
