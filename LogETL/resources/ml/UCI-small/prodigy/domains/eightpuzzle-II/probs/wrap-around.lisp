;******* VERSION 1-1 ******* Locked by nobody *******

; Initial tile configuration.

; B 1
; 2 3


; 11 12 
; 21 22 


(load-goal '(at 12 3))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 12 11)
(left-of 22 21)

(left-of 11 12)
(left-of 21 22)

(right-of 11 12)
(right-of 21 22)

(right-of 12 11)
(right-of 22 21)

(up-of 21 11)
(up-of 22 12)


(up-of 11 21)
(up-of 12 22)


(down-of 11 21)
(down-of 12 22)

(down-of 21 11)
(down-of 22 12)


; Initial tile configuration.

; B 1
; 2 3


(at 11 blank)
(at 12 1)

(at 21 2)
(at 22 3)


))