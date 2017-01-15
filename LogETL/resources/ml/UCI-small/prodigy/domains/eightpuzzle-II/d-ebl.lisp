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

; ***** EIGHT PUZZLE June 19, 1989 Etzi.

; The static preds need to be first.

(setq *OPERATORS* '(


(blank-LEFT
     (params      (<Lx> <Ll> <Ly>))
     (preconds   (and
		   (left-of <Lx> <Ll>) ;fails if <x> is on left edge.
		   (at <Lx> blank)
		   (at <Ll> <Ly>) ;y is to the left of x.
		      ))
     (effects   ((del (at <Lx> blank))
                 (del (at <Ll> <Ly>))
		 (add (at <Lx> <Ly>))
		 (add (at <Ll> blank)))))

(blank-RIGHT
     (params      (<Rx> <Rl> <Ry>))
     (preconds   (and
		   (right-of <Rx> <Rl>) ;fails if <x> is on right edge.
		   (at <Rx> blank)
		   (at <Rl> <Ry>) ;y is to the right of x.
		      ))
     (effects   ((del (at <Rx> blank))
                 (del (at <Rl> <Ry>))
		 (add (at <Rx> <Ry>))
		 (add (at <Rl> blank)))))

(blank-UP
     (params      (<Ux> <Ul> <Uy>))
     (preconds   (and
		   (up-of <Ux> <Ul>) ;fails if <x> is on right edge.
		   (at <Ux> blank)
		   (at <Ul> <Uy>) ;y is to the right of x.
		      ))
     (effects   ((del (at <Ux> blank))
                 (del (at <Ul> <Uy>))
		 (add (at <Ux> <Uy>))
		 (add (at <Ul> blank)))))

(blank-DOWN
     (params      (<Dx> <Dl> <Dy>))
     (preconds   (and
		   (down-of <Dx> <Dl>) 
		   (at <Dx> blank) 
		   (at <Dl> <Dy>) 
		      ))
     (effects   ((del (at <Dx> blank))
                 (del (at <Dl> <Dy>))
		 (add (at <Dx> <Dy>))
		 (add (at <Dl> blank)))))


))

(setq *INFERENCE-RULES* nil)
