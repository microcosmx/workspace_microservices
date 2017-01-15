#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Robert Joseph

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



;  Extended blocks world - rlj 2/4/88

;  ---- Primitives ----
; 
;  (location <object> <position>)
;  (vacant-loc <position>)
;  adjacent-loc = function that tells if a location is adjacent or not
;  (weight <object> <amount>)

(setq *OPERATORS* '(

(PICK-UP
 (params (<ob> <pos>))
 (preconds (and (object <ob>)
		(clear <ob>)
		(on-table <ob>)
		(location <ob> <pos>)
		(weight <ob> <w>)
		(less-than <w> 200)
	        (arm-empty)))
 (effects ((del (on-table <ob>))
	   (del (location <ob> <pos>))
	   (add (vacant-loc <pos>))
	   (del (clear <ob>))
	   (add (holding <ob>)))))

(PUT-DOWN
 (params (<ob> <pos>))
 (preconds (and (object <ob>)
	        (holding <ob>)
		(vacant-loc <pos>)))
 (effects ((del (holding <ob>))
	   (add (location <ob> <pos>))
	   (del (vacant-loc <pos>))
	   (add (clear <ob>))
	   (add (on-table <ob>)))))

(GLUE
 (params (<ob1> <ob2> <pos1> <w1> <w2>))
 (preconds (and (object <ob1>)
		(object <ob2>)
		(weight <ob1> <w1>)
		(weight <ob2> <w2>)
		(add-numb <w1> <w2> <w3>)
		(holding <ob2>)
		(location <ob1> <pos1>)
		(clear <ob1>)))
 (effects ((del (holding <ob2>))
 	   (del (weight <ob1> <w1>))
	   (del (weight <ob2> <w2>))
	   (add (weight <ob1> <w3>))
	   (del (object <ob2>)))))

(TOPPLE
  (params (<ob> <underob> <pos1> <pos2>))
  (preconds (and (object <ob>)
		 (object <underob>)
		 (on <ob> <underob>)
		 (clear <ob>)
       		 (weight <ob> <w>)
		 (less-than <w> 500)
		 (adjacent-loc <pos1> <pos2>)
		 (vacant-loc <pos2>)
		 (location <ob> <pos1>)))
 (effects ((del (location <ob> <pos1>))
	   (add (location <ob> <pos2>))
	   (del (vacant-loc <pos2>))
	   (del (on <ob> <underob>))
	   (add (clear <underob>))
	   (add (on-table <ob>)))))

(PUSH-OBJECT
 (params (<ob> <pos1> <pos2> <w>))
 (preconds
    (and (object <ob>)
	 (clear <ob>)
	 (weight <ob> <w>)
	 (less-than <w> 500)
	 (adjacent-loc <pos1> <pos2>)
	 (vacant-loc <pos2>)
	 (location <ob> <pos1>)
         (on-table <ob>)))
 (effects ((del (location <ob> <pos1>))
	   (add (location <ob> <pos2>))
	   (del (vacant-loc <pos2>))
	   (add (vacant-loc <pos1>)))))

(UNSTACK
 (params (<ob> <underob> <pos>))
 (preconds
	  (and (object <ob>)
               (object <underob>)
	       (on <ob> <underob>)
	       (clear <ob>)
	       (weight <ob> <w>)
	       (less-than <w> 200)
	       (location <ob> <pos>)
	       (arm-empty)))
 (effects ((del (on <ob> <underob>))
	   (del (clear <ob>))
	   (add (holding <ob>))
	   (del (location <ob> <pos>))
	   (add (clear <underob>)))))


(STACK
 (params (<ob> <underob> <pos>))
 (preconds (and (object <ob>)
		(object <underob>)
    	        (clear <underob>)
		(~ (pyramid <underob>))
		(location <underob> <pos>)
	        (holding <ob>)))
 (effects ((del (holding <ob>))
	   (del (clear <underob>))
	   (add (clear <ob>))
	   (add (location <ob> <pos>))
	   (add (on <ob> <underob>)))))

))

(setq *INFERENCE-RULES* '(


(INFER-ARM-EMPTY
 (params nil)
 (preconds
     (~ (exists (<ob>) (holding <ob>))))
 (effects ((add (arm-empty)))))

		       ))





