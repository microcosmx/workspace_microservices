;;
;; Minijup Problem #1
;;
;; In this case Prodigy is NOT aligned with the other object and its 
;; destination, and the whole action takes place in the first cuadrant.
;;


(load-goal '(and (at ball 100 99)
		 (at-prodigy 5 5)))

(load-start-state '((at ball 15 14)
		    (at-prodigy 1 34)
		    (radius prodigy 3)
		    (radius ball 2)
		    (linear-viscosity 20)
		    (mass prodigy 70)
		    (mass ball 30)
		    (modul-elast prodigy .3)
		    (modul-elast ball 1)
		    (coeff-resti prodigy .8)
		    (coeff-resti ball .2)
		    (time-step 1)
		    (upd-per-cycle 10)
		    (theo-f-ap-time 1)))
