; dont use unstack if you are holding a block
; and want to clear it.

(load-goal '(clear blockA))

(load-start-state 
    '(
      (holding blockA)
      (clear blockB)
      (object blockA)
      (object blockB)
      (on-table blockB)))
