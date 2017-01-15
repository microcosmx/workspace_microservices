;;
;; Minijup  problem #7
;; 
;; This problem is similar to p6, but instead of being aligned in a 
;; P X O manner, the three points are placed X P O.
;; 

(load-goal '(at ball 100 99))
		 
(load-start-state '((at ball 1 0)
		    (at-prodigy 67 66)
		    (radius prodigy 8)
		    (radius ball 4.5)
		    (linear-viscosity 50)
		    (mass prodigy 90)
		    (mass ball 40)
		    (modul-elast prodigy .1)
		    (modul-elast ball .7)
		    (coeff-resti prodigy .4)
		    (coeff-resti ball .2)
		    (time-step 1)
		    (upd-per-cycle 10)
		    (theo-f-ap-time 1)))
