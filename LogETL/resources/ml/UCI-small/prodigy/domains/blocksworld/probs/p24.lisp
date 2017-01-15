(load-goal '(and (holding C) (clear B)))

(load-start-state 
   '((object C)
     (on-table C)
     (object B)
     (on-table B)
     (object A)
     (on A B)
     (clear A)
     (clear C)
     (arm-empty)))

