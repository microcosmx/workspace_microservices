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


;  Domain-specific Simplification axioms for blocksworld

  (setq *SINGLE-GENERATORS*
    '((holding nil)      ; The robot can be holding at most one object.
      (is-key t nil)   ; A key opens a single door, e.g. (is-key key1 door1)
      (inroom t nil)   ; An object is an a single room at any time
                       ; e.g. (inroom robot rm1)
      (connects t nil t)   ; A dr connects to a single other room.
      (connects t t nil))) ; A dr connects from a single other room.
                             ; Eg. (conncets door1 room1 room2)



(setq *AT-LEAST-ONE-GENERATORS*
      '((is-object nil)      ; there is more than one object
	(dr-to-rm t nil)  ; every door has at least one room
	(dr-to-rm nil t)  ; every room has a at least one door
	(connects t nil t) ; every room is connect to at least one other room
	(connects t t nil) ; every room is connect from at least one other room
	(inroom t nil)))   ; Every object is in at least one room.

(setq *EVALUABLE-DOMAIN-FNS* nil)

; Most of the following information is necessary to characterize legal
; states (especially legal START states).

(setq *SIMPLIFICATION-RULES* '(

; If you cant carry it, your not holding it

 (rule0				
  (p-exp (~ (holding <r>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (~ (carriable <r>))))

; The robot is not carriable.

 (rule0a				
  (p-exp (~ (carriable robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; The robot is not pushable.

 (rule0b				
  (p-exp (~ (pushable robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; The robot is not holding itself.

 (rule0c				
  (p-exp (~ (holding robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; The robot is not an object

 (rule0d				
  (p-exp (~ (is-object robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; The robot is not a door.

 (rule0e				
  (p-exp (~ (is-door robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; The robot is not next to itself

 (rule0f				
  (p-exp (~ (next-to robot robot)))
  (q-exp t)
  (prove-cond t)
  (known-cond t))

; If something is carriable, then that something isn't equal to the robot.

 (rule1			
  (p-exp (not-equal robot <r1a>))
  (q-exp t)
  (prove-cond t)
  (known-cond (carriable <r1a>)))

; If something is locked, open, closed, or a dr-to-rm, then it must be a door.

 (rule2
  (p-exp (is-door <r2>))
  (q-exp t)
  (prove-cond t)
  (known-cond (or (locked <r2>)
	     (dr-open <r2>)
	     (dr-closed <r2>)
	     (dr-to-rm <r2> <$5>))))

; If a door is locked, then its closed.

 (rule3
  (p-exp (dr-closed <r3>))
  (q-exp t)
  (prove-cond t)
  (known-cond (locked <r3>)))

; If something is not an object, then it's not carriable.

 (rule4a
  (p-exp (~ (carriable <r4>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (~ (is-object <r4>))))

; If something is not an object, then it's not pushable

 (rule4b
  (p-exp (~ (pushable <r5>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (~ (is-object <r5>))))

; If something is not an object, then it's not being held.

 (rule4b
  (p-exp (~ (holding <r6>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (~ (is-object <r6>))))


; If something is in a room, then its not a door.

 (rule5
  (p-exp (~ (is-door <r7>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (inroom <r7> <$8>)))

; If seomthing is a door to a room, then it's not an object.

 (rule6
  (p-exp (~ (is-object <r9>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (dr-to-rm <r9> <$10>)))

  ))



