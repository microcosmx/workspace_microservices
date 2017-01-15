;;
;; Minijup  problem #2 
;; 
;; Now Prodigy is  NOT CONVENIENTLY-ALIGNED with the object and its
;; destination, and, after Prodigy moves, they will all be contained
;; in a vertical line.  
;; 

(load-goal '(and (at ball -35 -2)
		 (at-prodigy 65 -211)))

(load-start-state '((at ball -35 243)
		    (at-prodigy -28 -19)
		    (radius prodigy 4)
		    (radius ball 5)
		    (linear-viscosity 18)
		    (mass prodigy 20)
		    (mass ball 10)
		    (modul-elast prodigy .8)
		    (modul-elast ball .1)
		    (coeff-resti prodigy .3)
		    (coeff-resti ball .65)
		    (time-step 2)
		    (upd-per-cycle 5)
		    (theo-f-ap-time 1)))
