
;; Given (p & q),  prove  ((p & q) -> (q & p)). 


(load-goal '(and (define <y> as Q and P)
		 (define <x> as P-AND-Q implies <y>)))

(load-start-state '((current-time 0)
		    (define P-AND-Q as P and Q)
		    (define P as P)
		    (define Q as Q)
		    (formula P-AND-Q assumed conjunction available 0)
		    (formula P assumed term not-available 0)
		    (formula Q assumed term not-available 0)))



