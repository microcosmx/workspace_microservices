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



(setq *SCR-NODE-SELECT-RULES* nil)
(setq *SCR-GOAL-SELECT-RULES*
      '(
        (SELECT-FIRST-GOAL
          (lhs (and (current-node <node>)
                    (not-top-level-node <node>)
                    (primary-candidate-goal <node> <goal>)))
           (rhs (select goal <goal>)))
        ))

(setq *SCR-OP-SELECT-RULES* nil)
(setq *SCR-BINDINGS-SELECT-RULES* 
 '(

   (DO-NOT-MOVE-IF-NOT-NECESSARY
    (lhs (and (current-node <node>)
	      (current-goal <node> (at <object-1> <o1x> <o1y>))
	      (current-op <node> BUMP-OBJECT)
	      (known <node>
		     (and (at-prodigy <pxi> <pyi>)
			  (at <object-1> <o1xi> <o1yi>)
			  (radius prodigy <rad-prod>)
			  (radius <object-1> <rad-obj-1>)
			  (not-overlapping <pxi> <pyi> <o1xi> <o1yi>
					   <rad-prod> <rad-obj-1>)
			  (aligned <pxi> <pyi> <o1xi> <o1yi> <o1x> <o1y>)
			  (ordered <pxi> <pyi> <o1xi> <o1yi> <o1x> <o1y>)
			  (apart-enough-from-each-other <pxi> <pyi> <o1xi> 
							<o1yi> <rad-prod>
							<rad-obj-1>)
			  (forall (<object-2>) (at <object-2> <o2xi> <o2yi>)
				  (and
				   (radius <object-2> <rad-obj-2>)
				   (~ (path-blocked-by-object 
				       <o2xi> <o2yi> <rad-obj-2> <o1xi> <o1yi>
				       <rad-obj-1> <o1x> <o1y>))))))))
    (rhs  (select bindings (<object-1> <o1x> <o1y> <pxi> <pyi>))))


   (MAKE-ONE-OBJECT-AVOID-ANOTHER-BY-PRODIGY-BUMPING-THE-FIRST-ONE
    (lhs (and (current-node <node>)
	      (current-goal <node> (at <object-1> <o1x> <o1y>))
	      (current-op <node> BUMP-OBJECT)
	      (known <node>
		     (and (at-prodigy <pxi> <pyi>)
			  (at <object-1> <o1xi> <o1yi>)
			  (at <object-2> <o2xi> <o2yi>)
			  (radius prodigy <rad-prod>)
			  (radius <object-1> <rad-obj-1>)
			  (radius <object-2> <rad-obj-2>)
			  (or (path-blocked-by-object <o2xi> <o2yi> <rad-obj-2>
						      <o1xi> <o1yi> <rad-obj-1>
						      <o1x> <o1y>)
			      (path-blocked-by-object <o2xi> <o2yi> <rad-obl-2>
						      <pxi> <pyi> <rad-prod>
						      <o1xi> <o1yi>))
			  (point-to-avoid-object <o2xi> <o2yi>
						 <o1xi> <o1yi> <o1x> <o1y>
						 <rad-obj-1> <rad-obj-2> 
						 <x-to-move> <y-to-move>)
			  (point-before-bumping <o1xi> <o1yi> <x-to-move>
						<y-to-move> <rad-prod>
						<rad-obj-1> <px> <py>)))))
    (rhs  (select bindings (<object-1> <x-to-move> <y-to-move> <px> <py>))))



   (MOVE-STRAIGHT-IF-FREE-PATH
    (lhs (and (current-node <node>)
	      (current-goal <node> (at-prodigy <px> <py>))
	      (current-op <node> MOVE)
	      (known <node>
		     (and (at-prodigy <pxi> <pyi>)
			  (radius prodigy <rad-prod>)
 			  (forall (<object>) (at <object> <oxi> <oyi>)
				  (and 
				   (radius <object> <rad-obj>)
				   (~ (path-blocked-by-object 
				       <oxi> <oyi> <rad-obj> <pxi> <pyi> 
				       <rad-prod> <px> <py>))))))))
    (rhs  (select bindings (<pxi> <pyi> <px> <py>))))


   (AVOID-OBJECT-BY-ITSELF-IF-PATH-BLOCKED
    (lhs (and (current-node <node>)
	      (current-goal <node> (at-prodigy <px> <py>))
	      (current-op <node> MOVE)
	      (known <node>
		     (and (at-prodigy <pxi> <pyi>)
			  (at <object> <oxi> <oyi>)
			  (radius prodigy <rad-prod>)
			  (radius <object> <rad-obj>)
			  (path-blocked-by-object <oxi> <oyi> <rad-obj> 
						  <pxi> <pyi> <rad-prod>
						  <px> <py>)
			  (point-to-avoid-object <oxi> <oyi> <pxi> <pyi> <px> 
						 <py> <rad-prod> <rad-obj> 
						 <x-to-move> <y-to-move>)))))
    (rhs  (select bindings (<x-to-move> <y-to-move> <px> <py>))))


))

(setq *SCR-NODE-REJECT-RULES* nil)
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES* nil)
(setq *SCR-BINDINGS-REJECT-RULES* nil)
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* nil)
(setq *SCR-OP-PREFERENCE-RULES* nil)
(setq *SCR-BINDINGS-PREFERENCE-RULES* nil)
