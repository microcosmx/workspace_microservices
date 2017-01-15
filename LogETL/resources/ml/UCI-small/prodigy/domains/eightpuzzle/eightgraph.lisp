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


; general domain graphics routines....
(proclaim
  '(special *BOX-HEIGHT* *BOX-WIDTH* *ORGIN-X* *ORGIN-Y* *DOMAIN-HEIGHT*
	    *DOMAIN-WIDTH*
   ))

(defun reset-domain-graphics-parameters ()
  (psetq *NODE-MSG-X*  NIL 
	 *NODE-MSG-Y*  NIL
	 *STATE-MSG-X* NIL 
	 *STATE-MSG-Y* NIL
	 *WIDTH*       NIL))     ; maximum width of an object


(defun determine-domain-graphics-parameters (problem)
  (let ((*WIDTH* 0))
    (setq *NODE-MSG-X*  10)
    (setq *NODE-MSG-Y*  30)
    (setq *STATE-MSG-X* 10))
    (setq *STATE-MSG-Y* 40)
    (setq *DOMAIN-HEIGHT* (pg-window-height *DOMAIN-WINDOW*)
	  *DOMAIN-WIDTH* (pg-window-width *DOMAIN-WINDOW*)
          *ORGIN-X* (truncate *DOMAIN-WIDTH* 5) ; 20%
	  *ORGIN-Y* (- *DOMAIN-HEIGHT* 20)
	  *BOX-HEIGHT* (truncate  *DOMAIN-HEIGHT* 1.25); 80%
	  *BOX-WIDTH*  (truncate *DOMAIN-WIDTH* 1.666); 60%
    )
	  
)


(defun draw-domain-background ())


(defun delete-domain-graphic-objects (state-preds)
      (unless (null state-preds)
	 (when (eq 'place (caar state-preds))
		(erase-square (cdar state-preds)))
         (delete-domain-graphic-objects (cdr state-preds))
      )
)

(defun erase-square (place)
   (let*  ((temp1 (truncate *BOX-WIDTH* 3))
	   (temp2 (truncate *BOX-HEIGHT* 3))
	   (lft (+ 1 *ORGIN-X* (* (- (car place) 1) temp1)))
           (top (- *ORGIN-Y* -1 (* (- 4 (cadr place)) temp2)))
	   (rgt (+ lft temp1 -2))
	   (bot (+ top temp2 -2)))

	(pg-with-window *DOMAIN-WINDOW*
	     (pg-erase-rect lft top rgt bot))
   )
)



(defun add-domain-graphic-objects (state-preds)
     (unless (null state-preds)
       (if (eq 'place (caar state-preds)) (draw-number (cdar state-preds)))
	(add-domain-graphic-objects (cdr state-preds))
     )
)

(defun draw-number (place)
   (let* ((half-sqr-wdth (truncate *BOX-WIDTH* 6))
	  (half-sqr-hght (truncate *BOX-HEIGHT* 6))
	  (x-pos (+ *ORGIN-X* (* (- (* (car place) 2) 1) half-sqr-wdth)))
	  (y-pos (- *ORGIN-Y* (* (- (* (- 4 (cadr place)) 2) 1) half-sqr-hght))))

	(pg-write-text 	*DOMAIN-WINDOW*
			x-pos
			y-pos
			(string (digit-char (caddr place))))))

       

(defun draw-domain-foreground ()
   (pg-with-window *DOMAIN-WINDOW*
	(pg-frame-rect *ORGIN-X* (- *ORGIN-Y* *BOX-HEIGHT*)
				  (+ *ORGIN-X* *BOX-WIDTH*) *ORGIN-Y*)
   )
; now draw the cross hatching
;  p1--->|   |
;     ---|---|---
;        |   |
;     ---|---|---
;        |   |
;  the points start at p1 and go clockwise

   (let*  ((temp1 (truncate *BOX-WIDTH* 3))
	   (temp2 (truncate *BOX-HEIGHT* 3))
	   (x1 (+ temp1 *ORGIN-X*))
	   (y1 (- *ORGIN-Y* *BOX-HEIGHT*))
	   (x2 (+ (* temp1 2) *ORGIN-X*))
	   (y2 y1)
	   (x3 (+ *ORGIN-X* *BOX-WIDTH*))
	   (y3 (- *ORGIN-Y* temp2))
	   (x4 x3)
	   (y4 (- *ORGIN-Y*(* temp2 2)))
	   (x5 x2)
	   (y5 *ORGIN-Y*)
	   (x6 x1)
	   (y6 y5)
	   (x7 *ORGIN-X*)
	   (y7 y4)
	   (x8 x7)
	   (y8 y3)
	  )

	(pg-draw-line *DOMAIN-WINDOW* x1 y1 x6 y6)
        (pg-draw-line *DOMAIN-WINDOW* x2 y2 x5 y5)
	(pg-draw-line *DOMAIN-WINDOW* x3 y3 x8 y8)
	(pg-draw-line *DOMAIN-WINDOW* x4 y4 x7 y7)
   )
;   (print "Paused") (read-char) 
)


