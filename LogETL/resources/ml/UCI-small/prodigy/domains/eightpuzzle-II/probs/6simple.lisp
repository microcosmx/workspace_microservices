;******* VERSION 1-1 ******* Locked by nobody *******

; Initial tile configuration.

; B 1
; 2 3
; 4 5


; 11 12 
; 21 22 
; 31 32

(load-goal '(at 12 5))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 12 11)
(left-of 22 21)
(left-of 32 31)

(right-of 11 12)
(right-of 21 22)
(right-of 31 32)

(up-of 21 11)
(up-of 22 12)
(up-of 31 21)
(up-of 32 22)

(down-of 11 21)
(down-of 12 22)
(down-of 31 21)
(down-of 32 22)


; Initial tile configuration.

; B 1
; 2 3


(at 11 blank)
(at 12 1)

(at 21 2)
(at 22 3)

(at 31 4)
(at 32 5)

))