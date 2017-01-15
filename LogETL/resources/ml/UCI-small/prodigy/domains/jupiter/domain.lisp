#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Santiago Rementeria

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


; Operators for PRODIGY/SC-MINIJUP domain

(setq *OPERATORS* 
'(
	
   (MOVE 
    (params (<pxi> <pyi> <px> <py>))
    (preconds (and (at-prodigy <pxi> <pyi>)
		   (at <object> <oxi> <oyi>)
		   (radius prodigy <rad-prod>)
		   (radius <object> <rad-obj>)
		   (not-overlapping <pxi> <pyi> <oxi> <oyi>
				    <rad-prod> <rad-obj>)
		   (linear-viscosity <lin-visc>)
		   (timestep <t-step>)
		   (upd-per-cycle <upd-cycle>)
		   (theo-f-ap-time <th-f-time>)
		   (force-to-move <px> <py> <pxi> <pyi> <rad-prod> <lin-visc>
				  <t-step> <upd-cycle> <th-f-time> <fx> <fy>)))
    (effects ((del (at-prodigy <pxi> <pyi>))
	      (add (at-prodigy <px> <py>)))))
   
   

   (BUMP-OBJECT
    (params (<object> <ox> <oy> <pxi> <pyi>))
    (preconds (and (at <object> <oxi> <oyi>)
		   (radius prodigy <rad-prod>)
		   (radius <object> <rad-obj>)
		   (point-before-bumping <oxi> <oyi> <ox> <oy>
					 <rad-prod> <rad-obj>
					 <pxi> <pyi>)
		   (at-prodigy <pxi> <pyi>)
		   (linear-viscosity <lin-visc>)
		   (mass prodigy <mass-prod>)
		   (mass <object> <mass-obj>)
		   (modul-elast prodigy <elast-prod>)
		   (modul-elast <object> <elast-obj>)
		   (coeff-resti prodigy <rest-prod>)
		   (coeff-resti <object> <rest-obj>)
		   (timestep <t-step>)
		   (upd-per-cycle <upd-cycle>)
		   (theo-f-ap-time <th-f-time>)
		   (force-to-bump <pxi> <pyi> <oxi> <oyi> <ox> <oy> <rad-prod>
		     <rad-obj> <lin-visc> <t-step> <upd-cycle> <th-f-time>
		     <mass-prod> <mass-obj> <elast-prod> <elast-obj> 
		     <rest-prod> <rest-obj> <px> <py> <fx> <fy>)))
    (effects ((del (at <object> <oxi> <oyi>))
              (del (at-prodigy <pxi> <pyi>)) 
	      (add (at-prodigy <px> <py>))
	      (add (at <object> <ox> <oy>)))))
   
 ))

; Inference Rules for PRODIGY/MINIJUP domain

(setq *INFERENCE-RULES* nil)





