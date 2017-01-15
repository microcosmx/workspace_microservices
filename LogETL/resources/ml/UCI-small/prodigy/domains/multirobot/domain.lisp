#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Alicia Perez

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



;;;
;;; This is an unstable version; new control rules are being tested and added,
;;; and the operators can also change.
;;;
;;; If you have any suggestion or question, please send mail to aperez@cs
;;;

;; Pushable means that the object can be pushed, independently of the number 
;; of robots needed to push it

;; maximum weight that a robot alone can move: 100

(setq *OPERATORS* '(

(GOTO-OBJECT
 (params (<robot> <object> <room>))
 (preconds (and
	    (is-type <robot> robot)
	    (or (is-type <object> block)
		(is-type <object> box)  
		(is-type <object> key))
	    (in-room <object> <room>)
	    (in-room <robot> <room>)
	    ))
 (effects ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
	   (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
	   (add (next-to <robot> <object>)))))

(GOTO-DOOR
 (params (<robot> <door> <roomx>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <door> door) 
	    (connects <door> <roomx> <roomy>) 
	    (in-room <robot> <roomx>)
	    ))
 (effects 
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (add (next-to <robot> <door>)))))
    
(GOTO-LOC
 (params (<robot> <x> <y> <roomx>))
 (preconds (and
	    (is-type <robot> robot)
	    (loc-in-room <x> <y> <roomx>) 
	    (in-room <robot> <roomx>)
	    ))
 (effects 
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (add (at <robot> <x> <y>)))))
          

(PUSH-OBJECT        ;just one robot
 (params (<robot> <objectx> <objecty>))
 (preconds (and
	    (is-type <robot> robot)
	    (or (is-type <objecty> block)
		(is-type <objecty> box)) 
	    (pushable <objectx>) 
	    (weight <objectx> <weight>)
	    (less-than <weight> 100)
	    (in-room <objectx> <roomx>)
	    (in-room <objecty> <roomx>)
	    (in-room <robot> <roomx>)
	    (next-to <robot> <objectx>)
	    ))
 (effects
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (if (at <objectx> <4> <5>)(del (at <objectx> <4> <5>)))
     (if (next-to <objectx> <6>)(del (next-to <objectx> <6>)))
     (if (next-to <7> <objectx>)(del (next-to <7> <objectx>)))
     (add (next-to <objecty> <objectx>))     
     (add (next-to <objectx> <objecty>))
     (add (next-to <robot> <objectx>))
     (add (next-to <robot> <objecty>)))))

(T-PUSH-OBJECT        ;two robots
 (params (<robot1> <robot2> <objectx> <objecty>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (~ (equal-p <robot1> <robot2>))
	    (or (is-type <objecty> block)
		(is-type <objecty> box)) 
	    (pushable <objectx>) 
	    (in-room <objectx> <roomx>)
	    (in-room <objecty> <roomx>)
	    (in-room <robot1> <roomx>)
	    (in-room <robot2> <roomx>)
	    (next-to <robot1> <objectx>)
	    (next-to <robot2> <objectx>)
	    ))
 (effects
    ((if (at <robot1> <11> <12>)(del (at <robot1> <11> <12>)))
     (if (at <robot2> <21> <22>)(del (at <robot2> <21> <22>)))
     (if (next-to <robot1> <13>)(del (next-to <robot1> <13>)))
     (if (next-to <robot2> <23>)(del (next-to <robot2> <23>)))
     (if (at <objectx> <4> <5>)(del (at <objectx> <4> <5>)))
     (if (next-to <objectx> <6>)(del (next-to <objectx> <6>)))
     (if (next-to <7> <objectx>)(del (next-to <7> <objectx>)))
     (add (next-to <objecty> <objectx>))     
     (add (next-to <objectx> <objecty>))
     (add (next-to <robot1> <objectx>))
     (add (next-to <robot2> <objectx>))
     (add (next-to <robot1> <objecty>))
     (add (next-to <robot2> <objecty>)))))


