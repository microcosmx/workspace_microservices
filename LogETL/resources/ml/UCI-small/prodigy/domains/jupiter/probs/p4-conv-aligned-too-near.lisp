;;
;; Minijup  problem #4 
;; 
;; In this case, the object is CONVENIENTLY-ALIGNED with Prodigy and the 
;; destination, but the distance between the centres of the first two is
;; smaller than twice the sum of their radii, which is the minimum arbi-
;; trary distance we have chosen for Prodigy to be able to bump the other
;; object.
;; 

(load-goal '(and (at ball 100 99)
		 (at-prodigy 5 5)))

(load-start-state '((at ball 15 14)
		    (at-prodigy -3 -4)
		    (radius prodigy 9)
		    (radius ball 8)
		    (linear-viscosity 35)
		    (mass prodigy 7)
		    (mass ball .5)
		    (modul-elast prodigy 1)
		    (modul-elast ball .15)
		    (coeff-resti prodigy 0)
		    (coeff-resti ball .2)
		    (time-step 3)
		    (upd-per-cycle 3)
		    (theo-f-ap-time 1)))
