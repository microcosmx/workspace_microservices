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




(setq *WORLD-X-SIZE* 5 *WORLD-Y-SIZE* 5 *WORLD-Z-SIZE* 5)
(setq *RMG-reach* 2) 


; Operators

(setq *OPERATORS* '(


(PICK-UP
 (params (<ob> <ob-loc> <RMG-loc>))
; (vars (<ob> <ob-loc> <size> <RMG-loc>))
 (preconds
       (exists (<ob> <size>) (object <ob> <size>)
        (exists (<ob-loc>) (at <ob> <ob-loc>)
	 (exists (<RMG-loc>) (next-to <ob-loc> <RMG-loc>)
	    (and (at RMG <RMG-loc>)
	         (within-height <ob-loc> <RMG-loc> *RMG-reach*)
		 (clear <ob>)
	         (arm-empty)
    )))))
 (effects (
   (del (at <ob> <ob-loc>))
   (add (holding <ob>)))))
    

;  Temporarily hacked to use next-to as a generator
;  note: plenty of ordering problems

(PUT-DOWN
 (params (<ob> <new-loc> <RMG-loc>))
;  (vars (<ob> <new-loc> <RMG-loc> <loc-size> <ob-size>))
 (preconds
    (exists (<new-loc>) (vacant-loc <new-loc>)
     (exists (<ob> <ob-size>) (object <ob> <ob-size>)
      (exists (<RMG-loc>) (next-to <new-loc> <RMG-loc>)
       (and 
            (exists (<loc-size>) (location-size <new-loc> <loc-size>)
	    (equal-p <loc-size> <ob-size>))
            (supported-loc <new-loc>)
            (supported-loc <RMG-loc>)
            (holding <ob>)
	    (at RMG <RMG-loc>)
            (within-height <new-loc> <RMG-loc> *RMG-reach*))))))
 (effects (
   (del (holding <ob>))
   (add (at <ob> <new-loc>)))))



(MOVE
 (params (<adj-loc> <to-loc>))
;  (vars (<adj-loc> <to-loc>))
 (preconds
        (exists (<to-loc>) (is-type <to-loc> location)
	  (and (supported-loc <to-loc>)
	       (vacant-loc <to-loc>)
	       (exists (<adj-loc>) (t-adjacent-locs <to-loc> <adj-loc>)
		  (at RMG <adj-loc>)))))
 (effects (
   (del (at RMG <adj-loc>))
   (add (at RMG <to-loc>)))))

))

(setq *INFERENCE-RULES* '(

(INFER-VACANT-LOC
 (params (<loc>))
;  (vars (<loc> <obj> <obj-loc>))
 (preconds
     (forall (<loc>) (is-type <loc> location)
      (forall (<obj> <obj-loc>) (at <obj> <obj-loc>)
        (disjoint <loc> <obj-loc>))))
 (effects (
   (add (vacant-loc <loc>)))))

;Dont need this rule because the INFER-SUPPORTED-LOC handles this
;just fine.

;(INFER-SUPPORTED-LOC-BASE
; (params (<loc>))
; (vars (<loc>))
; (preconds
;  (forall (<loc>)(is-type <loc> location)
;   (ground-loc <loc>)))
; (effects (
;   (add (supported-loc <loc>)))))


(INFER-SUPPORTED-LOC
 (params (<loc>))
;  (vars (<loc> <sqloc>))
 (preconds
	(forall (<sqloc>) (supporting-sqlocs <loc> <sqloc>)
	    (occupied-sqloc <sqloc>)))
 (effects (
   (add (supported-loc <loc>)))))
 

; This rule is hacked to deal with the 'at-in' problem (might be solved by
; the ability to set up a failed exists generator as a goal)
;
(INFER-OCCUPIED-SQLOC
 (params (<sqloc> <obj>))
;  (vars (<sqloc> <obj> <obj-size> <loc>))
 (preconds
	 (exists (<obj> <obj-size>) (object <obj> <obj-size>)
	     (exists (<loc>) (covers <loc> <obj-size> <sqloc>)
		 (at <obj> <loc>))))
 (effects (
   (add (occupied-sqloc <sqloc>)))))


(INFER-CLEAR
 (params (<ob>))
;  (vars (<ob> <loc> <above-loc>))
 (preconds 
    (exists (<loc>) (at <ob> <loc>)
     (exists (<above-loc>) (above-loc <above-loc> <loc>)
      (vacant-loc <above-loc>))))
 (effects (
   (add (clear <ob>)))))


(INFER-ARM-EMPTY
 (params nil)
;  (vars (<ob>))
 (preconds
     (~ (exists (<ob>) (holding <ob>))))
 (effects (
   (add (arm-empty)))))

))

