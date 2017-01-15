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



;=========================================================================
; File: schedgraph.lisp        Version: 1-2            Created: 1/23/89
;
; Locked By:    nobody                               Modified: 
;
; Purpose:  Domain graphics routines for schedworld.
;
; Things to be done:
;
;=========================================================================



(proclaim '(special *X-DIM* *Y-DIM* *Z-DIM* *X-OR* *Y-OR* *WIDTH* 
                    *SPACES* *START-STATE* *DOMAIN-COLS* *DOMAIN-LINES* 
		    *DOMAIN-WINDOW* *DOMAIN-CHAR-WIDTH* *DOMAIN-CHAR-HEIGHT* 
		    *DOMAIN-STATE* *DOMAIN-NODE* *MACHINES* *END-TIME*
		    *NODE-MSG-X* *NODE-MSG-Y* *STATE-MSG-X* *STATE-MSG-Y*))



;=========================================================================
;                      Domain  Dependent Functions 
;=========================================================================


(defun reset-domain-graphics-parameters ()
    (psetq *NODE-MSG-X*  NIL 
	 *NODE-MSG-Y*  NIL
	 *STATE-MSG-X* NIL 
	 *STATE-MSG-Y* NIL
	 *DOMAIN-COLS* NIL
	 *DOMAIN-LINES* NIL
	 *DOMAIN-HEIGHT* NIL
	 *DOMAIN-WIDTH* NIL	   
	 *DOMAIN-CHAR-HEIGHT* NIL
	 *DOMAIN-CHAR-WIDTH* NIL
	 *SPACES*      NIL
	 *X-DIM*       NIL     ; number of slots along x-axis
	 *Y-DIM*       NIL     ; number of slots along y-axis
	 *Z-DIM*       NIL     ; number of slots along z-axiz
	 *X-OR*        NIL     ; x-origin   
	 *Y-OR*        NIL     ; y-origin   
	 *WIDTH*       NIL))     ; maximum width of an object


(reset-domain-graphics-parameters)

;-------------------------------------------------------------------------


(defun determine-domain-graphics-parameters (problem)
  (setq *WIDTH* 3)
  (setq *MACHINES* (find-machines *operators*))
  (setq *Y-DIM* (length *machines*))
  (setq *X-DIM* *END-TIME*)
  (setq *Z-DIM* 1)
  (setq *SPACES* (create-spaces *WIDTH*))
  (setq *DOMAIN-CHAR-HEIGHT* (pg-text-height *DOMAIN-WINDOW* "A"))
  (setq *DOMAIN-HEIGHT* (pg-window-height *DOMAIN-WINDOW*))
  (setq *DOMAIN-LINES* (truncate *DOMAIN-HEIGHT* *DOMAIN-CHAR-HEIGHT*))
  (setq *DOMAIN-CHAR-WIDTH* (pg-text-width *DOMAIN-WINDOW* "A"))
  (setq *DOMAIN-WIDTH* (pg-window-width *DOMAIN-WINDOW*))
  (setq *DOMAIN-COLS* (truncate *DOMAIN-WIDTH* *DOMAIN-CHAR-WIDTH*))
  (setq *X-OR* (update-x-or))
  (setq *Y-OR* (update-y-or))
  (setq *NODE-MSG-X*  (x-to-pixels *X-OR*))
  (setq *NODE-MSG-Y*  (y-to-pixels (1+ (calc-position *Y-OR* *Y-DIM* *Z-DIM*))))
  (setq *STATE-MSG-X* (x-to-pixels (+ *X-OR* 15)))
  (setq *STATE-MSG-Y* (y-to-pixels (1+ (calc-position *Y-OR* *Y-DIM* *Z-DIM*)))))

