

(load-goal '(and (define <n> as not p)
		 (define <e> as p equivalent <n>)
		 (define <ne> as not <e>)))


(load-start-state '((current-time 0)
		    (define np as not p)
		    (define p as p)
		    (define penp as p equivalent np)
		    (formula np assumed negation not-available 0)
		    (formula p assumed term available 0)
		    (formula penp assumed equivalence available 0)))