(PUSH-TO-DOOR    ;just one robot
 (params (<robot> <object> <door> <roomx>))
 (preconds (and
	    (is-type <robot> robot)
	    (connects <door> <roomx> <roomy>) 
	    (pushable <object>) 
	    (weight <object> <weight>)
	    (less-than <weight> 100)
	    (is-type <door> door) 
	    (in-room <object> <roomx>)
	    (in-room <robot> <roomx>)
	    (next-to <robot> <object>)
	    ))
 (effects     
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (add (next-to <object> <door>))
     (add (next-to <robot> <object>)))))


(T-PUSH-TO-DOOR    ;two robots
 (params (<robot1> <robot2> <object> <door> <roomx>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (~ (equal-p <robot1> <robot2>))
	    (connects <door> <roomx> <roomy>) 
	    (pushable <object>) 
	    (is-type <door> door) 
	    (in-room <object> <roomx>)
	    (in-room <robot1> <roomx>)
	    (in-room <robot2> <roomx>)
	    (next-to <robot1> <object>)
	    (next-to <robot2> <object>)
	    ))
 (effects     
    ((if (at <robot1> <11> <12>)(del (at <robot1> <11> <12>)))
     (if (at <robot2> <21> <22>)(del (at <robot2> <21> <22>)))
     (if (next-to <robot1> <13>)(del (next-to <robot1> <13>)))
     (if (next-to <robot2> <23>)(del (next-to <robot2> <23>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (add (next-to <object> <door>))
     (add (next-to <robot1> <object>))
     (add (next-to <robot2> <object>)))))


(PUSH-TO-LOC     ;just one robot
 (params (<robot> <object> <x> <y>))
 (preconds (and
	    (is-type <robot> robot)
	    (pushable <object>) 
	    (weight <object> <weight>)
	    (less-than <weight> 100)
	    (loc-in-room <x> <y> <roomx>) 
	    (in-room <object> <roomx>)
	    (in-room <robot> <roomx>)
	    (next-to <robot> <object>)
	    ))
 (effects
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (add (at <object> <x> <y>))
     (add (next-to <robot> <object>)))))


(T-PUSH-TO-LOC     ;two robots
 (params (<robot1> <robot2> <object> <x> <y>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (~ (equal-p <robot1> <robot2>))
	    (pushable <object>) 
	    (loc-in-room <x> <y> <roomx>) 
	    (in-room <object> <roomx>)
	    (in-room <robot1> <roomx>)
	    (in-room <robot2> <roomx>)
	    (next-to <robot1> <object>)
	    (next-to <robot2> <object>)
	    ))
 (effects
    ((if (at <robot1> <11> <12>)(del (at <robot1> <11> <12>)))
     (if (at <robot2> <21> <22>)(del (at <robot2> <21> <22>)))
     (if (next-to <robot1> <13>)(del (next-to <robot1> <13>)))
     (if (next-to <robot2> <23>)(del (next-to <robot2> <23>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (add (at <object> <x> <y>))
     (add (next-to <robot1> <object>))
     (add (next-to <robot2> <object>)))))


(GO-THRU-DOOR
 (params (<robot> <door> <roomy> <roomx>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <roomx> room) 
	    (is-type <door> door) 
	    (connects <door> <roomx> <roomy>) 
	    (in-room <robot> <roomy>)
	    (statis <door> open)
	    (next-to <robot> <door>)
	    ))
 (effects    
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (if (in-room <robot> <roomy>)(del (in-room <robot> <roomy>)))
     (add (in-room <robot> <roomx>)))))


(PUSH-THRU-DOOR    ;just one robot
 (params (<robot> <object> <door> <roomy> <roomx>))
 (preconds (and
	    (is-type <robot> robot)
	    (pushable <object>) 
	    (weight <object> <weight>)
	    (less-than <weight> 100)
	    (is-type <door> door) 
	    (is-type <roomx> room) 
	    (connects <door> <roomy> <roomx>) ;;CAMBIADO
	    (in-room <object> <roomy>)
	    (in-room <robot> <roomy>)
	    (statis <door> open)
	    (next-to <object> <door>)
	    (next-to <robot> <object>)
	    ))
 (effects
    ((if (at <robot> <1> <2>)(del (at <robot> <1> <2>)))
     (if (next-to <robot> <3>)(del (next-to <robot> <3>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (if (in-room <robot> <roomy>)(del (in-room <robot> <roomy>)))
     (if (in-room <object> <roomy>)(del (in-room <object> <roomy>)))
     (add (in-room <robot> <roomx>))
     (add (in-room <object> <roomx>))
     (add (next-to <robot> <object>)))))


(T-PUSH-THRU-DOOR    ;two robots
 (params (<robot1> <robot2> <object> <door> <roomy> <roomx>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (~ (equal-p <robot1> <robot2>))
	    (pushable <object>) 
	    (in-room <object> <roomy>)
	    (in-room <robot1> <roomy>)
	    (in-room <robot2> <roomy>)
	    (is-type <door> door) 
	    (is-type <roomx> room) 
	    (connects <door> <roomy> <roomx>) 
	    (statis <door> open)  
	    (next-to <object> <door>)
	    (next-to <robot1> <object>)
	    (next-to <robot2> <object>)
	    ))
 (effects
    ((if (at <robot1> <11> <12>)(del (at <robot1> <11> <12>)))
     (if (at <robot2> <21> <22>)(del (at <robot2> <11> <22>)))
     (if (next-to <robot1> <13>)(del (next-to <robot1> <13>)))
     (if (next-to <robot2> <23>)(del (next-to <robot2> <23>)))
     (if (at <object> <4> <5>)(del (at <object> <4> <5>)))
     (if (next-to <object> <6>)(del (next-to <object> <6>)))
     (if (next-to <7> <object>)(del (next-to <7> <object>)))
     (if (in-room <robot1> <roomy>)(del (in-room <robot1> <roomy>)))
     (if (in-room <robot2> <roomy>)(del (in-room <robot2> <roomy>)))
     (if (in-room <object> <roomy>)(del (in-room <object> <roomy>)))
     (add (in-room <robot1> <roomx>))
     (add (in-room <robot2> <roomx>))
     (add (in-room <object> <roomx>))
     (add (next-to <robot1> <object>))
     (add (next-to <robot2> <object>)))))


(PUT-BLOCK-INTO-BOX        ;just one robot
 (params (<robot> <block> <box>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <block> block)
	    (is-type <box> box) 
	    (pushable <block>)  ; to avoid moving unmovable blocks
	    (weight <block> <blockweight>)
	    (less-than <blockweight> 100)
	    (forall (<other-box>) (is-type <other-box> box)
		 (~ (in <other-box> <block>)))
	    (in-room <box> <roomx>)
	    (in-room <block> <roomx>)
	    (next-to <box> <block>)
	    (in-room <robot> <roomx>)
	    (next-to <robot> <box>)
	    (next-to <robot> <block>) ;maybe one of these could be ommited
	    (weight <box> <boxweight>)
	    (is-sum <boxweight> <blockweight> <totalweight>)
 	    ))
 (effects
    ((if (at <block> <4> <5>)(del (at <block> <4> <5>))) 
     (if (at <box> <1> <2>)(add (at <block> <1> <2>)))
     (add (in <box> <block>))
     (del (weight <box> <boxweight>))
     (add (weight <box> <totalweight>)))))

      
(T-PUT-BLOCK-INTO-BOX        ;just one robot
 (params (<robot1> <robot2> <block> <box>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (is-type <block> block)
	    (~ (equal-p <robot1> <robot2>))
	    (is-type <box> box) 
	    (pushable <block>); to avoid moving unmovable blocks
	    (forall (<other-box>) (is-type <other-box> box)
		 (~ (in <other-box> <block>)))
	    (in-room <box> <roomx>)
	    (in-room <block> <roomx>)
	    (next-to <box> <block>)
	    (in-room <robot1> <roomx>)
	    (in-room <robot2> <roomx>)
	    (next-to <robot1> <box>)
	    (next-to <robot1> <block>) 
	    (next-to <robot2> <box>)
	    (next-to <robot2> <block>) 
	    (weight <box> <boxweight>)
	    (weight <block> <blockweight>)
	    (is-sum <boxweight> <blockweight> <totalweight>)
 	    ))
 (effects
    ((if (at <block> <4> <5>)(del (at <block> <4> <5>)))
     (if (at <box> <1> <2>)(add (at <block> <1> <2>)))
     (add (in <box> <block>))
     (del (weight <box> <boxweight>))
     (add (weight <box> <totalweight>)))))


(TAKE-BLOCK-OUT-OF-BOX        ;just one robot
 (params (<robot> <block> <box>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <block> block)
	    (is-type <box> box) 
	    (pushable <block>)  ; to avoid moving unmovable blocks
	    (weight <block> <blockweight>)
	    (less-than <blockweight> 100)
            (in <box> <block>)
            (in-room <box> <room>)
	    (in-room <robot> <room>)
	    (next-to <robot> <box>)
	    (weight <box> <boxweight>)
	    (is-sum <newboxweight> <blockweight> <boxweight>)
 	    ))
 (effects
    ((if (at <block> <4> <5>)(del (at <block> <4> <5>)))
     (add (next-to <robot> <block>))
     (add (next-to <box> <block>))  
     (add (next-to <block> <box>))
     (del (in <box> <block>))
     (del (weight <box> <boxweight>))
     (add (weight <box> <newboxweight>)))))
      

(T-TAKE-BLOCK-OUT-OF-BOX        ;two robots
 (params (<robot1> <robot2> <block> <box>))
 (preconds (and
	    (is-type <robot1> robot)
	    (is-type <robot2> robot)
	    (is-type <block> block)
	    (~ (equal-p <robot1> <robot2>))
	    (is-type <box> box) 
	    (pushable <block>)  ; to avoid moving unmovable blocks
	    (in <box> <block>)
	    (in-room <box> <room>)
	    (in-room <block> <room>)
	    (in-room <robot1> <room>)
	    (in-room <robot2> <room>)
	    (next-to <robot1> <box>)
	    (next-to <robot2> <box>)  
	    (weight <box> <boxweight>)
	    (weight <block> <blockweight>)
	    (is-sum <newboxweight> <blockweight> <boxweight>)
 	    ))
 (effects
    ((if (at <block> <4> <5>)(del (at <block> <4> <5>))) 
     (add (next-to <robot1> <block>)) 
     (add (next-to <robot2> <block>)) 
     (add (next-to <box> <block>))   
     (add (next-to <block> <box>))   
     (del (in <box> <block>))
     (del (weight <box> <boxweight>))
     (add (weight <box> <newboxweight>)))))
      

(OPEN-DOOR     
 (params (<robot> <door>))
 (preconds (and
	    (is-type <door> door) 
	    (statis <door> closed)
	    (is-type <robot> robot)
	    (next-to <robot> <door>)
	    ))
 (effects 
    ((del (statis <door> closed))
     (add (statis <door> open)))))


(CLOSE-DOOR
 (params (<robot> <door>))
 (preconds (and
	    (is-type <door> door) 
	    (statis <door> open)
	    (is-type <robot> robot)
	    (next-to <robot> <door>)))
 (effects 
    ((del (statis <door> open))
     (add (statis <door> closed)))))

(UNLOCK-DOOR
 (params (<robot> <door> <key>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <door> door) 
	    (statis <door> locked)
	    (is-type <key> key) ;if the robot could carry other objects
	    (is-key <door> <key>)
	    (holding <robot> <key>)
	    (next-to <robot> <door>)
	    ))
 (effects 
    ((del (statis <door> locked))
     (add (statis <door> closed)))))

(LOCK-DOOR
 (params (<robot> <door> <key>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <door> door)  
	    (is-type <key> key) ;if the robot could carry other objects
	    (is-key <door> <key>)
	    (holding <robot> <key>)
	    (next-to <robot> <door>)
	    (statis <door> closed) 
	    ))
 (effects
    ((del (statis <door> closed))
     (add (statis <door> locked)))))

(PICK-UP-KEY
 (params (<robot> <key>))
 (preconds (and
	    (is-type <robot> robot)
	    (is-type <key> key) 
	    (next-to <robot> <key>) 
	    ))	    
 (effects 
    ((add (holding <robot> <key>)))))
	
		 )))


(setq *INFERENCE-RULES* '(

 (CONNECTS1
  (params (<door> <roomy> <roomx>))
  (preconds 
;   (or 					
    (connect <door> <roomy> <roomx>)
;    (connect <door> <roomx> <roomy>)		
;    )						
    )
  (effects ((add (connects <door> <roomy> <roomx>))
            (add (connects <door> <roomx> <roomy>)))))

			  ))


 

