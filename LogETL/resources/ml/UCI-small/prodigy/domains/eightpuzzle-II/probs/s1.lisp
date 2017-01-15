;******* VERSION 1-1 ******* Locked by nobody *******




; 11 12 13
; 21 22 23
; 31 32 33


(load-goal '(and (at 22 blank) (at 33 5)))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 12 11)
(left-of 22 21)
(left-of 32 31)
(left-of 13 12)
(left-of 23 22)
(left-of 33 32)

(right-of 11 12)
(right-of 21 22)
(right-of 31 32)
(right-of 12 13)
(right-of 22 23)
(right-of 32 33)

(up-of 21 11)
(up-of 22 12)
(up-of 23 13)
(up-of 31 21)
(up-of 32 22)
(up-of 33 23)

(down-of 11 21)
(down-of 12 22)
(down-of 13 23)
(down-of 21 31)
(down-of 22 32)
(down-of 23 33)

; Initial tile configuration.

; B 1 2
; 3 4 5
; 6 7 8

(at 11 blank)
(at 12 1)
(at 13 2)

(at 21 3)
(at 22 4)
(at 23 5)

(at 31 6)
(at 32 7)
(at 33 8)))