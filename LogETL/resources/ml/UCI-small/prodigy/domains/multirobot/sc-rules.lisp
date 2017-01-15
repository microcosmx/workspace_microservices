;******* VERSION 1-1 ************************** January 30, 1989

;;;
;;; This is an unstable version; new control rules are being tested and added,
;;; and the operators can also change.
;;;
;;; If you have any suggestion or question, please send mail to aperez@cs
;;;


(setq *SCR-NODE-SELECT-RULES* nil)
(setq *SCR-GOAL-SELECT-RULES*
      '((SELECT-FIRST-GOAL
  	  (lhs (and (current-node <node>)
	            (list-of-candidate-goals <node> <goals>)
	            (is-first-goal <goal> <goals>)))
           (rhs (select goal <goal>)))))

(setq *SCR-OP-SELECT-RULES* 

;; The following rules reject the operators that make the robot move pushing
;; an object when he should move alone

      '((MOVE-ROBOT-ONLY--SELECT-GO-THRU
	  (lhs (and (current-node <node>)
	            (current-goal <node> (in-room <robot> <room>))
		    (candidate-op  <node> GO-THRU-DOOR)
		    (known <node> (is-type <robot> robot))))
	  (rhs (select op GO-THRU-DOOR)))

	(MOVE-ROBOT-ONLY--SELECT-GOTO-OBJECT
	  (lhs (and (current-node <node>)
	            (current-goal <node> (next-to <robot> <object>))
		    (candidate-op  <node> GOTO-OBJECT)
		    (known <node> (is-type <robot> robot))))
	  (rhs (select op GOTO-OBJECT)))

	(MOVE-ROBOT-ONLY--SELECT-GOTO-DOOR
	  (lhs (and (current-node <node>)
	            (current-goal <node> (next-to <robot> <door>))
		    (candidate-op  <node> GOTO-DOOR)		 
		    (known <node> (is-type <robot> robot))))
	  (rhs (select op GOTO-DOOR)))

	))

(setq *SCR-BINDINGS-SELECT-RULES* 

;; if the robot is in roomy and the goal is to be in room x, and roomx and 
;; roomy are connected, it goes thru that door directly (avoids a longer path)

      '((SELECT-SHORTER-PATH
	 (lhs (and (current-node <node>)
		   (current-op <node> GO-THRU-DOOR)
		   (current-goal <node> (in-room <robot> <roomx>))
		   (known <node> (in-room <robot> <roomy>))
		   (known <node> (or (connect <door> <roomx> <roomy>)
				     (connect <door> <roomy> <roomx>)))
		   (~ (known <node> (statis <door> locked)))))
	 (rhs (select bindings (<robot> <door> <roomy> <roomx>))))
	
	))

(setq *SCR-NODE-REJECT-RULES* nil)
(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES*  

;;The following six reject-operator rules avoid that after failing to push an 
;;object using only one robot, try to redo the same thing but using two robots
;;(This would be the same as adding a (greater weight *maxweight*) precond.
;;to the team operators)

      '((REJECT-TPOB-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (next-to <objectx> <objecty>))
		   (known <node> 
			  (and (weight <objectx> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> PUSH-OBJECT)
		   (candidate-op <node> T-PUSH-OBJECT)))
	 (rhs (reject op T-PUSH-OBJECT)))
	
	(REJECT-TPDO-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (next-to <object> <door>))
		   (known <node> 
			  (and (weight <object> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> PUSH-TO-DOOR)
		   (candidate-op <node> T-PUSH-TO-DOOR)))
	 (rhs (reject op T-PUSH-TO-DOOR)))

	(REJECT-TPLOC-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (at <object> <x> <y>))
		   (known <node> 
			  (and (weight <object> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> PUSH-TO-LOC)
		   (candidate-op <node> T-PUSH-TO-LOC)))
	 (rhs (reject op T-PUSH-TO-LOC)))

   	(REJECT-TPTD-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (in-room <object> <room>))
		   (known <node> 
			  (and (weight <object> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> PUSH-THRU-DOOR)
		   (candidate-op <node> T-PUSH-THRU-DOOR)))
	 (rhs (reject op T-PUSH-THRU-DOOR)))
 
	(REJECT-TPINTO-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (in <box> <block>))
		   (known <node> 
			  (and (weight <block> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> PUT-BLOCK-INTO-BOX)
		   (candidate-op <node> T-PUT-BLOCK-INTO-BOX)))
	 (rhs (reject op T-PUT-BLOCK-INTO-BOX)))

	(REJECT-TTOUT-IF-ONE-CAN-ALONE
	 (lhs (and (current-node <node>)
		   (current-goal <node> (~(in <box> <block>)))
		   (known <node> 
			  (and (weight <block> <weight>)
			       (less-than <weight> 100)))		       
		   (candidate-op <node> TAKE-BLOCK-OUT-OF-BOX)
		   (candidate-op <node> T-TAKE-BLOCK-OUT-OF-BOX)))
	 (rhs (reject op T-TAKE-BLOCK-OUT-OF-BOX)))

	))
    
