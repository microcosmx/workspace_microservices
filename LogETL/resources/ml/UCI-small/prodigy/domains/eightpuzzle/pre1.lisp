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


; pre1.lisp
;

(setq *SCR-OP-PREFERENCE-RULES*
    '(
      (PREFER-for-psix3  
         (lhs (and (current-node <node>)
	           (current-goal <node> (place <x> <y> <p>))
		   (candidate-op <node> UP)
		   (candidate-op <node> LEFT)
		   (known <node> (and (vnei <z> <x>) (vnei <x> <zz>)
		                      (hnei <y> <w>) (pla <z> <w>)
				      (place <zz> <w> <p>)))))
         (rhs (prefer operator UP LEFT)))

       (PREFER-for-six4
          (lhs (and (current-node <node>)
	            (current-goal <node> (pla <z> <y>))
		    (on-goal-stack <node> (place <x> <y> <p>))
                    (candidate-op <node> LEFT)
		    (candidate-op <node> RIGHT)
                    (known <node> (and (vnei <x> <z>) (vnei <z> <zz>)
		                       (hnei <y> <w>) (pla <zz> <w>)
				       (place <z> <w> <p>)))))
            (rhs (prefer operator RIGHT LEFT)))

       ))
