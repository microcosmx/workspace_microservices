(load-goal '(and (on blockA blockB) (on blockB blockC)))

(load-start-state 
    '(
      (clear blockB)
      (clear blockC)
      (clear blockA)
      (object blockA)
      (object blockB)
      (object blockC)
      (object blockD)
      (on blockB blockD)
      (on-table blockC)
      (on-table blockA)
      (arm-empty)))

