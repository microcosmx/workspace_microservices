#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Yolanda Gil and Jaime Carbonell

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



; FILE: domain.lisp

;  Telescope domain.  yg 10/22/87


(setq *OPERATORS* '(

(GRIND-CONCAVE
  (params (<obj>))
  (preconds
      (is-solid <obj>))
  (effects (
      (add (is-concave <obj>))
      (if (is-planar <obj>) (del (is-planar <obj>))))))
;      (if (is-reflective <obj>) (del (is-reflective <obj>)))
;      (if (is-polished <obj>) (del (is-polished <obj>))))))

(POLISH
  (params (<obj>))
  (preconds
      (and (is-clean <obj>)
           (is-glass <obj>)))
;           (~ (is-reflective <obj>))))
  (effects (
      (add (is-polished <obj>)))))

(ALUMINIZE
  (params (<obj>))
  (preconds
      (and (is-clean <obj>)
           (is-solid <obj>)))
  (effects (
      (add (is-reflective <obj>)))))
;      (if (is-clean <obj>) (del (is-clean <obj>))))))

(CLEAN
  (params (<obj>))
  (preconds
      (is-solid <obj>))
  (effects (
      (add (is-clean <obj>)))))

))

;;; Prodigy always adds the subgoals on a stack, so the last subgoal
;;;  in the rule is the first that Prodigy tries.

(setq *INFERENCE-RULES* '(

(TELESCOPE-MIRROR
  (params (<obj>))
  (preconds
      (and (is-mirror <obj>)
           (is-concave <obj>)))
  (effects (
      (add (is-telescope-mirror <obj>)))))

(IS-MIRROR
  (params (<obj>))
  (preconds 
      (and (is-reflective <obj>)
           (is-polished <obj>)))
  (effects (
      (add (is-mirror <obj>)))))

))


