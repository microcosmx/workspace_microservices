;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  has to topple blockA and blockB off blockC
; 

(load-goal '(clear blockC))

(load-start-state 
    '(
      (clear blockA)
      (object blockA)
      (object blockB)
      (object blockC)
      (weight blockA 400)
      (weight blockB 300)
      (weight blockC 300)
      (on-table blockC)
      (location blockA (1 1))
      (location blockB (1 1))
      (location blockC (1 1))
      (on blockA blockB)
      (on blockB blockC)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