(setq *SCR-BINDINGS-REJECT-RULES* 

;;This rule avoids that the robot attempts to go to a room that he has already
;;visited, i.e. is in the stack goal, when trying to find a path from one room
;;to other one.

      '((REJECT-GOING-BACK-TO-SAME-ROOM
	 (lhs (and (current-node <node>)
		   (current-goal <node> (in-room <robot> <roomy>))
		   (current-op <node> GO-THRU-DOOR)
	           (candidate-bindings <node> (<robot> <door> <roomx> <roomy>))
		   (on-goal-stack <node> (in-room <robot> <roomx>))))
	 (rhs (reject bindings (<robot> <door> <roomx> <roomy>))))


;; These rules avoid to generate bindings for the TEAM operators such that the
;; only difference among them is the order in the robot vars. The 
;; string-greaterp is a trick to distinguish between the two robots (there is 
;; no other way, unless they had different capabilities) and should be 
;; reconsidered if they had, since it could reject good alternatives.
;; The rules are only useful when the team op does not lead to a solution 
;; (otherwise, the first couple of bindings is valid and no other bindings 
;; are tried

  	(TPOB-SELECT-ONLY-ONE-COUPLE
  	 (lhs (and (current-node <node>)
  		   (current-op <node> T-PUSH-OBJECT)
  	           (candidate-bindings <node> 
  			    (<robot1> <robot2> <objectx> <objecty>))
  	           (candidate-bindings <node> 
  			    (<robot2> <robot1> <objectx> <objecty>))
  		   (known <node> (greater-var <robot1> <robot2>))))
  	 (rhs (reject bindings 
  		   (<robot1> <robot2> <objectx> <objecty>))))
  
  	(TPDO-SELECT-ONLY-ONE-COUPLE
  	 (lhs (and (current-node <node>)
  		   (current-op <node> T-PUSH-TO-DOOR)
  	           (candidate-bindings <node> 
  			    (<robot1> <robot2> <object> <door> <roomx>))
  	           (candidate-bindings <node> 
  			    (<robot2> <robot1> <object> <door> <roomx>))
  		   (known <node> (greater-var <robot1> <robot2>))))
  	 (rhs (reject bindings 
  		   (<robot1> <robot2> <object> <door> <roomx>))))
  
  	(TPLOC-SELECT-ONLY-ONE-COUPLE
  	 (lhs (and (current-node <node>)
  		   (current-op <node> T-PUSH-TO-LOC)
  	           (candidate-bindings <node> 
  			    (<robot1> <robot2> <object> <x> <y>))
  	           (candidate-bindings <node> 
  			    (<robot2> <robot1> <object> <x> <y>))
  		   (known <node> (greater-var <robot1> <robot2>))))
  	 (rhs (reject bindings 
  		   (<robot1> <robot2> <object> <x> <y>))))
  
  	(TPTD-SELECT-ONLY-ONE-COUPLE
  	 (lhs (and (current-node <node>)
  		   (current-op <node> T-PUSH-THRU-DOOR)
  	           (candidate-bindings <node> 
  			    (<robot1> <robot2> <object> <door> <roomy> <roomx>))
  	           (candidate-bindings <node> 
  			    (<robot2> <robot1> <object> <door> <roomy> <roomx>))
  		   (known <node> (greater-var <robot1> <robot2>))))
  	 (rhs (reject bindings 
  		   (<robot1> <robot2> <object> <door> <roomy> <roomx>))))
  
  	(TPINTO/OUT-SELECT-ONLY-ONE-COUPLE
  	 (lhs (and (current-node <node>)
  		   (or (current-op <node> T-PUT-BLOCK-INTO-BOX)
  		       (current-op <node> T-PUT-BLOCK-INTO-BOX))
  	           (candidate-bindings <node> 
  			    (<robot1> <robot2> <block> <box>))
   	           (candidate-bindings <node> 
  			    (<robot2> <robot1> <block> <box>))
  		   (known <node> (greater-var <robot1> <robot2>))))
  	 (rhs (reject bindings 
  		   (<robot1> <robot2> <block> <box>))))

	))

(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* nil)
(setq *SCR-OP-PREFERENCE-RULES*  nil )
(setq *SCR-BINDINGS-PREFERENCE-RULES*

;;If a door has to be opened because <robot1> has to go thru it, <robot1> is 
;;the preferred candidate to open the door, since he will have to go near
;;the door anyway

      '((PREFER-ROBOT-THAT-HAS-TO-GO-THRU-FOR-OPEN-DOOR
	  (priority 0)
	  (lhs (and (current-node <node>)
		    (current-op <node> OPEN-DOOR)
		    (on-goal-stack <node> (in-room <robot1> <room>))
		    (candidate-bindings <node> (<robot1> <door>))
		    (candidate-bindings <node> (<robot2> <door>))
		    (known <node> (connects <door> <roomx> <room>))))
	  (rhs (prefer bindings 
		       (<robot1> <door>)
		       (<robot2> <door>))))

	))

