(load-goal '(and (clear C) (on-table C) (holding D)))

(load-start-state
    '((object D)
     (on-table D)
     (object C)
     (on C D)
     (object B)
     (on B C)
     (object A)
     (on A B)
     (clear A)
     (arm-empty)))

