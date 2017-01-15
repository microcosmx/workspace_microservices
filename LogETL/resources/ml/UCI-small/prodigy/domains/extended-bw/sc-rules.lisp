#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Robert Joseph

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
      '(
        (SELECT-FIRST-GOAL
          (lhs (and (current-node <node>)
                    (not-top-level-node <node>)
                    (primary-candidate-goal <node> <goal>)))
           (rhs (select goal <goal>)))
        ))

(setq *SCR-OP-SELECT-RULES* nil)
(setq *SCR-BINDINGS-SELECT-RULES* 
      '(
	(PUSH-OBJECT-FORM-VACANT-1
	 (lhs (and (current-node <node>)
		   (current-goal <node> (vacant-loc <pos2>))
		   (current-op <node> PUSH-OBJECT)
		   (candidate-bindings <node> (<a> <pos1> <pos2> <b>))
		   (known <node>
			 (location <ob> <pos2>)
			 (weight <ob> <wt>)
			 (less-than <wt> 500))))
	 (rhs (select bindings (<ob> <pos1> <pos2> <wt>))))
	(TOPPLE-TO-CLEAR-1
	 (lhs (and (current-node <node>)
		   (current-goal <node> (clear <underob>))
		   (known <node>
			  (location <underob> <pos3>))))
	 (rhs (select bindings (<ob> <underob> <pos3> <pos2>))))
	))
(setq *SCR-NODE-REJECT-RULES* 
      '(					
	(PYRAMID-STACK-RULE-1
           (lhs (and (candidate-node <node>)
                     (candidate-goal <node> (on <obj1> <obj2>))
		     (known <node> (pyramid <obj2>))))
	   (rhs (reject node <node>)))
	(EQUAL-OBJECTS
           (lhs (and (candidate-node <node>)
	             (candidate-goal <node> (on <obj1> <obj1>))))
	   (rhs (reject node <node>)))
	(CAN-NOT-MOVE-OBJECT-RULE-1
           (lhs (and (candidate-node <node>)
		     (candidate-goal <node> (vacant-loc <loc1>))
		     (known <node> 
			   (and (location <ob> <loc1>)
				(weight <ob> <wt>)
				(~ (less-than <wt> 500))))))
	   (rhs (reject node <node>)))
	))
		     
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES*
      '(
	(CAN-PUSH-ON-OCCUPIED-SPACE-1
           (lhs (and (current-node <node>)
		     (current-goal <node> (location <ob1> <loc1>))
		     (candidate-op <node> PUSH-OBJECT)
;		     (candidate-bindings <node> (<ob1> <loc1> <loc2> <w>))
		     (known <node> 
			   (and (location <ob2> <loc1>)
				(weight <ob2> <wt>)
				(~ (less-than <wt> 500))))))
	   (rhs (reject op PUSH-OBJECT)))
	(CANT-TOPPLE-UNLESS-ADJACENT-1
	   (lhs (and (current-node <node>)
		     (current-goal <node> (location <ob1> <loc1>))
		     (candidate-op <node> TOPPLE)
		     (known <node>
			    (and (location <ob1> <loc2>)
			         (~(adjacent-loc <loc1> <loc2>))))))
	   (rhs (reject op TOPPLE)))
	))

(setq *SCR-BINDINGS-REJECT-RULES* nil)
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* 
    '(
      (MOVE-CLEARED-OBJECT-FIRST
	  (lhs (and (current-node <node>)
		    (candidate-goal <node> (location <ob1> <loc1>))
		    (known <node>
			  (clear <ob1>))))
	  (rhs (prefer goal (location <ob1> <loc1>) <x>)))
      (CHOOSE-STACK-INORDER-1
	  (lhs (and (current-node <node>)
	            (candidate-goal <node> (on <x> <y>))
		    (candidate-goal <node> (on <y> <z>))))
	  (rhs (prefer goal (on <y> <z>) (on <x> <y>))))
     ))
(setq *SCR-OP-PREFERENCE-RULES* 
      '(
	(UNSTACK-VS-TOPPLE-1
	   (lhs (and (current-node <node>)
		     (current-goal <node> (clear <ob>))
		     (candidate-op <node> TOPPLE)
		     (candidate-op <node> UNSTACK)
		     (known <node>
			    (and (weight <ob> <w>)
 			         (less-than <w> 200)))))
	   (rhs (prefer op UNSTACK TOPPLE)))
	   
;        (PUSH-OBJECT-RULE-1
;	   (lhs (and (current-node <node>)
;		     (current-goal <node> (location <ob> <pos>))
;		     (candidate-op <node> PUSH-OBJECT)
;		     (candidate-op <node> TOPPLE)
;		     (known <node> (on-table <ob>))))
;	   (rhs (prefer operator PUSH-OBJECT TOPPLE)))
;        (PUSH-OBJECT-RULE-2
;	   (lhs (and (current-node <node>)
;		     (current-goal <node> (location <ob> <pos>))
;		     (candidate-op <node> PUSH-OBJECT)
;		     (candidate-op <node> PUT-DOWN)
;		     (known <node> (on-table <ob>))))
;	   (rhs (prefer operator PUSH-OBJECT PUT-DOWN)))
;        (PUSH-OBJECT-RULE-3
;	   (lhs (and (current-node <node>)
;		     (current-goal <node> (location <ob> <pos>))
;		     (candidate-op <node> PUSH-OBJECT)
;		     (candidate-op <node> STACK)
;		     (known <node> (on-table <ob>))))
;	   (rhs (prefer operator PUSH-OBJECT STACK)))
;
	))
(setq *SCR-BINDINGS-PREFERENCE-RULES* 
     '(
;       (PUSH-OBJECT-FORWARD-1
;	   (lhs (and (current-node <node>)
;		     (current-ops <node> PUSH-OBJECT)
;		     (candidate-bindings <node> 
;			 (<ob> <pos-to1> <pos-from> <w>))
;		     (cadidate-bindings <node>
;			 (<ob> <pos-to2> <pos-from> <w>))
;		     (known <node> 
;			    (and (location <ob> <pos2>)
;				 (distance <pos2> <pos-to1> <dist1>)
;				 (distance <pos2> <pos-to2> <dist2>)
;				 (less-than <dist1> <dist2>)))))
;	   (rhs (prefer bindings (<ob> <pos-to1> <pos-from> <w>))))
      ))
