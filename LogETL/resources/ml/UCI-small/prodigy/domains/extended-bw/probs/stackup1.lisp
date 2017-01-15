;******* VERSION 1-1 ******* Locked by nobody *******
; 
;  stacks two block on top of one another 
; 

;  bug in the common lisp version steve knows about it 
;(load-goal '(and (on <ob3> <ob2>)
;		 (on <ob2> <ob1>)))

(load-goal '(and (object blockA)
		 (on blockA blockB)
		 (on blockB blockC)))
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
      (location blockB (1 2))
      (location blockC (1 3))
      (on-table blockA)
      (on-table blockB)
      (on-table blockC)
      (clear blockA)
      (clear blockB)
      (clear blockC)
;      (vacant-loc (1 2))
;      (vacant-loc (1 3))
      (vacant-loc (2 1))
      (vacant-loc (2 2))
      (vacant-loc (2 3))
      (vacant-loc (3 1))
      (vacant-loc (3 2))
      (vacant-loc (3 3))
     ))

