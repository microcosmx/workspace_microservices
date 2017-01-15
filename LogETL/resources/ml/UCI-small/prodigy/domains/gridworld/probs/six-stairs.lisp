;******* VERSION 1-2 ******* Locked by nobody *******
; 
; In this problem the RMG needs to build a staircase to get
; on top of the big block.
; 

(load-goal '(at RMG (location 2 2 3 2 2 3)))


(load-start-state
 '((at RMG (location 1 1 0 1 1 0))
   (at block1 (location 0 2 0 0 2 0))
   (at block2 (location 0 1 0 0 1 0))
   (at block3 (location 0 0 0 0 0 0))
   (at block4 (location 4 2 0 4 2 0))
   (at block5 (location 4 1 0 4 1 0))
   (at block6 (location 4 0 0 4 0 0))
   (object block1 (size 1 1 1)) 
   (object block2 (size 1 1 1)) 
   (object block3 (size 1 1 1)) 
   (object block4 (size 1 1 1)) 
   (object block5 (size 1 1 1)) 
   (object block6 (size 1 1 1))))

