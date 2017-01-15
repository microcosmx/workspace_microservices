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
           (rhs (select goal <goal>)))))
(setq SCR-OP-SELECT-RULES 
      '(
;	(DRILL-IF-HOT
;	 (lhs (and (current-node <node>)
;		   (current-goal <node> (has-hole <obj> <size> <ori>))
;		   (candidate-op <node> DRILL-PRESS)
;		   (known <node> (temperature <obj> HOT))))
;	 (rhs (select operator DRILL-PRESS)))
;
;	(IMMERSION-PAINT-IF-HOT
;	 (lhs (and (current-node <node>)
;		   (current-goal <node> (painted <obj> <char>))
;		   (candidate-op <node> IMMERSION-PAINTER)
;		   (known <node> (temperature <obj> HOT))))
;	 (rhs (select operator IMMERSION-PAINTER)))
	))
(setq SCR-BINDINGS-SELECT-RULES nil)
(setq SCR-NODE-REJECT-RULES 
      '(
	(CHECK-FOR-IMPOSSIBLE-PAINTING
	 (lhs (and (candidate-node <node>)
		   (is-top-level-goal <node> (painted <obj> <paint>))
		   (known <node> (and (~ (sprayable <paint>))
				      (~ (have-paint-for-immersion <paint>))))))
	 (rhs (reject node <node>)))

	(CHECK-FOR-IMPOSSIBLE-PAINTING-WHEN-ALREADY-PAINTED
	 (lhs (and (candidate-node <node>)
		   (in-goal-exp <node> (painted <obj> <paint>))
		   (or (is-top-level-goal <node> (shape <obj> CYLINDRICAL))
		       (is-top-level-goal <node> 
				(surface-condition <obj> SMOOTH)))
		   (known <node> (and (~ (sprayable <paint>))
				      (~ (have-paint-for-immersion <paint>))))))
	 (rhs (reject node <node>)))

	(CHECK-FOR-IMPOSSIBLE-HOLING
	 (lhs (and (candidate-node <node>)
		   (is-top-level-goal <node> (has-hole <obj> <hole> <ori>))
		   (known <node> (and (or (~ (is-drillable <obj> <ori>))
					  (~ (have-bit <hole>)))
				      (~ (is-punchable <obj> <hole> <ori>))))))
	 (rhs (reject node <node>)))

	(CHECK-FOR-IMPOSSIBLE-JOINING
	 (lhs (and (candidate-node <node>)
		   (is-top-level-goal <node> (joined <obj1> <obj2> <ori>))
		   (known <node> (~ (can-be-welded  <obj1> <obj2> <ori>)))
		   (known <node> 
		       (forall (<b>) (is-bolt <b>)
			   (and (is-width <w> <b>)
	   			(or (and (or (~ (is-drillable <obj1> <ori>))
				             (~ (have-bit <w>)))
					 (~ (is-punchable <obj1> <w> <ori>)))
	   		            (and (or (~ (is-drillable <obj2> <ori>))
				             (~ (have-bit <w>)))
				         (~ (is-punchable <obj2> <w> <ori>)))))
		       ))))
	 (rhs (reject node <node>)))

	(CHECK-FOR-IMPOSSIBLE-JOINING-AND-POLISHING1
	 (lhs (and (candidate-node <node>)
		   (is-top-level-goal <node> (joined <obj1> <obj2> <ori>))
	    	   (known <node> 
			 (~ (can-be-welded <obj1> <obj2> <orientation-w>)))
		   (or (is-top-level-goal <node> 
				(surface-condition <obj1> POLISHED))
		       (is-top-level-goal <node> 
	   			(surface-condition <obj1> SMOOTH)))	  
		   (known <node> 
		   	(or (~ (is-drillable <obj1> <ori>))
	  		    (forall (<b>) (is-bolt <b>)
			   	(and (is-width <w> <b>)
	  		             (~ (have-bit <w>))))))))
	 (rhs (reject node <node>)))

	(CHECK-FOR-IMPOSSIBLE-JOINING-AND-POLISHING2
	 (lhs (and (candidate-node <node>)
		   (is-top-level-goal <node> (joined <obj1> <obj2> <ori>))
	    	   (known <node>
			(~ (can-be-welded <obj1> <obj2> <orientation-w>)))
		   (or (is-top-level-goal <node> 
				(surface-condition <obj2> POLISHED))
		       (is-top-level-goal <node> 
	   			(surface-condition <obj2> SMOOTH)))	  
		   (known <node> 
		   	(or (~ (is-drillable <obj2> <ori>))
	  		    (forall (<b>) (is-bolt <b>)
			   	(and (is-width <w> <b>)
	  		             (~ (have-bit <w>))))))))
	 (rhs (reject node <node>)))


	))

