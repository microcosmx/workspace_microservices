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
	            (list-of-candidate-goals <node> <goals>)
	            (is-first-goal <goal> <goals>)))
           (rhs (select goal <goal>)))
       ))
(setq SCR-OP-SELECT-RULES 
  '(
	(MINIMUM-EFFORT-GOTO
         (lhs (and (current-node <node>)
		   (current-goal <node> (next-to robot <obj>))
		   (candidate-op <node> GOTO-OBJ)
		   (known <node> (~ (is-door <obj>)))))
         (rhs (select operator GOTO-OBJ)))

	(DO-GOTO-DR
         (lhs (and (current-node <node>)
		   (current-goal <node> (next-to robot <obj>))
		   (known <node> (is-door <obj>))
		   (candidate-op <node> GOTO-DR)))
         (rhs (select operator GOTO-DR)))

   ))

(setq SCR-BINDINGS-SELECT-RULES  nil)
     
(setq SCR-NODE-REJECT-RULES nil)
(setq SCR-GOAL-REJECT-RULES 
      '(
	
	(LOCK-AFTER-INROOM
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (locked <d>))
                   (candidate-goal <node> (inroom <x> <r>))
		   (known <node> (dr-to-rm <d> <r>))))
         (rhs (reject goal (locked <d>))))


	(ROBOT-INROOM-LAST
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (inroom <x> <r>))
                   (candidate-goal <node> (inroom robot <r2>))
                   (not-equal <x> robot)))
         (rhs (reject goal (inroom robot <r2>))))

	(CLOSE-DOORS-AFTER-INROOM-OBJ
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (closed <door>))
                   (candidate-goal <node> (inroom <obj> <room>))
                   (not-equal <obj> robot)))
         (rhs (reject goal (closed <door>))))


	(CLOSE-DOORS-AFTER-INROOM
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (closed <door>))
                   (candidate-goal <node> (inroom robot <room>))
		   (known <node> (dr-to-rm <door> <room>))))
         (rhs (reject goal (closed <door>))))

	(INROOM-OVER-NEXT-TO-1
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (inroom <x> <r>))
                   (candidate-goal <node> (next-to <x> <y>))))
         (rhs (reject goal (next-to <x> <y>))))

	(INROOM-OVER-NEXT-TO-2
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (inroom <x> <r>))
                   (candidate-goal <node> (next-to <y> <x>))))
         (rhs (reject goal (next-to <y> <x>))))

       ))


(setq SCR-OP-REJECT-RULES 
       '(

	(PUSH-OBJ-TOBE-NEXTTO
         (lhs (and (current-node <node>)
		   (current-goal <node> (next-to <obj1> <obj2>))
		   (candidate-op <node> PUSH-TO-DR)
		   (known <node> (~ (is-door <obj2>)))))
         (rhs (reject operator PUSH-TO-DR)))

	    (CARRY-IF-CARRIABLE
             (lhs (and (current-node <node>)
                       (current-goal <node> (next-to <obj1> <obj2>))
   		       (candidate-op <node>  PUTDOWN-NEXT-TO)
                       (known <node> (~ (carriable <obj2>)))
                       (known <node> (~ (carriable <obj1>)))))
             (rhs (reject operator PUTDOWN-NEXT-TO)))

	    (PUSH-IF-PUSHABLE
             (lhs (and (current-node <node>)
                       (current-goal <node> (next-to <obj1> <obj2>))
   		       (candidate-op <node>  PUSH-BOX)
                       (known <node> (~ (pushable <obj2>)))
                       (known <node> (~ (pushable <obj1>)))))
             (rhs (reject operator PUSH-BOX)))

	    (PUSH-IF-PUSHABLE-THRU-DOOR
             (lhs (and (current-node <node>)
                       (current-goal <node> (inroom <obj> <rm>))
   		       (candidate-op <node>  PUSH-THRU-DR)
                       (known <node> (~ (pushable <obj>)))))
             (rhs (reject operator PUSH-THRU-DR)))

	    (CARRY-IF-CARRIABLE-THRU-DOOR
             (lhs (and (current-node <node>)
                       (current-goal <node> (inroom <obj> <rm>))
   		       (candidate-op <node>  CARRY-THRU-DR)
                       (known <node> (~ (carriable <obj>)))))
             (rhs (reject operator CARRY-THRU-DR)))
                   

	    (ONLY-PUSH-BOX
	     (lhs (and (current-node <node>)
		       (current-goal <node> (next-to robot <x>))
		       (candidate-op <node> PUSH-BOX)))
	     (rhs (reject operator PUSH-BOX)))


	 (ONLY-CARRY-BOX-THRU-DOOR
	     (lhs (and (current-node <node>)
		       (current-goal <node> (inroom robot <x>))
		       (candidate-op <node> CARRY-THRU-DR)))
	     (rhs (reject operator CARRY-THRU-DR)))

	 (ONLY-PUSH-BOX-THRU-DOOR1
	     (lhs (and (current-node <node>)
		       (current-goal <node> (inroom robot <x>))
		       (candidate-op <node> PUSH-THRU-DR)))
	     (rhs (reject operator PUSH-THRU-DR)))

	 (ONLY-PUSH-BOX-THRU-DOOR2
	     (lhs (and (current-node <node>)
		       (current-goal <node> (next-to <y> <x>))
		       (candidate-op <node> PUSH-THRU-DR)))
	     (rhs (reject operator PUSH-THRU-DR)))

	 (ONLY-PUTDOWN-NEXT-TO-1
	     (lhs (and (current-node <node>)
		       (current-goal <node> (arm-empty))
		       (candidate-op <node> PUTDOWN-NEXT-TO)))
	     (rhs (reject operator PUTDOWN-NEXT-TO)))

	 (ONLY-PUTDOWN-NEXT-TO-2
	     (lhs (and (current-node <node>)
		       (current-goal <node> (inroom <xx> <yy>))
		       (candidate-op <node> PUTDOWN-NEXT-TO)))
	     (rhs (reject operator PUTDOWN-NEXT-TO)))
	))

		       



		       