(defun find-machines (operators)
  (cond ((null operators) nil)
	(t (cons (find-scheduled (get (caar operators) 'effects))
		 (find-machines (cdr operators))))))

(defun find-scheduled (preds)
  (cond ((null preds)(error "No scheduled predicate in adds"))
	((eq (caadar preds) 'scheduled)
	 (caddr (cadar preds)))
	(t (find-scheduled (cdr preds)))))



;(defun print-graphics-params ()
;    (terpri) (format t "*X-DIM*: ~A" *X-DIM*)
;    (terpri) (format t "*Y-DIM*: ~A" *Y-DIM*)
;    (terpri) (format t "*Z-DIM*: ~A" *Z-DIM*)
;    (terpri) (format t "*WIDTH*: ~A" *WIDTH*)
;    (terpri) (format t "*X-OR*: ~A" *X-OR*)
;    (terpri) (format t "*Y-OR*: ~A" *Y-OR*))

;-------------------------------------------------------------------------

(defun draw-domain-background ())


;-------------------------------------------------------------------------

(defun delete-domain-graphic-objects (idels)())



;-------------------------------------------------------------------------

(defun add-domain-graphic-objects (iadds)
  (cond ((null iadds))
	((not (eq 'scheduled (get-pred (car iadds))))
	 (add-domain-graphic-objects (cdr iadds)))
	(t (display-words (symbol-name (get-name (car iadds)))
			  (get-machine (car iadds))
			  (get-time (car iadds)))
	   (add-domain-graphic-objects (cdr iadds)))))


(defun get-name (at)  (cadr at))
(defun get-pred (pred) (car pred))
(defun get-machine (pred)   (caddr pred))
(defun get-time (pred) (1- (cadddr pred)))

;-------------------------------------------------------------------------

(defun draw-domain-foreground ()
;; prints out the grid for gridworld.  The grid is drawn last because the 
;; lines are erased when the objects are added. 
  (draw-vertical *X-OR*  *Y-OR*  *X-DIM*  *Y-DIM* *Z-DIM* *WIDTH*)
  (draw-horizontal *X-OR* *Y-OR* *X-DIM* *Y-DIM* *Z-DIM* *WIDTH*)
  (plot-machines *MACHINES*)
  (plot-times 1)
  (pg-refresh-window *DOMAIN-WINDOW*))

(defun plot-machines (machines)
  (cond ((null machines))
	(t (pg-write-text *DOMAIN-WINDOW* 
			 (x-to-pixels 
			  (- *X-OR* 1 (length (symbol-name (car machines)))))
			 (y-to-pixels (calc-y-coor
				       (machine-index (car machines) *machines* 0)
				       0))
			 (symbol-name (car machines)))
	   (plot-machines (cdr machines)))))

(defun plot-times (time)
  (cond ((> time *end-time*))
	(t (display-word (princ-to-string time)
			 (length *machines*)
			 (1- time))
	   (plot-times (1+ time)))))

;=========================================================================
;                      Miscellaneous Functions
;=========================================================================


(defun display-words (word machine time)
  (display-word
   word
   (machine-index machine *machines* 0)
   time))

(defun machine-index (machine machines index)
  (cond ((null machines)(error "Machine is not in the list of machines."))
	((eq machine (car machines)) index)
	(t (machine-index machine (cdr machines) (1+ index)))))



;-------------------------------------------------------------------------

(defun display-word (word machine time)
  (pg-write-text *DOMAIN-WINDOW* (x-to-pixels (calc-x-coor time (length word)))
			  (y-to-pixels (calc-y-coor machine 0)) word)
  (pg-refresh-window *DOMAIN-WINDOW*))

;-------------------------------------------------------------------------


(defun calc-x-coor (x length)
   (+ 1 (truncate (- *WIDTH* length) 2) *X-OR* x (* x *WIDTH*)))

  (defun calc-y-coor (y z)
    (let ((real-y (- *Y-DIM* y 1)))
      (+ (- *Z-DIM* z) *Y-OR* real-y (* real-y *Z-DIM*))))
  
;-------------------------------------------------------------------------
  
;; CALC-LENGTH returns the total number of characters that need to be 
;; printed.  Size = the size of a column or row (i.e. width or height).
;; Dimension = the number of columns or rows.  "(1+ dimension)" is the
;; number of markers needed to separate the columns or rows.

  
  
  (defun calc-length (dimension size)
    (+ (1+ dimension) (* dimension size)))
  
  
;-------------------------------------------------------------------------
  
  (defun calc-position (offset dimension size)
    (+ offset dimension (* dimension size)))


;-------------------------------------------------------------------------
  
  (defun draw-vertical (x-or y-or x-dim y-dim z-dim size)
    (let ((y-length (calc-length y-dim z-dim)))
      (cond ((< x-dim 0))
	    (t (draw-v-line (calc-position x-or x-dim size) y-or y-length)
	       (draw-vertical x-or y-or (1- x-dim) y-dim z-dim size)))))
  
  (defun draw-v-line (x-or y-or y-length)
    (pg-draw-line *DOMAIN-WINDOW* (x-to-pixels x-or) (y-to-pixels y-or) 
		  (x-to-pixels x-or) 
		  (- (y-to-pixels (+ y-or y-length)) *DOMAIN-CHAR-HEIGHT*)))
  
;-------------------------------------------------------------------------
  
  
  (defun draw-horizontal (x-or y-or x-dim y-dim z-dim size)
    (let ((x-length (calc-length x-dim size)))
      (cond ((< y-dim 0))
	    (t (draw-h-line (calc-position y-or y-dim z-dim) x-or x-length)
	       (draw-horizontal x-or y-or x-dim (1- y-dim) z-dim size)))))
  
  
  (defun draw-h-line (y-or x-or x-length)
    (pg-draw-line *DOMAIN-WINDOW* (x-to-pixels x-or) (y-to-pixels y-or) 
		  (- (x-to-pixels (+ x-or x-length)) *DOMAIN-CHAR-WIDTH*)
		  (y-to-pixels y-or)))
  
  
  (defun x-to-pixels (x)
    (* x *DOMAIN-CHAR-WIDTH*))
  
  
  (defun y-to-pixels (y) 
    (* y *DOMAIN-CHAR-HEIGHT*))
  
  
;-------------------------------------------------------------------------

 (defun create-spaces (width)
    (make-string width :initial-element #\space))


;-------------------------------------------------------------------------

; These functions calculate the amount of extra white space surrounding
; the grid along the x and y axes.  The origin is set to be half of this value
; in order to center the grid on the screen.
;

(defun update-x-or ()
    (let ((x-size (- *DOMAIN-COLS* (calc-length *X-DIM* *WIDTH*))))  ;
	 (cond ((< x-size 0)
		(error "Default values for *X-DIM* and *WIDTH* too large."))
	       (t (setq *X-OR* (max (truncate x-size 2)
				    (1+ (machine-name-length *MACHINES*))))))))

(defun update-y-or ()
    (let ((y-size (- *DOMAIN-LINES* 2 (calc-length *Y-DIM* *Z-DIM*))))  
	 (cond ((< y-size 0)
		(error "Default values for *Y-DIM* and *Z-DIM* too large."))
	       (t (setq *Y-OR* (truncate y-size 2) )))))


(defun machine-name-length (machines)
  (cond ((null machines) 0)
	(t (max (length (symbol-name (car machines))) 
		(machine-name-length (cdr machines))))))
