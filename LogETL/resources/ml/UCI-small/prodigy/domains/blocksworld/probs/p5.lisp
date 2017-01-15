(load-goal '(and (on blockA blockB)(on blockB blockC)))

(load-start-state 
    '(
      (arm-empty)
      (clear blockA)
      (clear blockB)
      (clear blockD)
      (object blockA)
      (object blockB)
      (object blockC)
      (object blockD)
      (on-table blockA)
      (on-table blockC)
      (on blockD blockC)
      (on-table blockB)))