(setq SCR-BINDINGS-REJECT-RULES 
  '(	

	  (DONT-PUTDOWN-NEXT-TO-IF-NOT-CARRIABLE
           (lhs (and (current-node <node>)		
		     (current-op <node> PUTDOWN-NEXT-TO)
		     (candidate-bindings <node> (<a> <b> <r>))
		     (known <node> (~ (carriable <a>)))))
	   (rhs (reject bindings (<a> <b> <r>))))

	  (DONT-PUSH-TO-IF-NOT-CARRIABLE
           (lhs (and (current-node <node>)		
		     (current-op <node> PUSH-BOX)
		     (candidate-bindings <node> (<a> <b> <r>))
		     (known <node> (~ (pushable <a>)))))
	   (rhs (reject bindings (<a> <b> <r>))))

	  (UNLOCK-FROM-RM-YOUR-IN
           (lhs (and (current-node <node>)		
		     (current-op <node> UNLOCK)
		     (candidate-bindings <node> (<d> <k> <r>))
		     (is-bound <r>)
		     (known <node> (inroom robot <r2>))
		     (not-equal <r> <r2>)
		     (known <node> (dr-to-rm <d> <r2>))))
	   (rhs (reject bindings (<d> <k> <r>))))

	  (PUSH-OTHER-BOX
           (lhs (and (current-node <node>)		
		     (current-op <node> PUSH-BOX)
		     (candidate-bindings <node> (<a> <b> <r>))
		     (in-goal-exp <node> (inroom <a> <r2>))
		     (known <node> (inroom <b> <r3>))
		     (not-equal <r3> <r2>)))
	   (rhs (reject bindings (<a> <b> <r>))))

	  (MOVE-OTHER-BOX
           (lhs (and (current-node <node>)		
		     (current-op <node> PUTDOWN-NEXT-TO)
		     (candidate-bindings <node> (<a> <b> <r>))
		     (in-goal-exp <node> (inroom <a> <r2>))
		     (known <node> (inroom <b> <r3>))
		     (not-equal <r3> <r2>)))
	   (rhs (reject bindings (<a> <b> <r>))))

   ))


(setq SCR-NODE-PREFERENCE-RULES nil)
(setq SCR-GOAL-PREFERENCE-RULES 
 '((HOLDING-LAST
         (lhs (and (current-node <node>)
                   (candidate-goal <node> <g1>)
                   (is-equal <g1> (holding <x>))
                   (candidate-goal <node> <g2>)
                   (not-equal <g1> <g2>)))
	 (priority 1)
         (rhs (prefer goal <g2> (holding <x>))))


    (CLOSE-LATE
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (closed <d>))
                   (candidate-goal <node> <g2>)
                   (or (is-equal <g2> (open <x>))
		       (and (is-equal <g2> (inroom <y> <r>))
			    (not-equal <y> robot))
			(is-equal <g2> (next-to <w> <z>)))))
	 (priority 1)
         (rhs (prefer goal <g2> (closed <d>))))

    (LOCK-LATE
         (lhs (and (current-node <node>)
                   (candidate-goal <node> (locked <d>))
                   (candidate-goal <node> <g2>)
                   (or (is-equal <g2> (open <x>))
		       (and (is-equal <g2> (inroom <y> <r>))
			    (not-equal <y> robot))
			(is-equal <g2> (next-to <w> <z>)))))
	 (priority 1)
         (rhs (prefer goal <g2> (locked <d>))))
  ))



