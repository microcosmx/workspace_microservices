;******* VERSION 1-1 ******* Locked by nobody *******




; 11 12 
; 21 22 


(load-goal '(and (at 12 2) (at 21 blank)))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 12 11)
(left-of 22 21)

(right-of 11 12)
(right-of 21 22)

(up-of 21 11)
(up-of 22 12)


(down-of 11 21)
(down-of 12 22)


; Initial tile configuration.

; B 1
; 2 3


(at 11 blank)
(at 12 1)

(at 21 2)
(at 22 3)


))