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



; Domain-specific Simplification information for blocksworld

; Important: This assumes that there are no problems with
; only ONE block. If there are such problems, you must
; remove the last simplification rule, rule9.

(setq *SINGLE-GENERATORS* '((on nil t) (on t nil) (holding nil)))

(setq *AT-LEAST-ONE-GENERATORS* '((object nil)))

(setq *EVALUABLE-DOMAIN-FNS* nil)

(setq *SIMPLIFICATION-RULES* '(

; cant be equal if one is on top of other.
 (rule0
  (p-exp (is-equal <r0a> <r0b>))
  (q-exp nil)
  (prove-cond t)
  (known-cond (or (on <r0a> <r0b>)
	          (on <r0b> <r0a>))))

; rule1 =  nobodys on top iff is-clear or held
 (rule1 
  (p-exp (~ (on <r1a> <r1b>)))
  (q-exp (or (clear <r1b>)
             (holding <r1b>)))
  (prove-cond t)
  (known-cond  (and (complete-univ-quantified-var <r1a> object)
	       (f-outside-scope <r1b> <r1a>))))


; rule2 =  on noone iff ontable (or holding)
 (rule2
  (p-exp (~ (on <r2b> <r2a>)))
  (q-exp (or (on-table <r2b>)
             (holding <r2b>)))
  (prove-cond t)
  (known-cond (and (complete-univ-quantified-var <r2a> object)
                   (f-outside-scope <r2b> <r2a>))))


 (rule3
  (p-exp (~ (holding <r3>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (on-table <r3>)))

 (rule4 
  (p-exp (~ (clear <r4>)))
  (q-exp t)
  (prove-cond t)
  (known-cond (holding <r4>)))


 (rule5
  (p-exp (on-table <r5>))
  (q-exp nil)
  (prove-cond t)
  (known-cond (holding <r5>)))

; more than one object in domain.
 (rule9
   (p-exp (is-equal <r9a> <r9b>)) 
   (q-exp nil)
   (prove-cond t)
   (known-cond (or (complete-univ-quantified-var <r9a> object)
                   (complete-univ-quantified-var <r9b> object))))
 ))





