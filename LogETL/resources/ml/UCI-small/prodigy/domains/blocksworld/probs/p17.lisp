(load-goal '(on blockA blockB))

(load-start-state 
    '(
      (clear blockB)
      (clear blockC)
      (clear blockA)
      (object blockA)
      (object blockB)
      (object blockC)
      (on-table blockB)
      (on-table blockC)
      (on-table blockA)
      (arm-empty)))

