;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  has to topple blockA and blockB and blockC then move 
;     a block then topple blockD
; 

(load-goal '(clear blockE))

(load-start-state 
    '(
      (clear blockA)
      (object blockA)
      (object blockB)
      (object blockC)
      (object blockD)
      (object blockE)
      (weight blockA 400)
      (weight blockB 300)
      (weight blockC 300)
      (weight blockD 300)
      (weight blockE 300)
      (on-table blockE)
      (location blockA (1 1))
      (location blockB (1 1))
      (location blockC (1 1))
      (location blockD (1 1))
      (location blockE (1 1))
      (on blockA blockB)
      (on blockB blockC)
      (on blockC blockD)
      (on blockD blockE)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

