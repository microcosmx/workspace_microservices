;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  stacks one block on top of another 
; 

(load-goal '(on blockA blockB))

(load-start-state 
    '(
      (object blockA)
      (object blockB)
      (weight blockA 100)
      (weight blockB 200)
      (box blockA)
      (holding blockA)
      (location blockB (1 1))
      (on-table blockB)
      (clear blockB)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

