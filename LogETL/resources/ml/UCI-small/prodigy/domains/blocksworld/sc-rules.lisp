
(setq *SCR-NODE-SELECT-RULES* nil)
(setq *SCR-GOAL-SELECT-RULES* 
      '((SELECT-FIRST-GOAL
  	  (lhs (and (current-node <node>)
		    (not-top-level-node <node>)
                    (primary-candidate-goal <node> <goal>)))
           (rhs (select goal <goal>)))
       ))

(setq *SCR-OP-SELECT-RULES*  nil)
(setq *SCR-BINDINGS-SELECT-RULES* nil)
(setq *SCR-NODE-REJECT-RULES* nil)
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES* nil)
(setq *SCR-BINDINGS-REJECT-RULES* nil)
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES*  nil)
(setq *SCR-OP-PREFERENCE-RULES* nil)
(setq *SCR-BINDINGS-PREFERENCE-RULES* nil)
