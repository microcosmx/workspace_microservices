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


    

; 
;      D A T A      S T R U C T U R E S
; 
; 
;  LOCATION:   (location 'x1 'y1 'z1 'x2 'y2 'z2)
; 		where x1<=x2, y1<=y2, z1<=z2
; 
;  OBJECT:  (object 'name (size 'x 'y 'z))
; 
;  SIZE: (size 'x 'y 'z)
; 

; 
;      U N C H A N G E A B L E      O P E R A T O R S
; 


(proclaim '(special *WORLD-X-SIZE* *WORLD-Y-SIZE* *WORLD-Z-SIZE*))

; have to load planner/startup.lisp in order to set *PLANNER-PATH*
(eval-when (compile) 
	(load-path *PLANNER-PATH* "g-loop") ; load g-map and g-loop definitions
	(load-path *PLANNER-PATH* "g-map")
	(load-path *PLANNER-PATH* "data-types"))

; COVERS returns t if loc of size loc-size covers sqloc.  If loc is unbound
; it returns all the locations of size loc-size that cover sqloc.
;
(defun covers (loc loc-size sqloc)
    (cond ((or (is-variable loc-size) (is-variable sqloc))
	   'no-match-attempted)
	  ((and (is-location loc) (is-square-location sqloc) 
		(is-size loc-size))
	   (and (<= (min-x-coor loc) (min-x-coor sqloc))
		(<= (min-y-coor loc) (min-y-coor sqloc))
		(<= (min-z-coor loc) (min-z-coor sqloc))
		(>= (max-x-coor loc) (max-x-coor sqloc))
		(>= (max-y-coor loc) (max-y-coor sqloc))
		(>= (max-z-coor loc) (max-z-coor sqloc))))
	  ((and (is-variable loc) (is-square-location sqloc) 
		(is-size loc-size))
	   (binding-list loc (cover-gen loc-size sqloc)))
	  (t (type-error 'covers))))


(defun cover-gen (size sqloc)
  (let ((x-size (1- (x-dim size)))
	(y-size (1- (y-dim size)))
	(z-size (1- (z-dim size)))
	(loc-list nil))
    (do ((x (- (min-x-coor sqloc) x-size) (1+ x)))
	((> x (min-x-coor sqloc)) loc-list)
      (do ((y (- (min-y-coor sqloc) y-size) (1+ y)))
	  ((> y (min-y-coor sqloc)))
	(do ((z (- (min-z-coor sqloc) z-size) (1+ z)))
	    ((> z (min-z-coor sqloc)))
	  (setq loc-list (cons (make-location x y z
					      (+ x x-size)
					      (+ y y-size)
					      (+ z z-size))
			       loc-list)))))))

(defun in-location (sqloc loc)
    (cond ((or (is-variable loc) (is-variable sqloc)) 'no-match-attempted)
	  ((and (is-location loc) (is-square-location sqloc))
	   (and (<= (min-x-coor loc) (min-x-coor sqloc))
		(<= (min-y-coor loc) (min-y-coor sqloc))
		(<= (min-z-coor loc) (min-z-coor sqloc))
		(>= (max-x-coor loc) (max-x-coor sqloc))
		(>= (max-y-coor loc) (max-y-coor sqloc))
		(>= (max-z-coor loc) (max-z-coor sqloc))))
	  (t (type-error 'covers))))


; NEXT-TO generates all the locations next to a given location that are
; exactly one square large.
;
(defun next-to (loc sqloc)
    (cond ((and (is-variable loc) (is-variable sqloc)) 'no-match-attempted)
	  ((and (is-variable sqloc) (is-location loc))
	   (binding-list sqloc (next-to-gen loc)))
	  ((and (is-location loc) (is-square-location sqloc))
	   (not (null (member sqloc (next-to-gen loc) :test #'equal))))
	  (t (type-error 'next-to))))


(defun next-to-gen (loc)
  (nconc (gen-sqlocs (min-x-coor loc)
                     (1- (min-y-coor loc))
                     (min-z-coor loc)
                     (max-x-coor loc)
                     (1- (min-y-coor loc))
                     (max-z-coor loc))
         (gen-sqlocs (min-x-coor loc)
                     (1+ (max-y-coor loc))
                     (min-z-coor loc)
                     (max-x-coor loc)
                     (1+ (max-y-coor loc))
                     (max-z-coor loc))
         (gen-sqlocs (1- (min-x-coor loc))
                     (min-y-coor loc)
                     (min-z-coor loc)
                     (1- (min-x-coor loc))
                     (max-y-coor loc)
                     (max-z-coor loc))
         (gen-sqlocs (1+ (max-x-coor loc))
                     (min-y-coor loc)
                     (min-z-coor loc)
                     (1+ (max-x-coor loc))
                     (max-y-coor loc)
                     (max-z-coor loc))))



; GEN-SQLOCS generates all the sqlocs within a specified region.
; HACK ALERT --> It is carfully designed to place lower locations before
; higher locations so PRODIGY doesn't try to build stairways to heaven.
;
(defun gen-sqlocs (min-x min-y min-z max-x max-y max-z)
    (do ((z max-z (1- z))
         (loc-list nil))
        ((< z min-z) loc-list)
      (do ((x min-x (1+ x)))
          ((> x max-x))
        (do ((y min-y (1+ y)))
	    ((> y max-y))
	  (setq loc-list (cons (make-location x y z x y z) loc-list))))))



; T-ADJACENT-LOCS generates all the traversable locations from a given loc.
; The traversable locs consist of any square one unit away in either the
;  x or y direction and can have an offset of 0 or 1 in the z direction.
;
; The location must be one square big -- this should be changed.
; 
(defun t-adjacent-locs (loc-a loc-b)
    (cond ((and (is-variable loc-a) (is-variable loc-b)) 'no-match-attempted)
	  ((and (is-variable loc-a) (is-square-location loc-b))
	   (binding-list loc-a (t-adj-gen loc-b)))
	  ((and (is-variable loc-b) (is-square-location loc-a))
	   (binding-list loc-b (t-adj-gen loc-a)))
	  ((and (is-square-location loc-a) (is-square-location loc-b))
	   (not (null (member loc-a (t-adj-gen loc-b) :test #'equal))))
	  (t (type-error 't-adjacent-locs))))


(defun t-adj-gen (loc)
  (nconc (gen-sqlocs (min-x-coor loc)
                     (1- (min-y-coor loc))
                     (1- (min-z-coor loc))
                     (max-x-coor loc)
                     (1- (min-y-coor loc))
                     (1+ (max-z-coor loc)))
         (gen-sqlocs (min-x-coor loc)
                     (1+ (max-y-coor loc))
                     (1- (min-z-coor loc))
                     (max-x-coor loc)
                     (1+ (max-y-coor loc))
                     (1+ (max-z-coor loc)))
         (gen-sqlocs (1- (min-x-coor loc))
                     (min-y-coor loc)
                     (1- (min-z-coor loc))
                     (1- (min-x-coor loc))
                     (max-y-coor loc)
                     (1+ (max-z-coor loc)))
         (gen-sqlocs (1+ (max-x-coor loc))
                     (min-y-coor loc)
                     (1- (min-z-coor loc))
                     (1+ (max-x-coor loc))
                     (max-y-coor loc)
                     (1+ (max-z-coor loc)))))



; WITHIN-HEIGHT determines whether the RMG can reach a given location based
; on his current location and reach.
; 
(defun within-height (location RMG-location reach)
    (cond ((or (is-variable location)
	       (is-variable RMG-location)
	       (is-variable reach))
	   'no-match-attempted)
	  ((or (not (is-location location))
	       (not (is-location RMG-location))
	       (not (boundp reach))
	       (not (numberp (eval reach))))
	   (type-error 'within-height))
	  (t (not (> (abs (- (min-z-coor location)
                             (min-z-coor RMG-location)))
                     (eval reach))))))



; LOCATION-SIZE returns the size of a location
;
(defun location-size (location location-size)
    (cond ((is-variable location) 'no-match-attempted)
          ((and (is-location location) (is-variable location-size))
           (binding-list location-size (list (size-calc location))))
          ((and (is-location location) (is-size location-size))
           (equal location-size (size-calc location)))
          (t (type-error 'location-size))))


(defun size-calc (loc)
    (list 'size
	  (1+ (abs (- (max-x-coor loc) (min-x-coor loc))))
	  (1+ (abs (- (max-y-coor loc) (min-y-coor loc))))
	  (1+ (abs (- (max-z-coor loc) (min-z-coor loc))))))



; EQUALP determines if the two arguments are equal.  If one of the arguments
; is a variable it will get bound to the other argument.
; 
(defun equal-p (first-arg second-arg)
    (cond ((and (is-variable first-arg) (is-variable second-arg))
	   'no-match-attempted)
	  ((is-variable first-arg) (equalp-bind first-arg second-arg))
	  ((is-variable second-arg) (equalp-bind second-arg first-arg))
	  (t (equal first-arg second-arg))))


(defun equalp-bind (var value)
    (binding-list var (list value)))



; GROUND-LOC determines if a location is at ground level (i.e. has an z-coor
;  of zero).
; 
(defun ground-loc (loc)
    (cond ((is-variable loc) 'no-match-attempted)
	  ((not (is-location loc))
	   (type-error 'ground-sq))
	  (t (zerop (min-z-coor loc)))))



; TYPE determines the type of a thing
; 
(defun is-type (thing thing-type)
    (cond ((or (is-variable thing) (is-variable thing-type)) 
	   'no-match-attempted)
	  (t (eq (car thing) thing-type))))



; TOWARDS is given three locations and verifies that the second one is towards
; the third location from the first location.
; 
; The locations must be one square big -- this should be changed.
;
(defun towards (f-loc a-loc t-loc)
    (cond ((or (is-variable f-loc)
	       (is-variable a-loc)
	       (is-variable t-loc))
	   'no-match-attempted)
	  ((or (not (is-square-location f-loc))
	       (not (is-square-location a-loc))
	       (not (is-square-location t-loc)))
	   (type-error 'towards))
	  (t
	   (= (distance-calc f-loc t-loc)
	      (+ (distance-calc f-loc a-loc) (distance-calc a-loc t-loc))))))

(defun distance-calc (a-loc b-loc)
    (+ (abs (- (min-x-coor a-loc) (min-x-coor b-loc)))
       (abs (- (min-y-coor a-loc) (min-y-coor b-loc)))
       (abs (- (min-z-coor a-loc) (min-z-coor b-loc)))))

; This function calculates the distance between a square and the closest
; corner of the given location.
; 
(defun distance-sq-to-loc (loc-sq loc)
    (let ((x (closest-coor (min-x-coor loc-sq)
		 (min-x-coor loc) (max-x-coor loc)))
	  (y (closest-coor (min-y-coor loc-sq)
		 (min-y-coor loc) (max-y-coor loc)))
	  (z (closest-coor (min-z-coor loc-sq)
		 (min-z-coor loc) (max-z-coor loc))))
	 (distance-calc loc-sq (make-location x y z x y z))))


; Determines which corner is closer
(defun closest-coor (coor min-coor max-coor)
    (cond ((< (abs (- coor min-coor))
	      (abs (- coor max-coor)))
	   min-coor)
	  (t max-coor)))

			       
; Calculates the distance from a to b.
; 
(defun distance (a-loc b-loc dist)
    (cond ((is-variable a-loc) 'no-match-attempted)
	  ((is-variable b-loc) 'no-match-attempted)
	  ((not (is-square-location a-loc))
	   (type-error 'distance))
	  ((not (is-location b-loc)) (type-error 'distance))
	  (t (let ((result (distance-sq-to-loc a-loc b-loc)))
		  (cond ((is-variable dist)
			 (list (list (list dist result))))
			((equal dist result) t))))))

; LESS-THAN 

(defun less-than (a b)
    (cond ((is-variable a) 'no-match-attempted)
	  ((is-variable b) 'no-match-attempted)
	  (t (< a b))))


; ABOVE-LOC generates the location above the given location
;
; The height of the above-location must be 1 -- should be changed.
; 
(defun above-loc (a-loc b-loc)
    (cond ((and (is-variable a-loc) (is-variable b-loc)) 'no-match-attempted)
          ((and (is-variable a-loc) (is-location b-loc))
           (binding-list a-loc
			 (list (make-location (min-x-coor b-loc)
					      (min-y-coor b-loc)
					      (1+ (max-z-coor b-loc))
					      (max-x-coor b-loc)
					      (max-y-coor b-loc)
					      (1+ (max-z-coor b-loc))))))
	  ((and (is-variable b-loc) (is-location a-loc))
	   (binding-list b-loc
			 (list (make-location (min-x-coor a-loc)
					      (min-y-coor a-loc)
					      (1- (min-z-coor a-loc))
					      (max-x-coor a-loc)
					      (max-y-coor a-loc)
					      (1- (min-z-coor a-loc))))))
          ((and (is-location a-loc) (is-location b-loc))
	   (equal a-loc (make-location (min-x-coor b-loc)
 				       (min-y-coor b-loc)
				       (1+ (max-z-coor b-loc))
				       (max-x-coor b-loc)
				       (max-y-coor b-loc)
				       (1+ (max-z-coor b-loc)))))
          (t (type-error 'above-loc))))



; SUPPORTING-SQLOCS generates all the squares that are below the bottom 
; corners of a given location. (HN, 25 sep.)
; 
(defun supporting-sqlocs (location sqloc)
    (cond ((is-variable location) 'no-match-attempted)
          ((and (is-location location) (is-square-location sqloc))
           (and (member sqloc (gen-corner-loc location) :test #'equal) t))
          ((and (is-location location) (is-variable sqloc))
           (binding-list sqloc (gen-corner-loc location)))
          (t (type-error 'corner))))


(defun gen-corner-loc (location)
    (cond ((is-square-location location)
	   (list (make-location (min-x-coor location)
		     (min-y-coor location)
		     (1- (min-z-coor location))
		     (min-x-coor location)
		     (min-y-coor location)
		     (1- (min-z-coor location)))))
	  (t (list (make-location (min-x-coor location)
		       (min-y-coor location)
		       (1- (min-z-coor location))
		       (min-x-coor location)
		       (min-y-coor location)
		       (1- (min-z-coor location)))
		   (make-location (min-x-coor location)
		       (max-y-coor location)
		       (1- (min-z-coor location))
		       (min-x-coor location)
		       (max-y-coor location)
		       (1- (min-z-coor location)))
		   (make-location (max-x-coor location)
		       (max-y-coor location)
		       (1- (min-z-coor location))
		       (max-x-coor location)
		       (max-y-coor location)
		       (1- (min-z-coor location)))
		   (make-location (max-x-coor location)
		       (min-y-coor location)
		       (1- (min-z-coor location))
		       (max-x-coor location)
		       (min-y-coor location)
		       (1- (min-z-coor location)))))))


; DISJOINT returns t if loc-a and loc-b do not overlap
;
(defun disjoint (loc-a loc-b)
    (cond ((or (is-variable loc-a) (is-variable loc-b)) 'no-match-attempted)
          ((not (and (is-location loc-a) (is-location loc-b)))
           (type-error 'disjoint))
          (t (or (> (min-x-coor loc-a) (max-x-coor loc-b))
                 (> (min-y-coor loc-a) (max-y-coor loc-b))
                 (> (min-z-coor loc-a) (max-z-coor loc-b))
                 (> (min-x-coor loc-b) (max-x-coor loc-a))
                 (> (min-y-coor loc-b) (max-y-coor loc-a))
                 (> (min-z-coor loc-b) (max-z-coor loc-a))))))

; UNDER-LOC returns t if loc-b is under loc-a.
; 
(defun under-loc (loc-a loc-b)
    (cond ((or (is-variable loc-a) (is-variable loc-b)) 'no-match-attempted)
	  ((not (and (is-location loc-a) (is-location loc-b)))
	   (type-error 'under-loc))
	  (t (and (< (max-z-coor loc-b) (min-z-coor loc-a))
		  (not (or (> (min-x-coor loc-a) (max-x-coor loc-b))
			   (> (min-y-coor loc-a) (max-y-coor loc-b))
			   (> (min-x-coor loc-b) (max-x-coor loc-a))
			   (> (min-y-coor loc-b) (max-y-coor loc-a))))))))




;  BINDING-LIST returns a binding list for a single variable, only
;
(defun binding-list (var val-list)
  (cond ((null val-list) nil)
        ((null (car val-list)) (binding-list var (cdr val-list)))
        (t (append (list (list (list var (car val-list))))
                   (binding-list var (cdr val-list))))))


; 
;  T Y P E     C H E C K I N G
; 

(defun type-error (function)
    (error "~A" (append '(Illegal or incorrect type encountered in -)
			(list function))))


(defun is-location (location)
    (and (listp location)
	 (eq (car location) 'location)
	 (<= (min-x-coor location) (max-x-coor location))
	 (<= (min-y-coor location) (max-y-coor location))
	 (<= (min-z-coor location) (max-z-coor location))
         (>= (min-x-coor location) 0)
         (<= (max-x-coor location) *WORLD-X-SIZE*)
         (>= (min-y-coor location) 0)
         (<= (max-y-coor location) *WORLD-Y-SIZE*)
         (>= (min-z-coor location) 0)
         (<= (max-z-coor location) *WORLD-Z-SIZE*)))


(defun is-square-location (location)
    (and (is-location location)
         (eq (min-x-coor location) (max-x-coor location))
         (eq (min-y-coor location) (max-y-coor location))
         (eq (min-z-coor location) (max-z-coor location))))



(defun is-size (size)
    (cond ((and (listp size) (eq (car size) 'size)))))



; 
;  Accessing Functions for Data Type LOCATION	
; 

(defun make-location (x1 y1 z1 x2 y2 z2)
  (cond ((or (< x1 0)
             (< y1 0)
             (< z1 0)
             (> x2 *WORLD-X-SIZE*)
             (> y2 *WORLD-Y-SIZE*)
             (> z2 *WORLD-Z-SIZE*))
         nil)
        (t (list 'location x1 y1 z1 x2 y2 z2))))


(defun min-x-coor (location)
    (cadr location))


(defun min-y-coor (location)
    (caddr location))


(defun min-z-coor (location)
    (cadddr location))


(defun max-x-coor (location)
    (car (cddddr location)))


(defun max-y-coor (location)
    (cadr (cddddr location)))


(defun max-z-coor (location)
    (caddr (cddddr location)))


;
; Accessing functions for size
;

(defun x-dim (size)
  (cadr size))

(defun y-dim (size)
  (caddr size))

(defun z-dim (size)
  (cadddr size))


