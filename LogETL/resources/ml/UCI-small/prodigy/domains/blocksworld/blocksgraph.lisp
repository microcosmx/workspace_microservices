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


;==========================================================================
; File:   graphics.lisp     Version: 0.0                  Created: 3/2/88
;
; Locked by:  none.                                      Modified: 4/4/88
; 
; Purpose:    To standardize domain graphics for Prodigy.
;
;==========================================================================
; This was written by dkahn in june and july 1989.
; The user must be careful not to use more blocks in the domain then can fit
; on the graphics window, otherwise the graphics will not work well, although
; I don't think it will crash.  The objects scale automatically to the width
; and height of the characters used to label them.  The height is, of course, 
; determined by the font used, but the width may be controlled by the user
; when writing the problems for the domain.

(require 'pg-system)
(use-package (symbol-name 'pg))


(proclaim
 '(special *DOMAIN-WINDOW* *FINISH*   *NODE-MSG-X*    *NODE-MSG-Y*
	   *STATE-MSG-X*   *STATE-MSG-Y*   *START-STATE*   *WIDTH*
	   *DOMAIN-STATE*  *DOMAIN-NODE*  *DOMAIN-GRAPHICS*))

(proclaim
  '(special *GRAPHICS-INFO* *DOMAIN-CHAR-HEIGHT* *GRAPHICS-INFO*
            *BLOCK-SEPARATION-X* *BLOCK-SEPARATION-Y*
            *DOMAIN-HEIGHT* *DOMAIN-WIDTH* *HAND-LOC-X* *HAND-LOC-Y*
            *DOMAIN-CHAR-WIDTH* *X-DIM* *Y-DIM* *START-X* *START-Y*
            *BLOCK-WIDTH* *BLOCK-HEIGHT* *DOMAIN-LINES* *DOMAIN-COLS* ))
;==========================================================================
;                      Domain  Dependent  Functions
;==========================================================================

;; RESET-DOMAIN-GRAPHICS-PARAMETERS should be called when a new problem is 
;; loaded. This function sets graphics variables to null. In particular, 
;; the variables  *NODE-MSG-X*, *NODE-MSG-Y*, which determine the location
;; of the node message in the graphics window and *STATE-MSG-X*, and 
;; *STATE-MSG-Y* which determine the location of the state message in the
;; graphics window should be set to null in this function.
;;

(defun reset-domain-graphics-parameters ()
  (psetq *NODE-MSG-X*  NIL 
	 *NODE-MSG-Y*  NIL
	 *STATE-MSG-X* NIL 
	 *STATE-MSG-Y* NIL
	 *WIDTH*       NIL
	 *DOMAIN-CHAR-HEIGHT* NIL
	 *DOMAIN-CHAR-WIDTH* NIL     ; MAXIMUM WIDTH OF an object
	 *GRAPHICS-INFO* NIL)
   (mapcar #'makunbound '(*DOMAIN-HEIGHT* *DOMAIN-LINES* *DOMAIN-WIDTH*
		*START-X* *START-Y* *BLOCK-WIDTH* *BLOCK-HEIGHT* 
		*BLOCK-SEPARATION-X* *BLOCK-SEPARATION-Y* *HALF-BLOCK*
		*HAND-LOC-X* *HAND-LOC-Y*  *HAND-DATA*))
)
;--------------------------------------------------------------------------

;; DETERMINE-DOMAIN-GRAPHICS-PARAMETERS SETS the variables used in drawing 
;; the domain graphics for a given problem. This routine should also 
;; determine the location of the node and state messages: i.e., set the
;; variables (*NODE-MSG-Y*, *NODE-MSG-X*, *STATE-MSG-X*, *STATE-MSG-Y*).
;;

