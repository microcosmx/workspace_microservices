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
(setq *SCR-BINDINGS-SELECT-RULES*  NIL)
(setq *SCR-NODE-REJECT-RULES* nil)
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES*

      '((CABLE-FIRST-BACKPLANE-ONLY-ONCE
	 ;;
	 ;; 5/13/88 prodigy tried to use the C-F-B-T-P-S operator
	 ;; to undo a prior backplane/box configuration. 
	 ;;
	 (lhs (and (current-node <node>)
		   (current-goal <node> (cabled <bp1>))
		   (known <node> (cabled <bp2>))))
	 (rhs (reject operator CABLE-FIRST-BP-TO-POWER-SUPPLY)))
))


(setq *SCR-BINDINGS-REJECT-RULES* 
      '((REJECT-AN-ALREADY-OCCUPIED-SLOT-NUMBER
	 (lhs (and (current-node <node>)
		   (current-goal <node> (inside-bp <b> <bp> slot <bps>))
		   (known <node>
			  (and (instance <bp> <bp> bp)
			       (slot-number <bpt> <slot> <bps>)
			       (instance <b> <bt> board)
			       (inside-bp <b> <bp> slot <bps>)))))
	 (rhs (reject bindings (<bps>))))))
	
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* nil)
(setq *SCR-OP-PREFERENCE-RULES* nil)
(setq *SCR-BINDINGS-PREFERENCE-RULES* 
      '((TRY-PLACING-BPS-IN-THE-SAME-BOX-BEFORE-USING-ANOTHER
	 (lhs (and (current-node <node>)
		   (current-op <node> PLACE-BP-INSIDE-BOX)
		   (candidate-bindings <node> (<bx1> <bxs1> <bp1>))
		   (candidate-bindings <node> (<bx2> <bxs2> <bp2>))
		   (known <node>
			  (and   (inside-box <bp> <bx> section <bxs>)
				 (equal-p <bx1> <bx>)))))
	 (rhs (prefer bindings (<bx1> <bxs1> <bp1>)
			       (<bx2> <bxs2> <bp2>))))))
		


;; CONFIGURE-MODS-BEFORE-BPS
;;       when goal is (configured <o> <i>)


;; PREFER-SPC-BP-TO-RK611-BP
;;       when goal is (instance <x> <xt> bp)

;; TRY-PUTTING-BOARDS-IN-AN-ALREADY-CABLED-BP 
;;       when goal is (inside <b> <bp> section <n>)



