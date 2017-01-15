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


 

(defun loc-in-room (x y room)
    (cond ((or (is-variable x)
	       (is-variable y))
	   'no-match-attempted)
	  ((is-variable room)
	   `(((,room ,(convert-loc-to-room x y)))))
	  ((atom room)(eq room (convert-loc-to-room x y)))
	  (t (error "Room is not a variable or an atom"))))

(defun convert-loc-to-room (x y)
  (cond ((and (<= y 5)
	      (>= y 1))
	 (cond ((and (<= x 4)
		     (>= x 3))
		'rpdp)
	       ((and (<= x 9)
		     (>= x 5))
		'rclk)
	       ((and (<= x 12)
		     (>= x 10))
		'rril)
	       (t (error "Invalid x room coordinate"))))
	((and (<= y 10)
	      (>= y 6))
	 (cond ((and (<= x 2)
		     (>= x 1))
		'runi)
	       ((and (<= x 6)
		     (>= x 3))
		'rmys)
	       ((and (<= x 9)
		     (>= x 7))
		'rram)
	       ((and (<= x 12)
		     (>= x 10))
		'rhal)
	       (t (error "Invalid x room coordinate"))))
	(t (error "Invalid y room coordinate"))))
