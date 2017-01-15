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


;  ----------- functions for domain topple blocks world ------------

;  adjacent-loc: determines adjacent locations for a x,y coordinate grid
;      given pos1 and pos2 functions tells if they are next to one another
;      given pos1 or pos2 the function generates all adjacent positions

(defun adjacent-loc (pos1 pos2)
    (cond ((and (is-variable pos1)
		(is-variable pos2))
	   'no-match-attempted)
	  ((is-variable pos1)
	   (let ((x2 (getx pos2))
		 (y2 (gety pos2))
		 (ls nil))
		(do* ((x (- x2 1) (+ x 1)))
		     ((= x (+ x2 2)) ls)
		     (do* ((y (- y2 1) (+ y 1)))
			  ((= y (+ y2 2)))
			  (if (and (not 
					(and (= x x2)
					     (= y y2)))
				   (> y 0)
				   (< y *YDIM*)
				   (> x 0)
				   (< x *XDIM*))
			      (setq ls (cons (list (list pos1 (list x y)))
					     ls)))))))
	  ((is-variable pos2)
	   (let ((x1 (getx pos1))
		 (y1 (gety pos1))
		 (ls nil))
		(do* ((x (- x1 1) (+ x 1)))
		     ((= x (+ x1 2)) ls)
		     (do* ((y (- y1 1) (+ y 1)))
			  ((= y (+ y1 2)))
			  (if (and (not 
					(and (= x x1)
					     (= y y1)))
				   (> y 0)
				   (< y *YDIM*)
				   (> x 0)
				   (< x *XDIM*))
			      (setq ls (cons (list (list pos2 (list x y)))
					     ls)))))))
	  (t 
	     (let ((x1 (getx pos1))
		   (y1 (gety pos1))
		   (x2 (getx pos2))
		   (y2 (gety pos2)))
		  (cond ((and (= x1 x2)
			      (or (= y1 (- y2 1))
				  (= y1 (+ y2 1))))
			 t)
			((and (= y1 y2)
			      (or (= x1 (- x2 1))
				  (= x1 (+ x2 1))))
			 t)
			((= 1 (abs (* (- x1 x2) (- y1 y2))))
			 t)
			(t nil))))))

; less-than: determines if one number is less than another number
	  
(defun less-than (x y)
    (cond ((or (is-variable x)
	       (is-variable y))
	   'no-match-attempted)
	  ((< x y) t)))

; add-num: adds two numbers, depositing result in z
	  
(defun add-numb (x y z)
    (cond ((and (is-variable z)
		(not (is-variable x))
		(not (is-variable y)))
	   (list (list (list z (+ x y)))))
    	  ((and (not (is-variable z))
		(not (is-variable x))
		(not (is-variable y))
		(= z (+ x y)))
	   t)
	  (t 'no-match-attempted)))

;  distance determines the distance between two positions 
;    currently not being used

(defun distance (pos1 pos2 dist)
    (cond ((or (is-variable pos1)
	       (is-variable pos2)
	       (not (is-variable dist)))
	   'no-match-attempted)
	  (t (let ((deltax (- (getx pos1) (getx pos2)))
		   (deltay (- (gety pos1) (gety pos2))))
		  (list 
		    (list 
		      (list dist 
			    (sqrt (+ (square deltax)
				     (square deltay))))))))))
	       
(defun square (x)
    (* x x))

(defun getx (pos)
    (car pos))
    
(defun gety (pos)
    (cadr pos))

	    