(setq SCR-GOAL-REJECT-RULES 
      '(
	(DONT-CHANGE-SURFACE-CONDITION-BEFORE-SHAPE
	 (lhs (and (current-node <node>)
		   (is-top-level-node <node>)
		   (candidate-goal <node> (shape <obj> <shape>))
	           (candidate-goal <node> (surface-condition <obj> <cond>))))
	 (rhs (reject goal (surface-condition <obj> <cond>))))

	(DONT-CHANGE-SHAPE-BEFORE-PAINTING
	 (lhs (and (current-node <node>)
		   (is-top-level-node <node>)
		   (candidate-goal <node> (shape <obj> <shape>))
		   (candidate-goal <node> (painted <obj> <paint>))))
	 (rhs (reject goal (painted <obj> <paint>))))


	(PAINT-BEFORE-SMOOTH
	 (lhs (and (current-node <node>)
		   (is-top-level-node <node>)
		   (candidate-goal <node> (surface-condition <obj> SMOOTH))
		   (candidate-goal <node> (painted <obj> <paint>))))
	 (rhs (reject goal (painted <obj> <paint>))))

	(SMOOTH-BEFORE-HOLE
         (lhs (and (current-node <node>)
		   (is-top-level-node <node>)
		   (candidate-goal <node> (has-hole <obj1> <w> <ori>))
		   (candidate-goal <node> (surface-condition <obj> <x>))))
	 (rhs (reject goal (surface-condition <obj> <x>))))


	(DO-JOINS-LAST
	 (lhs (and (current-node <node>)
		   (is-top-level-node <node>)
		   (candidate-goal <node> (joined <obj1> <obj2> <ori>))
		   (candidate-goal <node> <g2>)
		   (predicate <p> <g2>)
		   (not-equal <p> joined)))
	 (rhs (reject goal (joined <obj1> <obj2> <ori>))))
	))

(setq SCR-OP-REJECT-RULES 
      '(

;	(DONT-ROLL-IF-YOU-WANT-POLISH
;	 (lhs (and (current-node <node>)
;		   (is-top-level-goal <node> (surface-condition <obj> POLISHED))
;		   (current-goal <node> (shape <obj> CYLINDRICAL))
;		   (candidate-op <node> ROLL)))
;	 (rhs (reject operator ROLL)))
	
	(DONT-ROLL
	 (lhs (and (current-node <node>)
		   (candidate-op <node> ROLL)))
	 (rhs (reject operator ROLL)))

	(DONT-POLISH-TO-UNPOLISH
	 (lhs (and (current-node <node>)
		   (current-goal <node> (~ (surface-condition <obj> POLISHED)))
		   (candidate-op <node> POLISH)))
	 (rhs (reject operator POLISH)))

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

(setq SCR-BINDINGS-REJECT-RULES nil)
(setq SCR-NODE-PREFERENCE-RULES nil)
(setq SCR-GOAL-PREFERENCE-RULES nil)

;  '((RTEST
;      (lhs (and (current-node <n>)
;	        (candidate-goal <n> (shape <x> CYLINDRICAL))
;	        (candidate-goal <n> (polished <x>))))
;      (rhs (perfer (shape <x> CYLINDRICAL) (polished <x>))))))

(setq SCR-OP-PREFERENCE-RULES 
    '((PREFER-WELD-TO-BOLT
	(lhs (and (current-node <n>)
		  (current-goal <n> (joined <a> <b> <orient>))
		  (candidate-op <n> BOLT)
		  (candidate-op <n> WELD)))
        (priority 1)
	(rhs (prefer operator WELD BOLT)))))

(setq SCR-BINDINGS-PREFERENCE-RULES nil)
