(load-goal '(on blockA blockB))

(load-start-state 
    '(
      (clear blockB)
      (clear blockA)
      (object blockA)
      (object blockB)
      (object blockC)
      (on blockA blockC)
      (on-table blockB)
      (on-table blockC)
      (arm-empty)))

