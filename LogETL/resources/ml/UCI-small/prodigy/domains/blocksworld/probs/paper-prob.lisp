(load-goal '(holding cblockB)) 
; c = paper b, a=paper c, b= paper a

(load-start-state 
    '((arm-empty)
      (clear blockA)
      (object ablockC)
      (object cblockB)
      (object blockA)
      (on-table ablockC)
      (on blockA cblockB)
      (on cblockB ablockC)))






