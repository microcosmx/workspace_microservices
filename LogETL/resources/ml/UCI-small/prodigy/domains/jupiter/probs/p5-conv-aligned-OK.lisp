;;
;; Minijup  problem #5 
;; 
;; Here we have the case in which the object is CONVENIENTLY-ALIGNED with 
;; Prodigy and the destination, there is no overlapping and the distance
;; between the first two is greater than twice the sum of their radii :
;; Prodigy can bump the object.
;; 

(load-goal '(and (at ball 100 99)
		 (at-prodigy 5 5)))

(load-start-state '((at ball 15 14)
		    (at-prodigy -45 -46)
		    (radius prodigy 15)
		    (radius ball 12)
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