(setq SCR-OP-PREFERENCE-RULES nil)
(setq SCR-BINDINGS-PREFERENCE-RULES
  '( (LOCK-FROM-RM-YOUR-IN
           (lhs (and (current-node <node>)		
		     (current-op <node> LOCK)
		     (candidate-bindings <node> (<d> <k> <r>))
		     (candidate-bindings <node> (<d2> <k2> <r2>))
		     (is-bound <r>)
   		     (is-bound <r2>)
		     (not-equal <r> <r2>)
		     (known <node> (inroom robot <r2>))
		     (known <node> (dr-to-rm <d> <r2>))))
	   (priority 1)
	   (rhs (prefer bindings (<d2> <k2> <r2>)(<d> <k> <r>))))

	  (PUSH-FROM-SAME-ROOM
           (lhs (and (current-node <node>)		
		     (current-op <node> PUSH-TO-DR)
		     (candidate-bindings <node> (<b> <d> <r>))
		     (candidate-bindings <node> (<b> <d> <r2>))
		     (is-bound <r>)
		     (is-bound <r2>)
		     (known <node> (inroom <b> <r2>))
		     (known <node> (~ (inroom <b> <r>)))))
	   (priority 1)
	   (rhs (prefer bindings (<b> <d> <r2>) (<b> <d> <r>))))

	(CARRY-FROM-NEXT-ROOM
           (lhs (and (current-node <node>)		
		     (current-op <node> CARRY-THRU-DR)
		     (candidate-bindings <node> (<ba> <da> <r1a> <r2a>))
		     (candidate-bindings <node> (<bb> <db> <r1b> <r2b>))
		     (is-bound <da>)
		     (is-bound <db>)
		     (not-equal <da> <db>)
		     (known <node> (inroom robot <r>))
		     (known <node> (dr-to-rm <da> <r>))))
	   (priority 1)
	   (rhs (prefer bindings (<ba> <da> <r1a> <r2a>)
				(<bb> <db> <r1b> <r2b>))))


	(PUSH-FROM-NEXT-ROOM
           (lhs (and (current-node <node>)		
		     (current-op <node> PUSH-THRU-DR)
		     (candidate-bindings <node> (<ba> <da> <r1a> <r2a>))
		     (candidate-bindings <node> (<bb> <db> <r1b> <r2b>))
		     (is-bound <da>)
		     (is-bound <db>)
		     (not-equal <da> <db>)
		     (known <node> (inroom robot <r>))
		     (known <node> (dr-to-rm <da> <r>))))
	   (priority 1)
	   (rhs (prefer bindings (<ba> <da> <r1a> <r2a>)
				(<bb> <db> <r1b> <r2b>))))
 
	(GO-FROM-NEXT-ROOM
           (lhs (and (current-node <node>)		
		     (current-op <node> GO-THRU-DR)
		     (candidate-bindings <node> (<da> <r1a> <r2a>))
		     (candidate-bindings <node> (<db> <r1b> <r2b>))
		     (is-bound <da>)
		     (is-bound <db>)
		     (not-equal <da> <db>)
		     (known <node> (inroom robot <r>))
		     (known <node> (dr-to-rm <da> <r>))))
	   (priority 1)
	   (rhs (prefer bindings (<da> <r1a> <r2a>)
				(<db> <r1b> <r2b>))))

;	(GO-FROM-NEXT-ROOM
;           (lhs (and (current-node <node>)		
;;		     (current-op <node> GOTO-DR)
;		     (candidate-bindings <node> (<d> <ra>))
;		     (candidate-bindings <node> (<d> <rb>))
;		     (is-bound <ra>)
;		     (is-bound <rb>)
;		     (not-equal <ra> <rb>)
;		     (known <node> (inroom robot <ra>))))
;	   (priority 1)
;	   (rhs (prefer bindings (<d> <ra>)
;				(<d> <rb>))))
; 
;	(PUSH-FROM-NEXT-ROOM
;           (lhs (and (current-node <node>)		
;		     (current-op <node> PUSH-TO-DR)
;		     (candidate-bindings <node> (<b> <d> <ra>))
;		     (candidate-bindings <node> (<b> <d> <rb>))
;		     (is-bound <ra>)
;		     (is-bound <rb>)
;		     (not-equal <ra> <rb>)
;		     (known <node> (inroom robot <ra>))))
;	   (priority 1)
;	   (rhs (prefer bindings (<b> <d> <ra>)
;				(<b> <d> <rb>))))

	  ))
