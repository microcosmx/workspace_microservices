#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Oren Etzioni

The PRODIGY System was designed and built by Steven Minton, Craig Knoblock,
Dan Kuokka and Jaime Carbonell.  Additional contributors include Henrik Nordin,
Yolanda Gil, Manuela Veloso, Robert Joseph, Santiago Rementeria, Alicia Perez, 
Ellen Riloff, Michael Miller, and Dan Kahn.

This software is made available under the following conditions:
1) PRODIGY will only be used for internal, noncommercial research purposes.
2) The code will not be distributed to other sites without the explicit 
   permission of the designers.  PRODIGY is available by request.
3) Any bugs, bug fixes, or extensions will be forwarded to the designers. 

Send comments or requests to: prodigy@cs.cmu.edu or The PRODIGY PROJECT,
School of Computer Science, Carnegie Mellon University, Pittsburgh, PA 15213.
*******************************************************************************|#

; A file to compute Manhatten distance based on functions by jba.
; July 4, 1989.

(defvar *md-table* nil)
(defvar *coordinate-table* nil)

; n should be the length of a side. so it's 3 for the 8puzzle.
(defun precompute-manhattan-distances (n)
  (setq *md-table* (make-array (list (1+ (* n n))
				     (1+ (* n n)))))
  (iter:iterate (for pos1 from 1 to (* n n))
		(do (iter:iterate (for pos2 from 1 to (* n n))
				  (do (setf (aref *md-table* pos1 pos2)
					    (compute-manhattan-distance 
					      pos1 pos2 n)))))))

(defun compute-manhattan-distance (pos1 pos2 n)
  ;; assumes a nxn grid
  (let ((h-dist (abs (- (mod (1- pos1) n) (mod (1- pos2) n))))
	(v-dist (abs (- (div (1- pos1) n) (div (1- pos2) n)))))
    (+ h-dist v-dist)))

(defun manhattan-distance (pos1 pos2)
  (aref *md-table* pos1 pos2))

(defun div (x y)
  (truncate (/ x y)))
;****************************************************************
;       compute the md of the current loc of the tile to the desired.


(lower-md-op
          (lhs (and (current-node <node>)
	            (current-goal <node> (at <l> <t>))
		    (candidate-op <node> <o1>)
		    (candidate-op <node> <o2>)
		    (not-equal <o1> <o2>)
		    (unmatched-prec <o1> <p1>)
    		    (unmatched-prec <o2> <p2>)
		    (md <p1> <m1>)
		    (md <p2> <m2>)
		    (less-than <m1> <m2>)))
	  (rhs (prefer operator <o1> <o2>)


; returns a binding list ((num 504)).
(defun md (goal-list num)
  (if (is-variable goal-list) 'no-match-attempted
    (list (list num 
		(iter:iterate (for g in goal-list)
			      (sum (goal-md g)))))))


(defun less-than (x y)
  (cond ((is-variable x) 'no-match-attempted)
	((is-variable y) 'no-match-attempted)
	((< x y) t)
	(t nil)))


;g=(at <x> <y>).
(defun goal-md (g)

need from steve:
1. function that'll give me unmatched precs.
