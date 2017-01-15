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
; File: gridgraph.lisp        Version: 1-2            Created: 3/9/88
;
; Locked By:    nobody                               Modified: 4/2/88
;
; Purpose:  Domain graphics routines for gridworld.
;
; Things to be done:
;
; (1)  Fix parameter determination to add extra squares when space is there
; (2)  Document interface to prodigy
;=========================================================================



     
(proclaim '(special *X-DIM*     *Y-DIM*      *Z-DIM*      *X-OR* 
		    *Y-OR*      *WIDTH*      *SPACES*      *START-STATE*
		    *DOMAIN-COLS*       *DOMAIN-LINES*     *DOMAIN-WINDOW*
		    *DOMAIN-CHAR-WIDTH*   *DOMAIN-CHAR-HEIGHT* 
		    *DOMAIN-STATE*      *DOMAIN-NODE*      *NODE-MSG-X* 
		    *NODE-MSG-Y*      *STATE-MSG-X*      *STATE-MSG-Y*))


;=========================================================================
;                      Domain  Dependent Functions 
;=========================================================================

  
  (defun reset-domain-graphics-parameters ()
    (psetq 
     	   *X-DIM*       NIL     ; number of slots along x-axis
	   *Y-DIM*       NIL     ; number of slots along y-axis
	   *Z-DIM*       NIL     ; number of slots along z-axiz
	   *X-OR*        NIL     ; x-origin   
	   *Y-OR*        NIL     ; y-origin   
	   *WIDTH*       NIL     ; maximum width of an object
	   *SPACES*      NIL
	   *NODE-MSG-X*  NIL
	   *NODE-MSG-Y*  NIL
	   *STATE-MSG-X* NIL
	   *STATE-MSG-Y* NIL
	   *DOMAIN-COLS* NIL
	   *DOMAIN-LINES* NIL
	   *DOMAIN-HEIGHT* NIL
	   *DOMAIN-WIDTH* NIL	   
	   *DOMAIN-CHAR-HEIGHT* NIL
	   *DOMAIN-CHAR-WIDTH* NIL ))

  ;; do not initialize *start-state* or *domain-window*
  
  (reset-domain-graphics-parameters)

