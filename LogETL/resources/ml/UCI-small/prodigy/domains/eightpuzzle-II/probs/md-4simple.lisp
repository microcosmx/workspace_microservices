;******* VERSION 1-1 ******* Locked by nobody *******




; 1 2
; 3 4


(load-goal '(at 2 2))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 2 1)
(left-of 4 3)

(right-of 1 2)
(right-of 3 4)

(up-of 3 1)
(up-of 4 2)


(down-of 1 3)
(down-of 2 4)


; Initial tile configuration.

; B 1
; 2 3


(at 1 blank)
(at 2 1)

(at 3 2)
(at 4 3)
))