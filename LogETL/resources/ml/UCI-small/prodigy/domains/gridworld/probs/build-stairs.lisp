;******* VERSION 1-2 ******* Locked by nobody *******
; 
; In this problem the RMG needs to build a staircase to get
; on top of the big block.
; 

(load-goal '(at RMG (location 3 1 3 3 1 3)))


(load-start-state
 '((at RMG (location 1 1 0 1 1 0))
   (at block1 (location 0 2 0 0 2 0))
   (at block2 (location 0 1 0 0 1 0))
   (at block3 (location 0 0 0 0 0 0))
   (at big-block (location 3 1 0 3 1 2))
   (object block1 (size 1 1 1)) 
   (object block2 (size 1 1 1)) 
   (object block3 (size 1 1 1)) 
   (object big-block (size 1 1 3))))
