;******* VERSION 1-1 ******* Locked by nobody *******




; 11 12 13
; 21 22 23


(load-goal '(at 12 5))

(load-start-state 
 
'(
; Fixed Board configuration.
(left-of 12 11)
(left-of 22 21)
(left-of 13 12)
(left-of 23 22)

(right-of 11 12)
(right-of 21 22)
(right-of 12 13)
(right-of 22 23)

(up-of 21 11)
(up-of 22 12)
(up-of 23 13)


(down-of 11 21)
(down-of 12 22)
(down-of 13 23)

; Initial tile configuration.

; B 1 2
; 3 4 5


(at 11 blank)
(at 12 1)
(at 13 2)

(at 21 3)
(at 22 4)
(at 23 5)

))