;******* VERSION 1-1 ******* Locked by nobody *******
; 
; In this problem RMG needs to get on top of the big block
; and has to use the small block as a step.
; 

(load-goal '(at RMG (location 3 1 2 3 1 2)))


(load-start-state
 '((at RMG (location 1 1 0 1 1 0))
   (at small-block (location 1 2 0 1 2 0))
   (object small-block (size 1 1 1)) 
   (object big-block (size 1 1 2))
   (at big-block (location 3 1 0 3 1 1))))
