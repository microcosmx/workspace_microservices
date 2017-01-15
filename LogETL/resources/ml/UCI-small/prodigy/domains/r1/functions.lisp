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


;;(unless (fboundp 'is-variable) 
;;  "this version of is-variable is for debugging purposes..."
;;  (defun is-variable (x)
;;    (and (symbolp x)
;;	 (char-equal #\< (elt (symbol-name x) 0))
;;	 (char-equal #\> (elt (symbol-name x)
;;			      (1- (length (symbol-name x))))))))
;;
;;


;;;functions to generate new objects...


(defun forget-prior-object-names () 
  "renames the instances of objects starting from instance-1"
  (setq *generated-instances* (make-hash-table :test #'equal)))



(defun is-name  (type obj  &rest x)
  "for a list of objects of a particular type,
   create names for each object in the list."
  (unless (or (is-variable type) (is-variable obj) (null x)
	      (notevery #'(lambda (z) (is-variable z)) x))
    (list (mapcar #'(lambda (y) (list y (geninst type obj))) x))))


(defun  geninst (type obj &optional (space nil))
  "creates a new name for an object instance by concatenating
   the  type and kind of object with the instance number."
  (if (and (symbolp type) (symbolp obj)
	   (or (null space) (symbolp space)))
      (let* ((k (symbol-name type))
	     (o (symbol-name obj))
	     (s (if space (symbol-name space)))
	     (g (if (null s)
		    (concatenate 'string k "-" o)
		    (concatenate 'string k "-" o "-" s)))
	     (n (gethash g *generated-instances*)))
	(setq n (if n 
		    (setf (gethash g *generated-instances*) (1+ n))
		    (setf (gethash g *generated-instances*) 1)))
	(intern  (concatenate 'string g "-" (format nil "~D" n))))))



(defun increment (x y z) 
  "increments a number x by an amount y and either 
   generates result z, or tests whether z = (x + y)."
  (cond ((and (is-variable z)
	      (or (is-variable x) (is-variable y))) 'no-match-attempted)
	((and (numberp x) (numberp y) (is-variable z))  `(((,z ,(+ x y)))))
	((and (numberp x) (numberp y) (numberp z)) (= (+ x y) z))
	(t 'no-match-attempted)))



(defun decrement (x y z) 
  "decrements a number x by an amount y and either 
   generates result z, or tests whether z = (x - y)."
  (cond ((and (is-variable z)
	      (or (is-variable x) (is-variable y))) 'no-match-attempted)
	((and (numberp x) (numberp y) (is-variable z)) `(((,z ,(- x y)))))
	((and (numberp x) (numberp y) (numberp z)) (= (- x y) z))
	(t 'no-match-attempted)))


(defun not-equal-p (x y)
  "determine whether two things are not equal. 
   If one of the args is a variable we cannot match."
  (cond ((or (is-variable x) (is-variable y)) 'no-match-attempted)
	(t (not (equal x y)))))


(defun equal-p (x y)
  "determine whether two things are equal. If one of the args is 
   a variable it will get bound to the other argument."
  (cond ((and (is-variable x) (is-variable y)) 'no-match-attempted)
	((is-variable x) `(((,x ,y))))
	((is-variable y) `(((,y ,x))))
	(t (equal x y))))




(defun greater-or-equal (x y)
  "performs (x >= y) test"
  (cond ((and (numberp x) (numberp y)) (>= x y))
	(t 'no-match-attempted)))


(forget-prior-object-names)
