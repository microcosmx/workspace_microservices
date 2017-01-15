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



(setq *OPERATORS* '(

(GOTO-BOX
 (params (<box> <room>))
 (preconds (and
	    (is-type <box> object) ;[6]
	    (in-room <box> <room>) ;[5]
	    (in-room robot <room>) ;[5]
	    ))
 (effects ((if (at robot <1> <2>)(del (at robot <1> <2>)))
	   (if (next-to robot <3>)(del (next-to robot <3>)))
	   (add (next-to robot <box>)))))

(GOTO-DOOR
 (params (<door> <roomx>))
 (preconds (and
	    (is-type <door> door) ;[6]
	    (connects <door> <roomx> <roomy>) ;[6]
	    (in-room robot <roomx>) ;[5]
	    ))
 (effects 
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (add (next-to robot <door>)))))
    
(GOTO-LOC
 (params (<x> <y> <roomx>))
 (preconds (and
	    (loc-in-room <x> <y> <roomx>) ;[6]
	    (in-room robot <roomx>) ;[5]
	    ))
 (effects 
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (add (at robot <x> <y>)))))
          
(PUSH-BOX
 (params (<boxx> <boxy>))
 (preconds (and
	    (is-type <boxy> object) ;[6]
	    (pushable <boxx>) ;[6]
	    (in-room <boxx> <roomx>) ;[5]
	    (in-room <boxy> <roomx>) ;[5]
	    (in-room robot <roomx>) ;[5]
	    (next-to robot <boxx>) ;[1]
	    ))
 (effects
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (if (at <boxx> <4> <5>)(del (at <boxx> <4> <5>)))
     (if (next-to <boxx> <6>)(del (next-to <boxx> <6>)))
     (if (next-to <7> <boxx>)(del (next-to <7> <boxx>)))
     (add (next-to <boxy> <boxx>))     
     (add (next-to <boxx> <boxy>))
     (add (next-to robot <boxx>)))))

(PUSH-TO-DOOR
 (params (<box> <door> <roomx>))
 (preconds (and
	    (connects <door> <roomx> <roomy>) ;[6]
	    (pushable <box>) ;[6]
	    (is-type <door> door) ;[6]
	    (in-room robot <roomx>) ;[5]
	    (in-room <box> <roomx>) ;[5]
	    (next-to robot <box>) ;[1]
	    ))
 (effects     
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (if (at <box> <4> <5>)(del (at <box> <4> <5>)))
     (if (next-to <box> <6>)(del (next-to <box> <6>)))
     (if (next-to <7> <box>)(del (next-to <7> <box>)))
     (add (next-to <box> <door>))
     (add (next-to robot <box>)))))


(PUSH-TO-LOC
 (params (<box> <x> <y>))
 (preconds (and
	    (pushable <box>) ;[6]
	    (loc-in-room <x> <y> <roomx>) ;[6]
	    (in-room robot <roomx>) ;[5]
	    (in-room <box> <roomx>) ;[5]
	    (next-to robot <box>) ;[1]
	    ))
 (effects
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (if (at <box> <4> <5>)(del (at <box> <4> <5>)))
     (if (next-to <box> <6>)(del (next-to <box> <6>)))
     (if (next-to <7> <box>)(del (next-to <7> <box>)))
     (add (at <box> <x> <y>))
     (add (next-to robot <box>)))))

(GO-THRU-DOOR
 (params (<door> <roomy> <roomx>))
 (preconds (and
	    (connects <door> <roomx> <roomy>) ;[6]
	    (is-type <door> door) ;[6]
	    (is-type <roomx> room) ;[6]
	    (in-room robot <roomy>) ;[5]
	    (statis <door> open) ;[2]
	    (next-to robot <door>) ;[1]
	    ))
 (effects    
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (if (in-room robot <roomy>)(del (in-room robot <roomy>)))
     (add (in-room robot <roomx>)))))

(PUSH-THRU-DOOR
 (params (<box> <door> <roomy> <roomx>))
 (preconds (and
	    (connects <door> <roomy> <roomx>) ;[6]
	    (pushable <box>) ;[6]
	    (is-type <door> door) ;[6]
	    (is-type <roomx> room) ;[6]
	    (in-room robot <roomy>) ;[5]
	    (in-room <box> <roomy>) ;[5]
	    (statis <door> open) ;[2]
	    (next-to <box> <door>) ;[1]
	    (next-to robot <box>) ;[1]
	    ))
 (effects
    ((if (at robot <1> <2>)(del (at robot <1> <2>)))
     (if (next-to robot <3>)(del (next-to robot <3>)))
     (if (at <box> <4> <5>)(del (at <box> <4> <5>)))
     (if (next-to <box> <6>)(del (next-to <box> <6>)))
     (if (next-to <7> <box>)(del (next-to <7> <box>)))
     (if (in-room robot <roomy>)(del (in-room robot <roomy>)))
     (if (in-room <box> <roomy>)(del (in-room <box> <roomy>)))
     (add (in-room robot <roomx>))
     (add (in-room <box> <roomx>))
     (add (next-to robot <box>)))))

(OPEN-DOOR 
 (params (<door>))
 (preconds (and
	    (is-type <door> door) ;[6]
	    (next-to robot <door>) ;[5]
	    (statis <door> closed) ;[5]
	    ))
 (effects 
    ((if (statis <door> closed)(del (statis <door> closed)))
     (add (statis <door> open)))))

(CLOSE-DOOR
 (params (<door>))
 (preconds (and
	    (is-type <door> door) ;[6]
	    (next-to robot <door>) ;[5]
	    (statis <door> open))) ;[5]
 (effects 
    ((if (statis <door> open)(del (statis <door> open)))
     (add (statis <door> closed)))))
    	  
          
		 ))

(setq *INFERENCE-RULES* '(

;(CONNECTS
; (params (<door> <roomy> <roomx>))
; (preconds (or (connect <door> <roomy> <roomx>)
;	       (connect <door> <roomx> <roomy>)))
; (effects ((add (connects <door> <roomy> <roomx>)))))

))
