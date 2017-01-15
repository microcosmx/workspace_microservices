;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  blocka moves from (1 1) to (3 3) blockb is moved out of the way
; 

(setq *XDIM* 4 *YDIM* 4)

(load-goal '(location blockA (3 3)))

(load-start-state 
    '((clear blockA)
      (object blockA)
      (weight blockA 400)
      (on-table blockA)
      (location blockA (1 1))
      (clear blockB)
      (object blockB)
      (weight blockB 300)
      (on-table blockB)
      (location blockB (2 2))
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
;      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

