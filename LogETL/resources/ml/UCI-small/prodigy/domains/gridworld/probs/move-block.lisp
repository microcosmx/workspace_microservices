;******* VERSION 1-1 ******* Locked by nobody *******
; 
; In this problem RMG has to move a block.
; 
(load-goal '(at small-block (location 2 1 0 2 1 0)))


(load-start-state
 '((at RMG (location 1 1 0 1 1 0))
   (object small-block (size 1 1 1)) 
   (at small-block (location 1 2 0 1 2 0))))
