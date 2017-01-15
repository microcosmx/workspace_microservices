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



;  Domain-specific Simplification axioms for machine-shop sched world


(setq *SINGLE-GENERATORS* '((shape t nil) ; objects have a single shape.
                            (temperature t nil ); objects have a single temp.
                            (surface-condition t nil) ;  a single surfact cond
			    (last-scheduled t nil) ; objects last scheduled
                                                   ; at a particular time.
			    (composite-object nil t t t) ; Composite objects
                                                         ; have a name
			    (painted t nil) ; Objs are painted a single color
                            (is-width nil t))) ; objs have a single width


(setq *AT-LEAST-ONE-GENERATORS* '((is-object nil)
                                  (last-scheduled t nil)
				  (is-bolt nil)
                                  (composite-object nil t t t)))

(setq *EVALUABLE-DOMAIN-FNS*
    '((later nil t) ; can generate times either later or earlier (assumes
                    ; *END-TIME* set in functions.lisp
      (later t nil) 
      (is-width nil t) ; Width can be generated (width is a function)
      (is-time nil))) ; can generate possible times, (assumes *END-TIME* set).

(setq *SIMPLIFICATION-RULES* '(

; Composite objects have unique names

(rule1				
  (p-exp (not-equal <r2> <r3>))
  (q-exp t)
  (prove-cond t)
  (known-cond (composite-object <r3> <$r4> <$r5> <r2>)))

; Composite objects have unique names
(rule2				
  (p-exp (not-equal <r2a> <r3a>))
  (q-exp t)
  (prove-cond t)
  (known-cond (composite-object <r3a> <$r7a> <r2a> <$r6a>)))

; Can eliminate shape if not other restrictions on shape, since every object
; has a shape.

 (rule3
  (p-exp (shape <r-ob> <r>))
  (q-exp t)
  (prove-cond t)
  (known-cond (only-single-mention <r>)))

; Can eliminate width constraint if not other restrictions on width, 
; since every object has a width

(rule4
  (p-exp (is-width <r6> <r7>))
  (q-exp t)
  (prove-cond t)
  (known-cond (only-single-mention <r6>)))

; Can eliminate last-scheduled relation if not other restrictions 

(rule5				
  (p-exp (last-scheduled <r-ob1> <r-1>))
  (q-exp t)
  (prove-cond t)
  (known-cond (only-single-mention <r-1>)))

			))


