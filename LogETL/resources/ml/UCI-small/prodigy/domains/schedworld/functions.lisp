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



(proclaim '(special *START-STATE* *END-TIME*))

(eval-when (compile) 
	(load-path *PLANNER-PATH* "g-loop")
	(load-path *PLANNER-PATH* "g-map"))




;      D A T A      S T R U C T U R E S
; 


; 
;      U N C H A N G E A B L E      O P E R A T O R S
; 




(defun sprayable (paint)
    (cond ((is-variable paint)
	   (binding-list paint '((REGULAR RED) (REGULAR WHITE))))
	  ((eq (car paint) 'REGULAR))))


(defun have-paint-for-immersion (paint)
   (cond ((not (equal paint '(WATER-RES RED))))))

(defun have-bit (w)
    (cond ((is-variable w)
	   (binding-list w (list '(2 mm)
				 '(6 mm)
				 '(1 cm)
				 '(1.4 cm))))
	  ((member (car w) '(2 6 1 1.4) :test #'equal)
	   t)))

(defun is-punchable (obj size orient)
    (cond ((is-variable orient)
	   (cond ((is-variable size)
		  (mult-binding-list
		      (list size orient)
		      (list (list '(1 cm) '(1.4 cm))
			    '(ORIENTATION-1  ORIENTATION-2
				 ORIENTATION-4))))
		 ((binding-list orient 
		      '(ORIENTATION-1  ORIENTATION-2
			   ORIENTATION-4)))))
	  ((is-variable size)
	   (and (not (equal orient 'ORIENTATION-3))
		(binding-list size (list '(1 cm) '(1.4 cm)))))
	  (t  (and (not (equal orient 'ORIENTATION-3))
		   (not (eq (cadr size) 'cm))))))

(defun is-drillable (obj orient)
    (cond ((is-variable orient)
	   (binding-list orient   '(ORIENTATION-1  ORIENTATION-2
				       ORIENTATION-3)))
	  (t (not (equal orient 'ORIENTATION-4)))))

(defun regular-shape (shape)
    (cond ((is-variable shape)
	   nil)
	  ((member shape '(RECTANGULAR CYLINDRICAL))
	   t)))

(defun has-clamp (m)
    (cond ((is-variable m)
	   (binding-list m '(PUNCH SPRAY-PAINTER POLISHER)))
	  ((member m '(PUNCH SPRAY-PAINTER POLISHER))
	   t)))

(defun composite-shape (ob or s1 s2)
  (list (list (list s2 'NEW-SHAPE))))


(defun composite-object (new-ob or o1 o2)
    (cond ((is-variable new-ob)
	   (binding-list new-ob (list (concatenate 'string (symbol-name o1)
						   (symbol-name o2)))))
	  (t)))

(defun can-be-welded (o1 o2 orient)
    (cond ((is-variable orient)
	   (binding-list orient '(ORIENTATION-1  ORIENTATION-3)))
	  ((member orient '(ORIENTATION-1  ORIENTATION-3))
	   t)))

(defun can-be-bolted (o1 o2 orient)
    (cond ((is-variable orient)  
	   (binding-list orient  '(ORIENTATION-1 ORIENTATION-2 ORIENTATION-4)))
	  ((member orient '(ORIENTATION-1 ORIENTATION-2 ORIENTATION-4))
	   t)))

(defun is-width (w b)
  (cond ((is-variable w)
	 (list (list (list w (cadr b)))))
	((is-variable b) (error "is-width"))
	((equal w (cadr b)))))

(defun cul-de-sac (rm)
    (cond ((is-variable rm) 
	   (cond 
		 ((member '(is-room rm3) *START-STATE* :test #'equal)
		  (binding-list rm '(rm1 rm4)))
		 ((member '(is-room den) *START-STATE* :test #'equal)     
		  (binding-list rm '(bath lroom hall)))
		 ((binding-list rm '(rm1 rm2)))))
	  ((member '(is-room rm3) *START-STATE* :test #'equal)
	   (and (member rm '(rm1 rm4))
		t))
	  ((member '(is-room den) *START-STATE* :test #'equal)     
	   (and (member rm '(bath lroom hall))
		t))
	  ((member rm '(rm1 rm2))
	   t)))




; LESS-THAN 

(defun less-than (a b)
    (cond ((is-variable a) 'no-match-attempted)
	  ((is-variable b) 'no-match-attempted)
	  (t (< a b))))

(defun times-before (last-tme)
    (g-loop (init tme 1 ret-val nil)
	  (while (not (> tme last-tme)))
	  (do (push tme ret-val))
	  (next tme (+ 1 tme))
	  (result ret-val)))

(defun times-after (last-tme)
    (g-loop (init tme (+ 1 last-tme) ret-val nil)
	  (while (not (> tme *END-TIME*)))
	  (do (push tme ret-val))
	  (next tme (+ 1 tme))
	  (result ret-val)))

(defun before (tme last-tme)
    (cond ((and (is-variable tme) 
		(is-variable last-tme))
	   (error "before"))
	  ((is-variable tme)
	   (binding-list tme (times-before last-tme)))
	  ((is-variable last-tme)
	   (binding-list last-tme (times-after tme)))
	  ((> last-tme tme))))
	  

(defun later (tme prev-tme)
    (cond ((and (is-variable tme) 
		(is-variable prev-tme))
	   'no-match-attempted)
	  ((is-variable tme)
	   (binding-list tme (times-after prev-tme)))
	  ((is-variable prev-tme)
	   (binding-list prev-tme (times-before tme)))
	  ((> tme prev-tme))))

(defun is-time (tme)
    (cond ((is-variable tme) (binding-list tme (times-before *END-TIME*)))
          (t)))

;  BINDING-LIST returns a binding list for a single variable, only
;
(defun binding-list (var val-list)
  (cond ((null val-list) nil)
        ((null (car val-list)) (binding-list var (cdr val-list)))
        (t (append (list (list (list var (car val-list))))
                   (binding-list var (cdr val-list))))))

(defun mult-binding-list (vars val-lists)
    (and vars
	 (g-loop (init ret-val nil vals (car val-lists) var (car vars)
		     rst-bindings (mult-binding-list (cdr vars) 
				      (cdr val-lists)))
	       (while vals)
	       (do (cond (rst-bindings
			     (setq ret-val 
				   (append
					  (g-map (b in rst-bindings)
						(save (cons (list var 
								  (car
								      vals)) b)))
					  ret-val)))
			 (t (push (list (list var (car vals))) ret-val))))
	       (next vals (cdr vals))
	       (result ret-val))))


