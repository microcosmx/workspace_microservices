

(load-goal '(and (define <r> as not p)
		 (define <p> as p)
		 (define <q> as not <r>)
		 (define <x> as <p> implies <q>)))



(load-start-state '((current-time 0)
		    (define p as p)
		    (define not-p as not p)
		    (formula p assumed term available 0)
		    (formula not-p assumed negation available 0)))


