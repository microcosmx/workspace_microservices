(load-goal '(and (on blockA blockB) (holding blockC)))

(load-start-state 
    '(
      (clear blockC)
      (clear blockB)
      (object blockA)
      (object blockB)
      (on-table blockB)      
      (object blockC)
      (on-table blockC)
      (holding blockA)))

