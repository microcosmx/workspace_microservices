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


(setq *SCR-NODE-SELECT-RULES* nil)
(setq *SCR-GOAL-SELECT-RULES* 
      '((SELECT-FIRST-GOAL
  	  (lhs (and (current-node <node>)
		    (not-top-level-node <node>)
                    (primary-candidate-goal <node> <goal>)))
           (rhs (select goal <goal>)))))
(setq *SCR-OP-SELECT-RULES* nil)
(setq *SCR-BINDINGS-SELECT-RULES* nil)
(setq *SCR-NODE-REJECT-RULES* nil)
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES* 
  '(
	    (DONT-MAKE-OBJ1
	     (lhs (and (current-node <node>)
		       (current-goal <node> (is-object <x>))
		       (candidate-op <node> WELD)))
	     (rhs (reject operator WELD)))

	    (DONT-MAKE-OBJ2
	     (lhs (and (current-node <node>)
		       (current-goal <node> (is-object <x>))
		       (candidate-op <node> BOLT)))
	     (rhs (reject operator BOLT)))))

(setq *SCR-BINDINGS-REJECT-RULES* nil)
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* nil)

;  '((RTEST
;      (lhs (and (current-node <n>)
;	        (candidate-goal <n> (shape <x> CYLINDRICAL))
;	        (candidate-goal <n> (polished <x>))))
;      (rhs (perfer (shape <x> CYLINDRICAL) (polished <x>))))))

(setq *SCR-OP-PREFERENCE-RULES* 	nil)
(setq *SCR-BINDINGS-PREFERENCE-RULES* nil)
