;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  Changes the weight of a block from 400 to 700 using GLUE op.
; 

(setq *XDIM* 4 *YDIM* 4)

(load-goal '(weight blockA 500))

(load-start-state 
    '((clear blockA)
      (object blockA)
      (weight blockA 400)
      (on-table blockA)
      (location blockA (1 1))
      (clear blockB)
      (object blockB)
      (weight blockB 170)
;      (holding blockB)
      (on-table blockB)
      (location blockB (2 2))
      (clear blockC)
      (object blockC)
      (weight blockC 100)
;      (holding blockC)
      (on-table blockC)
      (location blockC (2 3))
      (vacant-loc (1 2))
      (vacant-loc (1 3))
      (vacant-loc (2 1))
;      (vacant-loc (2 2))
;      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))