;-------------------------------------------------------------------------


  (defun determine-domain-graphics-parameters (problem)
    (let ((set-x (null *X-DIM*))
	  (set-y (null *Y-DIM*))
	  (set-z (null *Z-DIM*))
	  (set-width (null *WIDTH*))
	  (set-x-or (null *X-OR*))
	  (set-y-or (null *Y-OR*)))
      (setq *DOMAIN-CHAR-HEIGHT* (pg-text-height *DOMAIN-WINDOW* "I"))
      (setq *DOMAIN-HEIGHT* (pg-window-height *DOMAIN-WINDOW*))
      (setq *DOMAIN-LINES* (truncate *DOMAIN-HEIGHT* *DOMAIN-CHAR-HEIGHT*))
      (setq *DOMAIN-CHAR-WIDTH* (pg-text-width *DOMAIN-WINDOW* "A"))
      (setq *DOMAIN-WIDTH* (pg-window-width *DOMAIN-WINDOW*))
      (setq *DOMAIN-COLS* (truncate *DOMAIN-WIDTH* *DOMAIN-CHAR-WIDTH*))
      (if set-x (setq *X-DIM* 0))
      (if set-y (setq *Y-DIM* 0))
      (if set-z (setq *Z-DIM* 0))
      (if set-width (setq *WIDTH* 0))
      (if set-x-or (setq *X-OR* 0))
      (if set-y-or (setq *Y-OR* 0))
      (set-parameters problem set-x set-y set-z set-width)
      (setq *SPACES* (create-spaces *WIDTH*))
      (if set-x-or (update-x-or))
      (if set-y-or (update-y-or))
      (print-graphics-params)
      (setq *NODE-MSG-X*  (x-to-pixels *X-OR*))
      (setq *NODE-MSG-Y*  (y-to-pixels 
				(1+ (calc-position *Y-OR* *Y-DIM* *Z-DIM*))))
      (setq *STATE-MSG-X* (x-to-pixels (+ *X-OR* 15)))
      (setq *STATE-MSG-Y* (y-to-pixels 
				(1+ (calc-position *Y-OR* *Y-DIM* *Z-DIM*))))))
  
  
  
  (defun set-parameters (state set-x set-y set-z set-width)
    (cond ((or (null state) (not (listp state))))
	  ((eq 'at (car state))
	   (update-params state set-x set-y set-z set-width))
	  (t (set-parameters (car state) set-x set-y set-z set-width)
	     (set-parameters (cdr state) set-x set-y set-z set-width))))
  
  
  (defun update-params (at set-x set-y set-z set-width)
    (let ((loc (get-location at)))
      (if set-width (update-width (cadr at)))
      (if set-x (update-x-dim (+ 2 (max-x-coor loc))))
      (if set-y (update-y-dim (+ 2 (max-y-coor loc))))
      (if set-z (update-z-dim (+ 1 (max-z-coor loc))))))
  
  
  (defun print-graphics-params ()
      (terpri) (format t "*X-DIM*: ~A" *X-DIM*)
      (terpri) (format t "*Y-DIM*: ~A" *Y-DIM*)
      (terpri) (format t "*Z-DIM*: ~A" *Z-DIM*)
      (terpri) (format t "*WIDTH*: ~A" *WIDTH*)
      (terpri) (format t "*X-OR*: ~A" *X-OR*)
      (terpri) (format t "*Y-OR*: ~A" *Y-OR*))
  
;-------------------------------------------------------------------------

  (defun draw-domain-background ())


;-------------------------------------------------------------------------
  
  (defun delete-domain-graphic-objects (idels)
    (cond ((null idels))
	  ((not (eq 'at (get-pred (car idels))))
	   (delete-domain-graphic-objects (cdr idels)))
	  (t (display-words *SPACES*  (get-location (car idels)))
	     (delete-domain-graphic-objects (cdr idels)))))
  
  
  (defun get-pred (pred) (car pred))
  (defun get-location (at)   (caddr at))
  
  
;-------------------------------------------------------------------------
  
  (defun add-domain-graphic-objects (iadds)
    (cond ((null iadds))
	  ((not (eq 'at (get-pred (car iadds))))
	   (add-domain-graphic-objects (cdr iadds)))
	  (t (display-words (princ-to-string (get-name (car iadds)))
			    (get-location (car iadds)))
	     (add-domain-graphic-objects (cdr iadds)))))
  
  
  (defun get-name (at)  (cadr at))


;-------------------------------------------------------------------------

  (defun draw-domain-foreground ()
    ;; prints out the grid for gridworld.  The grid is drawn last because the 
    ;; lines are erased when the objects are added. 
    (draw-vertical *X-OR*  *Y-OR*  *X-DIM*  *Y-DIM* *Z-DIM* *WIDTH*)
    (draw-horizontal *X-OR* *Y-OR* *X-DIM* *Y-DIM* *Z-DIM* *WIDTH*)
    (pg-refresh-window *DOMAIN-WINDOW*))
  
  
;=========================================================================
;                      Miscellaneous Functions
;=========================================================================

  
  (defun display-words (word location)
    (display-word-check
     word
     (min-x-coor location)(max-x-coor location)
     (min-y-coor location)(max-y-coor location)
     (min-z-coor location)(max-z-coor location)))
  
  

  (defun display-word-check (word min-x max-x min-y max-y min-z max-z)
    (cond ((or (> min-x max-x)
	       (> min-y max-y)
	       (> min-z max-z))
	   (error "First coordinate of location is greater than second!"))
	  (t (display-word-x word min-x max-x min-y max-y min-z max-z))))
  
  
;-------------------------------------------------------------------------

  
  (defun display-word-x (word min-x max-x min-y max-y min-z max-z)
    (display-word-y word min-x min-y max-y min-z max-z)
    (cond ((eq min-x max-x))
	  (t (display-word-x word (1+ min-x) max-x min-y max-y min-z max-z))))
  
  
  (defun display-word-y (word x min-y max-y min-z max-z)
    (display-word-z word x min-y min-z max-z)
    (cond ((eq min-y max-y))
	  (t (display-word-y word x (1+ min-y) max-y min-z max-z))))
  
  
  (defun display-word-z (word x y min-z max-z)
    (display-word word x y min-z)
    (cond ((eq min-z max-z))
	  (t (display-word-z word x y (1+ min-z) max-z))))
  
  
  (defun display-word (word x y z)
    (pg-write-text *DOMAIN-WINDOW*
		   (x-to-pixels (calc-x-coor x (length word)))
		   (y-to-pixels (calc-y-coor y z)) word)
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
  
  (defun update-width (word)
    (let ((word-size (length (princ-to-string word))))
      (cond ((> word-size *WIDTH*) (setq *WIDTH* word-size)))))
  
  (defun update-x-dim (coor)
    (cond ((> coor *X-DIM*)(setq *X-DIM* coor))))
  
  (defun update-y-dim (coor)
    (cond ((> coor *Y-DIM*)(setq *Y-DIM* coor))))
  
  (defun update-z-dim (coor)
    (cond ((> coor *Z-DIM*)(setq *Z-DIM* coor))))
  
  
;-------------------------------------------------------------------------

; These functions calculate the amount of extra white space surrounding
; the grid along the x and y axes.  The origin is set to be half of this value
; in order to center the grid on the screen.
;

  (defun update-x-or ()
    (let ((x-size (- *DOMAIN-COLS* (calc-length *X-DIM* *WIDTH*))))  ;
      (cond ((< x-size 0)
	     (error "Default values for *X-DIM* and *WIDTH* too large."))
	    (t (setq *X-OR* (truncate x-size 2))))))
  
  
  (defun update-y-or ()
    (let ((y-size (- *DOMAIN-LINES* 2 (calc-length *Y-DIM* *Z-DIM*))))  
      (cond ((< y-size 0)
	     (error "Default values for *Y-DIM* and *Z-DIM* too large."))
	    (t (setq *Y-OR* (truncate y-size 2))))))
  
;=========================================================================
  

(provide 'pg-domain)
