;******* VERSION 1-1 ******* Locked by nobody *******
;  
;  picks a block up off the table to hold it
; 


(load-goal '(holding blockA))

(load-start-state 
    '(
      (clear blockA)
      (object blockA)
      (weight blockA 100)
      (box blockA)
      (on-table blockA)
      (location blockA (1 1))
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

