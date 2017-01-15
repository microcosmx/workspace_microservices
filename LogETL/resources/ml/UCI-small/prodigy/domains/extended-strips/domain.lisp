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


; oops - noticed by -- domain allows pushing an
; object through the door while your holding another.
; Screws up putdown...

; modified by and changing types... 
 
(setq *OPERATORS* '(
 
;(GOTO-BOX
; (params (<b> <rm>))
; (preconds 
;     (and (is-box <b>)
;	  (inroom <b> <rm>)
;	  (inroom robot <rm>)))
; (effects ((add (next-to robot <b>))
;           (del (at robot <x> <y>))
;           (if (next-to robot <z>)(del (next-to robot <z>)))))) ; for testing

    
    
(PICKUP-OBJ
 (params (<o1>))
 (preconds
    (and (arm-empty)
	 (next-to robot <o1>)
	 (carriable <o1>)))
 (effects ((del (arm-empty))
	   (del (next-to <o1> <*30>))
	   (del (next-to <*31> <o1>))
	   (add (holding <o1>)))))


(PUTDOWN
 (params (<o2>))
 (preconds 
 	 (holding <o2>))
;           (inroom robot <o2-rm>)
 (effects 
    ((del (holding <*35>))
     (add (next-to robot <o2>))
     (add (arm-empty)))))

(PUTDOWN-NEXT-TO
 (params (<o3> <other-ob> <o3-rm>))
 (preconds 
      (and (holding <o3>)
	   (is-object <other-ob>)
	   (inroom <other-ob> <o3-rm>)
	   (inroom <o3> <o3-rm>)
	   (next-to robot <other-ob>)))
 (effects 
    ((del (holding <*35>))
     (add (next-to <o3> <other-ob>))
     (add (next-to robot <o3>)) ;  ordering important
     (add (next-to <other-ob> <o3>))
     (add (arm-empty)))))
     

          

; Steve, if you add this back in, add appropriate scrs too.
(PUSH-TO-DR
 (params (<b1> <d1> <r1>))
 (preconds
     (and (is-door <d1>)
	  (dr-to-rm <d1> <r1>)
	  (inroom <b1> <r1>)
	  (next-to robot <b1>)
	  (pushable <b1>)))
 (effects     
    ((del (next-to robot <*3>))
     (del (next-to <b1> <5>))
     (del (next-to <*13> <b1>))
     (add (next-to <b1> <d1>))
     (add (next-to robot <b1>)))))

(PUSH-THRU-DR
 (params (<b-x> <d-x> <r-x> <r-y>))
 (preconds 
     (and   
	  (is-room <r-x>)
	  (dr-to-rm <d-x> <r-x>)
	  (is-door <d-x>)
	  (dr-open <d-x>)
	  (next-to <b-x> <d-x>)
	  (next-to robot <b-x>)
	  (pushable <b-x>)
	  (connects <d-x> <r-x> <r-y>)
	  (inroom <b-x> <r-y>)))
 (effects
    (
     (del (next-to robot <*1>))  ;confuses regres-state-diff

				  ; next to one thing at a time, so must
     (del (next-to <b-x> <*12>)) ; be next to door, but complication, box
				; isnt oh well
     (del (next-to <*7> <b-x>))   
     (del (inroom robot <*21>))
     (del (inroom <b-x> <*22>))
     (add (inroom robot <r-x>))
     (add (inroom <b-x> <r-x>))  
     (add (next-to robot <b-x>)))))

(GO-THRU-DR
 (params (<ddx> <rrx> <rry>))
 (preconds 
     (and (arm-empty)
  	  (is-room <rrx>)
	  (dr-to-rm <ddx> <rrx>)
  	  (is-door <ddx>)
	  (dr-open <ddx>)
	  (next-to robot <ddx>)
	  (connects <ddx> <rrx> <rry>)
	  (inroom robot <rry>)))
 (effects    
    (
     (del (next-to robot <*19>)) ; robot must be next to door only
     (del (inroom robot <*20>))
     (add (inroom robot <rrx>)))))

(CARRY-THRU-DR
 (params (<b-zz> <d-zz> <r-zz> <r-ww>))
 (preconds 
     (and   
	  (is-room <r-zz>)
	  (dr-to-rm <d-zz> <r-zz>)
	  (is-door <d-zz>)
	  (dr-open <d-zz>)
	  (is-object <b-zz>)
	  (holding <b-zz>)
	  (connects <d-zz> <r-zz> <r-ww>)
	  (inroom <b-zz> <r-ww>)
	  (inroom robot <r-ww>)
	  (next-to robot <d-zz>)))
 (effects
    (
     (del (next-to robot <*48>))  ;confuses regres-state-diff

     (del (inroom robot <*41>))
     (del (inroom <b-zz> <*42>))
     (add (inroom robot <r-zz>))
     (add (inroom <b-zz> <r-zz>)))))



(GOTO-DR
 (params (<dx> <rx>))
 (preconds 
     (and (is-door <dx>)
	  (dr-to-rm <dx> <rx>)
	  (inroom robot <rx>)))
 (effects 
    ((del (next-to robot <*18>))
     (add (next-to robot <dx>)))))



(PUSH-BOX
 (params (<ba> <bb> <ra>))
 (preconds
     (and (is-object <ba>)
	  (is-object <bb>)
	  (inroom <bb> <ra>)
	  (inroom <ba> <ra>)
	  (pushable <ba>)
 	  (next-to robot <ba>)))
 (effects
    ((del (next-to robot <*14>))
     (del (next-to <ba> <*5>))
     (del (next-to <*6> <ba>))
     (add (next-to robot <ba>))
     (add (next-to robot <bb>))
     (add (next-to <ba> <bb>))
     (add (next-to <bb> <ba>))     
    )))




(GOTO-OBJ
 (params (<b> <rm>))
 (preconds 
     (and 
	  (is-object <b>)
	  (inroom <b> <rm>)
	  (inroom robot <rm>)))
 (effects ((add (next-to robot <b>))
	   (del (next-to robot <*109>)))))


(OPEN 
 (params (<door>))
 (preconds
     (and (is-door <door>)
	  (unlocked <door>)
	  (next-to robot <door>)
	  (dr-closed <door>)))
 (effects 
    ((del (dr-closed <door>))
     (add (dr-open <door>)))))

(CLOSE
 (params (<door1>))
 (preconds
     (and (is-door <door1>)
	  (next-to robot <door1>)
	  (dr-open <door1>)))
 (effects 
    ((del (dr-open <door1>))
     (add (dr-closed <door1>)))))

; should probably kill carriable scr rule

(LOCK
 (params (<door2> <k1> <rm-b>))
 (preconds
     (and (is-door <door2>)
	  (is-key <door2> <k1>)
	  (holding <k1>)
	  (dr-to-rm <door2> <rm-b>)
	  (inroom <k1> <rm-b>)
	  (next-to robot <door2>)
	  (dr-closed <door2>)
	  (unlocked <door2>)))
 (effects 
    ((del (unlocked <door2>))
     (add (locked <door2>)))))


(UNLOCK
 (params (<door3> <k2> <rm-a>))
 (preconds
     (and (is-door <door3>)
	  (is-key <door3> <k2>)
	  (holding <k2>)
	  (dr-to-rm <door3> <rm-a>)
	  (inroom <k2> <rm-a>)
	  (inroom robot <rm-a>)
	  (next-to robot <door3>)
	  (locked <door3>)))
 (effects 
    ((del (locked <door3>))
     (add (unlocked <door3>)))))



    	  
          

		 ))

(setq *INFERENCE-RULES* nil)

