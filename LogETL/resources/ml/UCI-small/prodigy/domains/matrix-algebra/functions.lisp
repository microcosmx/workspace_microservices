#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Manuela Veloso

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



(defun pred-f (x y)         ;x is the predecessor of y
  (cond
   ((and (is-variable x)
	 (is-variable y))
    'no-match-attempted)
   ((is-variable x)
    `(((,x ,(- y 1)))))
   ((is-variable y)
    `(((,y ,(+ x 1)))))
   ((eq y (+ x 1)))))



(defun diff-f (x y)         ;returns true iff x is different from y
  (cond
   ((or (is-variable x)
	(is-variable y))
    'no-match-attempted)
   (t 
    (not (eq x y)))))



(defun div-f (x y res)     ;res is the quotient of x by y
  (cond
   ((or (and (is-variable x)
	     (is-variable y)
	     (is-variable res))
	(and (is-variable x)
	     (is-variable y))
	(and (is-variable x)
	     (is-variable res))
	(and (is-variable y)
	     (is-variable res)))
    'no-match-attempted)
   ((is-variable x)
    `(((,x ,(* res y)))))
   ((is-variable y)
    `(((,y ,(/ x res)))))
   ((is-variable res)
    (if (not (equal y 0))
	`(((,res ,(/ x y))))
	'no-match-attempted))
   ((eq res (/ x y)))))


(defun neg-f (x y)            ;x is the symmetric of y
  (cond
   ((and (is-variable x)
	 (is-variable y))
    'no-match-attempted)
   ((is-variable x)
    `(((,x ,(* -1 y)))))
   ((is-variable y)
    `(((,y ,(* -1 x)))))
   ((eq x (* -1 y)))))


(defun range-f (k start end)    ;generates integers from 'start to 'end
  (cond
   ((or (not (is-variable k))
	(is-variable start)
	(is-variable end))
    'no-match-attempted)
   (t
    (do* ((val end (- val 1))
	  (lst-of-bindings 
	   (list (list (list k val)))
	   (cons (list (list k val))
		 lst-of-bindings)))
	 ((equal start val) lst-of-bindings)))))


(defun sum-f (x y res)          ;res is the sum of x and y
  (cond
   ((or (and (is-variable x)
	     (is-variable y)
	     (is-variable res))
	(and (is-variable x)
	     (is-variable y))
	(and (is-variable x)
	     (is-variable res))
	(and (is-variable y)
	     (is-variable res)))
    'no-match-attempted)
   ((is-variable x)
    `(((,x ,(- res y)))))
   ((is-variable y)
    `(((,y ,(- res x)))))
   ((is-variable res)
    `(((,res ,(+ x y)))))
   ((eq res (+ x y)))))



(defun mult-f (x y res)        ;res is the product of x and y
  (cond
   ((or (and (is-variable x)
	     (is-variable y)
	     (is-variable res))
	(and (is-variable x)
	     (is-variable y))
	(and (is-variable x)
	     (is-variable res))
	(and (is-variable y)
	     (is-variable res)))
    'no-match-attempted)
   ((is-variable x)
    `(((,x ,(/ res y)))))
   ((is-variable y)
    `(((,y ,(/ res x)))))
   ((is-variable res)
    `(((,res ,(* x y)))))
   ((eq res (* x y)))))

