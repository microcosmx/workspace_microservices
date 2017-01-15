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
    (setq *STATE-MSG-X* 35))
    (setq *STATE-MSG-Y* 30))


(defun draw-domain-background ())


(defun delete-domain-graphic-objects (state-predicates))


(defun add-domain-graphic-objects (state-predicates))

(defun draw-domain-foreground ())
