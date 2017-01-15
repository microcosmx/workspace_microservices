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


;reject.lisp

(setq *SCR-OP-REJECT-RULES* 
     '((REJECT-UP
          (lhs (and (current-node <node>)
                    (current-goal <node> (pla <x> <y>))
                    (on-goal-stack <node> (place <x> <y> <p>))
                    (candidate-op <node> UP)
                    (known <node> (and (hnei <z> <y>)
                                       (place <x> <z> <p>)))))
          (rhs (reject operator UP)))

       (REJECT-DOWN
          (lhs (and (current-node <node>)
                    (current-goal <node> (pla <x> <y>))
                    (on-goal-stack <node> (place <x> <y> <p>))
                    (candidate-op <node> DOWN)
                    (known <node> (and (hnei <y> <z>)
                                       (place <x> <z> <p>)))))
          (rhs (reject operator DOWN)))

       (REJECT-RIGHT
          (lhs (and (current-node <node>)
                    (current-goal <node> (pla <x> <y>))
                    (on-goal-stack <node> (place <x> <y> <p>))
                    (candidate-op <node> RIGHT)
                    (known <node> (and (vnei <x> <z>)
                                       (place <z> <y> <p>)))))
          (rhs (reject operator RIGHT)))

       (REJECT-LEFT
          (lhs (and (current-node <node>)
                    (current-goal <node> (pla <x> <y>))
                    (on-goal-stack <node> (place <x> <y> <p>))
                    (candidate-op <node> LEFT)
                    (known <node> (and (vnei <z> <x>)
                                       (place <z> <y> <p>)))))
          (rhs (reject operator LEFT)))

;--------- from eight to six ------
       (REJECT-UforSIX
          (lhs (and (current-node <node>)
	       (current-goal <node> (pla <x> 2))
	       (candidate-op <node> UP)
	       (known <node> (and (place 1 1 1)
	                          (place 2 1 2)
				  (place 3 1 3)))))
          (rhs (reject operator UP)))


;--------------------------------------------------------------
     
     (REJECT-for-8
         (lhs (and (current-node <node>)
	           (current-goal <node> (place 2 1 2))
		   (candidate-op <node> LEFT)
		   (known <node> (place 1 1 1))))
        (rhs       (reject operator LEFT)))
     
     (REJECT-for-8
         (lhs (and (current-node <node>)
	           (current-goal <node> (pla 2 1))
		   (on-goal-stack <node> (place 2 1 2))
		   (candidate-op <node> LEFT)
		   (known <node> (place 1 1 1))))
        (rhs       (reject operator LEFT)))

       ))
