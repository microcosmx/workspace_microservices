;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  simple topple that just gets blocka off of blockb

(load-goal '(clear blockB))

(load-start-state 
    '(
      (clear blockA)
      (object blockA)
      (object blockB)
      (weight blockA 400)
      (weight blockB 200)
      (box blockA)
      (on-table blockB)
      (location blockA (1 1))
      (location blockB (1 1))
      (on blockA blockB)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