(defun determine-domain-graphics-parameters (problem)
  (setq *GRAPHICS-INFO* (init-a-list problem))
;  (fill-in-graphics-info *start-state*)
  (setq *WIDTH* 0
	*DOMAIN-CHAR-HEIGHT* (pg-text-height *DOMAIN-WINDOW* "A")
	*DOMAIN-HEIGHT* (pg-window-height *DOMAIN-WINDOW*)
	*DOMAIN-WIDTH* (pg-window-width *DOMAIN-WINDOW*)
	*DOMAIN-LINES* (truncate *DOMAIN-HEIGHT* *DOMAIN-CHAR-HEIGHT*)
	*DOMAIN-CHAR-WIDTH* (pg-text-width *DOMAIN-WINDOW* "A")
	*DOMAIN-COLS* (truncate *DOMAIN-WIDTH* *DOMAIN-CHAR-WIDTH*)
	*X-DIM* (count-objects *STATIC-STATE-PREDS*)
	*Y-DIM* *X-DIM*
	*START-X* (truncate (* .05 *DOMAIN-WIDTH*)); to be added to
	*START-Y* (- *DOMAIN-HEIGHT* (truncate (* .15 *DOMAIN-HEIGHT*)))
					; to be subtracted from
	*NODE-MSG-X*  (+ *START-X* 10)
	*NODE-MSG-Y*  (- *DOMAIN-HEIGHT* 5 *DOMAIN-CHAR-HEIGHT*)
	*STATE-MSG-X* (+ *START-X* 10)
	*STATE-MSG-Y* (- *NODE-MSG-Y* 2 *DOMAIN-CHAR-HEIGHT*)

	*BLOCK-WIDTH* (+ 10 (* *DOMAIN-CHAR-WIDTH* (max-char-in-label problem)))
	*BLOCK-HEIGHT* (+ 6 *DOMAIN-CHAR-HEIGHT*)
	*BLOCK-SEPARATION-X* 5
	*BLOCK-SEPARATION-Y* 0
        *HALF-BLOCK* (truncate *BLOCK-HEIGHT* 2)
        *HAND-LOC-X* (truncate (* .6 *DOMAIN-WIDTH*))
        *HAND-LOC-Y* (+ *BLOCK-HEIGHT* 20)
	*HAND-DATA* `((,(- *HAND-LOC-X* 2) ,(- *HAND-LOC-Y* *HALF-BLOCK*)) ;start
                     (0 ,(- 0 *HALF-BLOCK* 4)) ;left side
	             (,(+ 2 (truncate *BLOCK-WIDTH* (/ .4))) 0) ; left top
		     (0 ,(- (+ *block-height* 5) *HAND-LOC-Y*)) ; up arm
		     (,(truncate *block-width* (/ .2)) 0) ; top of arm
		     (0 ,(- *HAND-LOC-Y* (+ *block-height* 5))); down arm
		     (,(+ 2 (truncate *BLOCK-WIDTH* (/ .4))) 0); right top
                     (0 ,(+ *HALF-BLOCK* 4)))
		    
  )

)


; MAX-CHAR-IN-LABEL moves through the initial state description
;  and creates a list of numbers, 0 for each predicate that is not
; OBJECT and the length of the object name for each predicate that
; is OBJECT.  It then returns the MAX of the list.

(defun max-char-in-label (preds)
    (apply #'max (mapcar #'(lambda (x) (if (eq (car x) 'object)
                                         (length (symbol-name (second x)))
							0))
		                                               preds)
    )
)
(defun last-el (lst)
     (car (last lst)))

; In pos-data the x-pos and y-pos are the position of the 
; object in space.  rest-position is the value to give to x-pos
; when the object is on-table.  Y-pos is 0 when the object is
; on-table.  Unchanged-flag is used by add-domain-graphic-objects to
; keep track of which objects aren't in the updata list.

(defstruct pos-data
           (x-pos nil)
	   (y-pos nil)
           rest-pos
	   (in-arm-p nil)
	   (unchanged-flag nil)
)


(defun count-objects (static-preds)
     (cond ((null static-preds) 0)
 	   ((eq 'object (caar static-preds)) 
                   (1+ (count-objects (cdr static-preds))))
	   (t (count-objects (cdr static-preds)))
     )
)

; This sets up the assocation list for *GRAPICS-INFO*.
; It must set-up an assoc for each object instantiated in
; lst.  It must then assign to the object a table position.
; x-pos is a counter that holds the table positions.  It is
; incremented so that on two objects receive the same table postion.

(defun init-a-list (lst)
    (do ((a-list nil) (x-pos 0)) ((null lst) a-list)
        (cond ((eq (caar lst) 'object)
               (setq a-list 
                  (acons (cadar lst) (make-pos-data :rest-pos x-pos) a-list)
	       )
	       (setq x-pos (1+ x-pos))
               (pop lst)
	      )
	      (t (pop lst)) 	; if pred is not static-object
	)
    )
)
#|
(defun fill-in-graphics-info (lst)
  (do () ((null lst) nil)
      (setq lst (build-list lst))
  )
)


; BUILD-LIST takes a list of initial state predicates
(defun build-list (lst)
  (let ((pred (car lst)))
    (cond ((null lst) nil)
          ((eq (car pred) 'on-table)
		(update (last-el pred) (pos-data-rest-pos (cdr
						        (assoc (last-el pred)
							*GRAPHICS-INFO*)));X
						        0); Y
		(build-list (cdr lst)))
          ((eq (car pred) 'holding) (setf (pos-data-rest-pos (cdr
							(assoc (last-el pred)
						 	*GRAPHICS-INFO*)))
                                                        t))
          ((and (eq (car pred) 'on) (good-object-p (last-el pred)))
                (update (cadr pred) (pos-data-x-pos  (cdr 
                                                       (assoc (last-el pred)
                                                        *GRAPHICS-INFO*)))
                                       (1+ (pos-data-y-pos (cdr (assoc (last-el pred)
                                                       *GRAPHICS-INFO*)))))
                 (build-list (cdr lst)))
          ((eq (caar lst) 'on) (append (list pred) (build-list (cdr lst))))
          (t (build-list (cdr LST)))
    )
  )
)
; GOOD-OBJECT-P is true of all the data in the struct are non-nil
; 
(defun good-object-p (object)
    (let ((struct (cdr (assoc object *GRAPHICS-INFO*))))
       (and (pos-data-x-pos struct)
            (pos-data-y-pos struct))
    )
)
|#

(defmacro get-x-point (lst)
    `(caar ,lst)
)

(defmacro get-y-point (lst)
    `(cadar ,lst)
)
;--------------------------------------------------------------------------

;; DRAW-DOMAIN-BACKGROUND uses domain graphics parameters to draw the
;; rear plane for the given domain -- drawn before any domain objects
;; are added to the domain graphics window. 
;;

(defun draw-domain-background ())


;--------------------------------------------------------------------------

;; DELETE-DOMAIN-GRAPHIC-OBJECTS erases objects from the domain window. 
;; The argument to this function is a list containing predicates to delete.
;; The function must parse the relevant graphics predicates and devise some
;; method for removing them from the domain window.
;;

(defun delete-domain-graphic-objects (state-preds)
   (unless (null state-preds)
   (let ((pred (caar state-preds)))
     (cond ((eq 'on-table pred) (erase-block (cadar state-preds)))
           ((eq 'on pred)       (erase-block (cadar state-preds)))
           ((eq 'holding pred)  (erase-block (cadar state-preds)))
           (t nil)
     )
   )
   (delete-domain-graphic-objects (cdr state-preds))
   )

)


;--------------------------------------------------------------------------

;; ADD-DOMAIN-GRAPHIC-OBJECTS adds objects to the domain window. This 
;; function is the complement of (delete-domain-graphic-objects). The 
;; argument to this function may contain predicates irrelevant to graphics, 
;; therefore the function must devise a method to parse relevant predicates
;; and add the appropriate objects to the domain window. 
;; 
; This function is yucky.  The state-preds list can contain the relations of 
; objects in an arbitrary order.  Thus the ON predicate could occur before
; the ON-TABLE of the supporting object (i.e. the list could say A is on B 
; before it says B is on the table.  The following routine draws every object 
; that it can in one pass through the outer loop.  It then starts over again
; with a list containing all undone predicates.
; This would be much more elegant with circular lists, but I don't want to 
; fool with them now.
	
(defun add-domain-graphic-objects (state-preds)
  (do ((good-a-list nil)
       (unchanged-a-list (flag-unchanged-objects state-preds))
       (incvar)
       (next-list state-preds incvar))
       ((null next-list))

    (setq incvar
     (do ((working-list next-list (cdr working-list))
          (stack-var nil))
	  ((null working-list) stack-var) ; return list of undone preds
;debug code    (format t "here's working-list ~A~%" working-list)
         (case (caar working-list)
           ('on-table (draw-on-table (car working-list))
                      (setq good-a-list (acons (get-ob working-list) t
						good-a-list)))
           ('holding (draw-in-hand (cadar working-list)))
           ('on (cond ((good-object-II-p (get-sup working-list) good-a-list)
			      (setq good-a-list (acons (get-ob working-list)
                                                               t  good-a-list))
                                     (draw-on-box (car working-list)))
                      (t (push (car working-list) stack-var))))
	 )                         ;  end case
     )
    )  				; end setq
  )
)

(defun flag-unchanged-objects (pred-list)
;reset all unchanged-flags
   (do ((count (1- (length *GRAPHICS-INFO*)) (1- count)))
	((minusp count))
        (setf (pos-data-unchanged-flag 
		(cdr (nth count *GRAPHICS-INFO*))) t)
   )
; now set to TRUE those that haven't changed.
   (dolist (pred pred-list)
;debug code        (format t "~%This is pred: ~A" pred)
	(cond ((or (eq (car pred) 'on-table)
		   (eq (car pred) 'on))
               (setf (pos-data-unchanged-flag
			(cdr (assoc (second pred) *GRAPHICS-INFO*)))
			nil)
	      )
	)
   )
)

;  the following access functions act on a list of predicates the first of
;  which should be an ON or an ON-TABLE predicate.  get-sub returns the object 
;  that is underneath in an ON predicate.  Get-ob returns the object that is
;  being put on the table.


(defun get-sup (preds)
        (caddar preds))

(defun get-ob (preds)
	(cadar preds))

(defun good-object-II-p (object a-list)
  (or (cdr (assoc object a-list))
      (pos-data-unchanged-flag (cdr (assoc object *GRAPHICS-INFO*))))
)

#|
(defun good-object-II-p (pred-list good-a-list)
   (let* ((pred-1 (car pred-list))
          (object (third pred-1))) ; get the object name
      (setq pred-list (cdr pred-list))
      (cond ((null pred-list) t) ; object is good
            ((and (member (car pred-1) '(on holding on-table)) 
                  (eq object (cadar pred-list)))
 				; then
 				         nil); bad object
            (t (good-object-II-p (cons pred-1 (cdr pred-list))))
      )
   )
)
|#

;--------------------------------------------------------------------------

;; DRAW-DOMAIN-FOREGROUND uses domain graphics parameters to draw the 
;; foremost plane of graphics -- drawn after the domain objects.
;;

 (defun draw-domain-foreground ()
         (pg-draw-line *DOMAIN-WINDOW* 5 *START-Y* (- *DOMAIN-WIDTH* 5) 
								*START-Y*) 
	(draw (get-x-point *HAND-DATA*) (get-y-point *HAND-DATA*) (cdr *HAND-DATA*))
;        (terpri) (princ "Paused") (read-char)
 )

(defun draw (X1 Y1 x-y-list)
 (unless (null x-y-list)
  (let ((X2 (+ X1 (get-x-point x-y-list)))
        (Y2 (+ Y1 (get-y-point x-y-list))))

        (pg-draw-line *DOMAIN-WINDOW* X1 Y1 X2 Y2)
	    (draw X2 Y2 (cdr x-y-list))
  )
 )
)


; ========================================================================
;  Drawing functions for boxes
; ========================================================================

; DRAW-ON-TABLE draws the box listed in the predicate on the table.
; Each box will have a predetermined table position 
(defun draw-on-table (pred)
    (let ((info-ob (cdr (assoc (last-el pred) *graphics-info*))))
        (draw-box-with-label (pos-data-rest-pos info-ob) 0 (last-el pred))
	(update (last-el pred) (pos-data-rest-pos info-ob) 0)
    )
)

; DRAW-ON-BOX draws an object on top of another one.
; It also updates the data on the upper box.

(defun draw-on-box (pred)
    (let ((support-ob (cdr (assoc (third pred) *graphics-info*)))
         ;(resting-ob (car (assoc (third pred) *graphics-info*)))
	 )
         (draw-box-with-label (pos-data-x-pos support-ob)
                              (1+ (pos-data-y-pos support-ob))
                              (cadr pred))
 	 (update (cadr pred) (pos-data-x-pos support-ob)
			     (1+ (pos-data-y-pos support-ob)))
    )
)

; DRAW-IN-HAND will draw a box in the hand of the robot.
; No update is nescessary.

(defun draw-in-hand (name)
         (pg-with-window *DOMAIN-WINDOW*
            (pg-frame-rect
                  *HAND-LOC-X*
                  (- *HAND-LOC-Y* *BLOCK-HEIGHT*)
                  (+ *HAND-LOC-X* *BLOCK-WIDTH*)
                  *HAND-LOC-Y*)
	 )
         (pg-write-text *DOMAIN-WINDOW*
                  (+ *HAND-LOC-X* 5)
                  (- *HAND-LOC-Y* 5)
                  (symbol-name name)
	 )
	 (setf (pos-data-in-arm-p (cdr (assoc name *GRAPHICS-INFO*))) t)
)


; UPDATE changes the location var of the block.
; 
; 
(defun update (keyname X Y)
    (let ((structure (cdr (assoc keyname *GRAPHICS-INFO*))))
     (setf (pos-data-x-pos structure) X)
     (setf (pos-data-y-pos structure) Y)
     )
)


; DRAW-BOX-WITH-LABEL will draw a little box and then write the print
; name of the object inside it.
; location is a small integer indicating the blocks position in block-space.
; name is a symbol whose print name is going to be the label.
; The block-org-? are the lower left-hand points and
; the corner-? are the upper right-hand points.

(defun draw-box-with-label (x-pos y-pos name)
    (let* ((block-org-x (calc-x-from-int x-pos))
           (block-org-y (calc-y-from-int y-pos))
           (corner-x (+ block-org-x *BLOCK-WIDTH*))
	   (corner-y (- block-org-y *BLOCK-HEIGHT*))
	  )

         (pg-with-window *DOMAIN-WINDOW*
	    (pg-frame-rect 
                  block-org-x
		  corner-y
		  corner-x
		  block-org-y)
	 )
	 (pg-write-text *DOMAIN-WINDOW*
		  (+ block-org-x 5)
		  (- block-org-y 5)
		  (symbol-name name)
	 )
    )
)
; CALC-X-FROM-INT will return a pixel value give a grid value.
(defun calc-x-from-int (int)
  (+ (* (+ *BLOCK-WIDTH* *BLOCK-SEPARATION-X*) int) *START-X*)
)
;  CALC-Y-FROM-INT will return a pixel value give a grid value.
(defun calc-y-from-int (int)
  (- *START-Y* (* (+ *BLOCK-HEIGHT* *BLOCK-SEPARATION-Y*) int))
)


(defun erase-block (name)
   (let (x-pos y-pos block-org-x block-org-y)   
        (cond ((pos-data-in-arm-p (cdr (assoc name *GRAPHICS-INFO*)))
                 (setq block-org-x *HAND-LOC-X* block-org-y *HAND-LOC-Y*)
		 (setf (pos-data-in-arm-p (cdr (assoc name *GRAPHICS-INFO*))) nil)
	      )
              (t (setq x-pos
			(pos-data-x-pos (cdr (assoc name *GRAPHICS-INFO*)))
		       y-pos 
                        (pos-data-y-pos (cdr (assoc name *GRAPHICS-INFO*))))
                 (setq block-org-x (calc-x-from-int x-pos))
                 (setq block-org-y (calc-y-from-int y-pos)))
	)
        (pg-with-window *DOMAIN-WINDOW*
                (pg-erase-rect block-org-x
                        (- block-org-y *BLOCK-HEIGHT*)
			(+ block-org-x *BLOCK-WIDTH*)
			block-org-y)
	)
   )
)

(provide 'pg-state)




