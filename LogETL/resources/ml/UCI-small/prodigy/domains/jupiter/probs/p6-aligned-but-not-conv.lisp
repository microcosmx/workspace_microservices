;;
;; Minijup  problem #6 
;; 
;; In this case, the object is ALIGNED with Prodigy and the destination,
;; but NOT CONVENIENTLY. There is no overlapping and the distance between
;; the first two is greater than twice the sum of their radii : P X O
;; 

(load-goal '(and (at ball 100 99)
		 (at-prodigy -34 2)))

(load-start-state '((at ball -3 -4)
		    (at-prodigy 113 112)
		    (radius prodigy 5)
		    (radius ball 2)
		    (linear-viscosity 17)
		    (mass prodigy 25)
		    (mass ball 15)
		    (modul-elast prodigy 1)
		    (modul-elast ball .15)
		    (coeff-resti prodigy 0)
		    (coeff-resti ball .2)
		    (time-step 3)
		    (upd-per-cycle 3)
		    (theo-f-ap-time 1)))
