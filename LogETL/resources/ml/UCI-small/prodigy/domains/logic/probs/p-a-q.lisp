
;; Given P, Q,  prove   (P & Q). 


(load-goal '(define <x> as p and q))

(load-start-state '((current-time 0)
		    (define p as p)
		    (define q as q)
		    (formula p assumed term available 0)
		    (formula q assumed term available 0)))
