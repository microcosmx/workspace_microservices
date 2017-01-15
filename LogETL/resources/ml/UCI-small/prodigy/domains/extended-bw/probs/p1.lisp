;******* VERSION 1-1 ******* Locked by nobody *******


(load-goal '(and (object <ob1>)
                 (object <ob2>)
                 (object <ob3>)
		 (on <ob3> <ob2>)
		 (on <ob2> <ob1>)))

(load-start-state 
    '(
      (clear blockA)
      (clear blockB)
      (clear blockC)
      (object blockA)
      (object blockB)
      (object blockC)
      (box blockA)
      (box blockB)
      (pyramid blockC)
      (on-table blockA)
      (on-table blockB)      
      (on-table blockC) 
     ))




