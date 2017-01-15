#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Mike Miller

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


;; This domain is a propositional logic domain  according to the rules 
;; outlined in Logic and Structure by  D. van Dalen. 


;; The information about a formula is described by the quinternary relation...
;; 
;;
;;    (formula <name> <origin> <type> <status> <genesis>)
;;
;; where each variable is 
;;
;;  <name> := a  symbol gensymmed by connective type.
;;  <origin> :=  assumed | derived
;;  <type>  :=  term | negation | conjunction | disjunction |
;;              implication |  existential | universal 
;;  <status> := available | not-available  
;;  <genesis> := the time the formula was created.
;;
;; examples...
;;
;; (formula c-1 assumed conjunction available 0)  ... let c-1 = (p & p)
;; (formula t-1 assumed term not-available 0)     ... let t-1 = p [inside c-1]
;; (formula t-2 derived term available 3)         ... let c-1 = p 
;;                                                     s.t.   [c-1   |-   t-2] 




;;
;;
;; The actual formula is described by the relation ...
;;
;;      ( <formult-name> is <form> [<form>] [<form>] [...] )
;;                               1       2        3 
;;
;; where each variable is 
;;
;; <formult-name>  := a symbol gensymmed by a connective type
;; <form> := <formult-name> | <connective-name>
;; 
;; <connective-name> := and | or | implies | not | forall | exists
;; 
;;
;; examples...
;; 
;; (t-1 is p), (c-1 is t-1 and t-1) ...asing (p & p)
;; (t-1 is p), (n-1 is not p), (d-2 is t-1 or n-1)  ... representing (p V ~p)
;; (t-2 is p), (t-4 is q), (c-33 is t-2 and t-4)  ... representing (p & q)
;;

;;
;;



(setq *OPERATORS* 
 '(

   (CONJUNCTION-INTRODUCTION
    (params (<p> <q>))
    (preconds (and (formula <p> <po> <pt> available <pg>)
		   (formula <q> <qo> <qt> available <qg>)
		   (genformula conjunction <c>)
		   (current-time <t1>) 
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <c> as <p> and <q>))
	      (add (formula <c> derived conjunction available <t2>)))))



   (CONJUNCTION-ELIMINATION
    (params (<c>))
    (preconds (and (define <c> as <p> and <q>)
		   (formula <c> <co> conjunction available <cg>)
		   (formula <p> <po> <pt> <ps> <pg>)
		   (formula <q> <qo> <qt> <qs> <qg>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (formula <p> derived <pt> available <t2>))
	      (add (formula <q> derived <qt> available <t2>)))))



   (IMPLICATION-INTRODUCTION 
    (params (<p> <q>))
    (preconds (and (formula <p> <po> <pt> available <pg>)
		   (formula <q> derived <qt> available <qg>)
		   (before <pg> <qg>)
		   (genformula implication <i>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <i> as <p> implies <q>))
	      (add (formula <i> derived implication available <t2>)))))


   (IMPLICATION-ELIMINATION
    (params (<i>))
    (preconds (and (define <i> as <p> implies <q>)
		   (formula <i> <io> implication available <ig>)
		   (formula <p> <po> <pt> available <pg>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (formula <q> <qo> <qt> available <t2>)))))




   (NEGATION-INTRODUCTION
    (params (<p>))
    (preconds (and (formula <p> <po> <pt> available <pg>)
		   (define <q> as falsum)
		   (formula <q> derived term available <qg>)
		   (before <pg> <qg>)
		   (genformula negation <n>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <n> as not <p>))
	      (add (formula <n> derived negation available <t2>)))))


   (NEGATION-ELIMINATION
    (params (<p>))
    (preconds (and (formula <p> <po> <pt> available <pg>)
		   (define <q> as not <p>)
		   (formula <q> <qo> negation available <qg>)
		   (genformula falsum <f>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <f> as falsum))
	      (add (formula <f> derived term available <t2>)))))



   (REDUTIO-AD-ABSURDUM
    (params (<p>))
    (preconds (and (define <p> as not <q>)
		   (formula <p> <po> negation <ps> <pg>)
		   (formula <q> <qo> <qt> <qs> <qg>)
		   (define <f> as falsum)
		   (formula <f> derived term available <fg>)
		   (before <pg> <fg>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (formula <q> derived <qt> available <t2>)))))


   (FALSUM-RULE 
    (params (<q>))
    (preconds (and (define <p> as falsum)
		   (formula <p> <po> term available <pg>)
		   (formula <q> <qo> <qt> <qs> <qg>)
		   (current-time <t1>)
		   (increment-time <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (formula <q> derived <qt> available <t2>)))))


   (EQUIVALENCE-INTRODUCTION 
    (params (<p> <q>))
    (preconds (and (define <p> as <p1> implies <p2>)
		   (formula <p> <po> implication available <pg>)
		   (define <q> as <q1> implies <q2>)
		   (formula <q> <qo> implication available <qg>)
		   (equal-p <p1> <q2>)
		   (equal-p <p2> <q1>)
		   (genformula equivalence <e>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <e> as <p1> equivalent <q1>))
	      (add (formula <e> derived equivalence available <t2>)))))



   (EQUIVALENCE-ELIMINATION 
    (params (<e>))
    (preconds (and (define <e> as <p> equivalent <q>)
		   (formula <e> <eo> equivalence available <eg>)
		   (formula <p> <po> <pt> <ps> <pg>)
		   (formula <q> <qo> <qt> <qs> <qg>)
		   (current-time <t1>)
		   (increment-time <t1> <t2>)))
    (effects ((del (current-time <t1>))
	      (add (current-time <t2>))
	      (add (define <p> derived <pt> available <t2>))
	      (add (define <q> derived <qt> available <t2>)))))

))      




(setq *INFERENCE-RULES* nil)
