;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  puts a block down on the table from holding it
; 

(load-goal '(on-table blockA))

(load-start-state 
    '(
      (object blockA)
      (weight blockA 100)
      (box blockA)
      (holding blockA)
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

