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


;;
;; this is the domain theory for a Left-associative Parser...
;;
;;


(setq *OPERATORS* 
 '(


   (LEFT-ASSOC-PARSE
    (params (<n+1> <cat-segm-n> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (combined <n+1> <rule-n+1> <cat-segm-n> <category>)
		   (genatom sur-conc <sur-conc-n+1>)
		   (genatom cat-segm <cat-segm-n+1>)))
    (effects ((del (current <n>))
	      (add (surface-concatenation <sur-conc-n+1> <sur-conc-n> <word>))
	      (add (category-segment <cat-segm-n+1> <cat-segm-n> <category>))
	      (add (parsed <n+1> <rule-n+1> <sur-conc-n+1> <cat-segm-n+1>))
	      (add (current <n+1>)))))




;; possibly solve this problem hierarchically, the following operators
;; form a [complete?] domain theory. They talk about transformations 
;; between ...



   (ABC-RULE-0
    (params (<n+1> <cat-segm-n> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>) 
		   (equal-p <cat-segm-n> null)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (equal-p <category> xy)))
    (effects ((add (datum <n+1> x))
	      (add (datum <n+1> y))
	      (add (combined <n+1> ABC-RULE-0 null xy)))))




   (ABC-RULE-1
    (params (<n+1> <cat-segm-n> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (equal-p <category> xy)
		   (datum <nx> x)
		   (datum <ny> y)))
    (effects ((add (datum <n+1> x))
	      (add (datum <n+1>  y))
	      (add (combined <n+1> ABC-RULE-1 <cat-segm-n> xy)))))




   (ABC-RULE-2
    (params (<n+1> <cat-segm-n> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (equal-p <category> x)
		   (datum <nx> x)))
    (effects ((del (datum <nx> x))
	      (add (combined <n+1> ABC-RULE-2 <cat-segm-n> x)))))



   (ABC-RULE-3
    (params (<n> <cat-segm-n> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (equal-p <category> y)
		   (~ (datum <nx> x))
		   (datum <ny> y)))
    (effects ((del (datum <ny> y))
	      (add (combined <n+1> ABC-RULE-3 <cat-segm-n> y)))))



   (ABC-RULE-4
    (params (<n+1> <category>))
    (preconds (and (successor <n> <n+1>)
		   (current <n>)
		   (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)
		   (queue <n+1> <word>)
		   (lex <word> <category>)
		   (equal-p <category> done)
		   (~ (datum <nx> x))
		   (~ (datum <ny> y))))
    (effects ((add (combined <n+1> ABC-RULE-4 <cat-segm-n> done)))))





   ;;(GENERAL-RULE-0
   ;; (params (<n+1> <category>))
   ;; (preconds (and (successor <n> <n+1>)
   ;;		     (current <n>)
   ;;                (parsed <n> null null)
   ;;		     (queue <n+1> <word>)
   ;;		     (lex <word> <category>)))
   ;; (effects ((add (datum <n+1> <category>))
   ;;           (add (combined <n+1> null <category>))))
   

   

))

(setq *INFERENCE-RULES* 
 '(

   (INFER-PARSED-QUEUE
    (params ())
    (preconds (forall (<n> <w>) (queue <n> <w>)
		      (parsed <n> <rule-n> <sur-conc-n> <cat-segm-n>)))
    (effects ((add (parsed-queue)))))

))
