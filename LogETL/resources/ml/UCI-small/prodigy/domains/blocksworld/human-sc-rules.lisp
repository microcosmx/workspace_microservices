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



(setq SCR-NODE-SELECT-RULES nil)
(setq SCR-GOAL-SELECT-RULES 
      '((SELECT-FIRST-GOAL
  	  (lhs (and (current-node <node>)
		    (not-top-level-node <node>)
                    (primary-candidate-goal <node> <goal>)))
           (rhs (select goal <goal>)))
       ))

(setq SCR-OP-SELECT-RULES  
    '((UNSTACK-FOR-HOLDING1
          (lhs (and (current-node <node>)
	            (current-goal <node> (holding <x>))
		    (known <node> (on <x> <y>))
		    (candidate-op <node> UNSTACK)))
	  (rhs (select operator UNSTACK)))

      (UNSTACK-FOR-CLEAR
          (lhs (and (current-node <node>)
	            (current-goal <node> (clear <x>))
		    (known <node> (~ (holding <x>)))
		    (candidate-op <node> UNSTACK)))
	  (rhs (select operator UNSTACK)))
     ))

     


(setq SCR-BINDINGS-SELECT-RULES nil)
(setq SCR-NODE-REJECT-RULES nil)
(setq SCR-GOAL-REJECT-RULES 
    '((BOTTOM-UP-CONSTRUCTION
	  (lhs (and (current-node <node>)
		    (is-top-level-node <node>)
		    (candidate-goal <node> (on <a> <b>))
		    (candidate-goal <node> (on <b> <c>))))
	  (rhs (reject goal (on <a> <b>))))

      (HOLD-LAST
	  (lhs (and (current-node <node>)
		    (is-top-level-node <node>)
		    (candidate-goal <node> (holding <a>))
		    (candidate-goal <node> <g1>)
		    (candidate-goal <node> <g2>)
		    (not-equal <g1> <g2>)))
	  (rhs (reject goal (holding <a>))))

      (ON-TABLE-BEFORE-ON
	  (lhs (and (current-node <node>)
		    (is-top-level-node <node>)
		    (candidate-goal <node> (on-table <a>))
		    (candidate-goal <node> (on <b> <a>))))
	  (rhs (reject goal (on <b> <a>))))

     ))

(setq SCR-OP-REJECT-RULES nil)
		    
(setq SCR-BINDINGS-REJECT-RULES 
      '((UNSTACK-FROM-GUY-ITS-ON
	  (lhs (and (current-node <n>)
		    (current-goal <n> (holding <x>))
		    (current-op <n> UNSTACK)
		    (candidate-bindings <n> (<x> <u>))
		    (known <n> (~ (on <x> <u>)))))
 	  (rhs (reject bindings (<x> <u>))))

	(UNSTACK-GUY-ON-ME1
	  (lhs (and (current-node <n>)
		    (current-goal <n> (clear <x>))
		    (current-op <n> UNSTACK)
		    (candidate-bindings <n> (<o> <x>))
		    (known <n> (~ (on <o> <x>)))))
 	  (rhs (reject bindings (<o> <x>))))))


(setq SCR-NODE-PREFERENCE-RULES nil)
(setq SCR-GOAL-PREFERENCE-RULES 
    '((PREFER-ON-TO-CLEAR1
	  (lhs (and (current-node <n>)
		    (candidate-goal <n> (clear <x>))
		    (candidate-goal <n> (on <x> <y>))))
	  (priority 1)
	  (rhs (prefer goal (on <x> <y>) (clear <x>))))

; This is the correct version of HOLD-LAST, found late.
;      (HOLD-LAST
;	  (lhs (and (current-node <node>)
;		    (candidate-goal <node> (holding <a>))
;		    (candidate-goal <node> <g2>)))
;	  (priority 1)
;	  (rhs (prefer goal <g2> (holding <a>))))

       (PREFER-CLEAR-TO-ON
	  (lhs (and (current-node <n>)
		    (candidate-goal <n> (clear <x>))
		    (candidate-goal <n> (on <z> <y>))
		    (not-equal <z> <x>)))
	  (priority 1)
	  (rhs (prefer goal (clear <x>) (on <z> <y>))))
     ))


(setq SCR-OP-PREFERENCE-RULES 
    '((PUTDOWN-PREFER
	  (lhs (and (current-node <n>)
		    (current-goal <n> (arm-empty))
		    (candidate-op <n> PICK-UP)
		    (candidate-op <n> <other-op>)))
	  (priority 1)
	  (rhs (prefer operator PUTDOWN <other-op>)))))
		    
		    

(setq SCR-BINDINGS-PREFERENCE-RULES nil)
