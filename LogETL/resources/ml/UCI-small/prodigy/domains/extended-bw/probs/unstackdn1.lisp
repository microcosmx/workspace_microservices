;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  takes three blocks down from one another
; 

(load-goal '(and (clear blockA)
		 (clear blockB)
		 (clear blockC)))

(load-start-state 
    '(
      (object blockA)
      (object blockB)
      (object blockC)
      (weight blockA 100)
      (weight blockB 100)
      (weight blockC 100)
      (box blockA)
      (box blockB)
      (box blockC)
      (location blockA (1 1))
      (location blockB (1 1))
      (location blockC (1 1))
      (on-table blockC)
      (on blockA blockB)
      (on blockB blockC)
      (clear blockA)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

