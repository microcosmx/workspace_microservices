

;;  A simple parser.
;; Left-associative Grammar
;; Theory and Implementation, 
;; by Roland Hausser 6/3/87




(load-goal '(parsed <n> <w> np))


(load-start-state '((word the)
		    (word quick)
		    (word brown)
		    (word fox)
		    (word jumped)
		    (word over)
		    (word lazy)
		    (word dogs)
		    (word around)
		    (word ate)
		    (word biscuits)
		    (word crazy)
		    (word delicious)
		    (word fence)
		    (word field)
		    (word fox)
		    (word through)
		    (word swam)
		    (word ran)
		    (word \.)

		    (category np)
		    (category n) 
		    (category s) 
		    (category v) 
		    (category p) 
		    (category pp)
		    (category vp)
		    (category m) 
		    (category d) 
		    (category done)
		    (category start)

		    (lex around p)
		    (lex ate v)
		    (lex biscuits n)
		    (lex brown m)
		    (lex crazy m)
		    (lex delicious m)
		    (lex dogs n)
		    (lex fence n)
		    (lex field m)
		    (lex fox n)
		    (lex jumped v)
		    (lex lazy m)
		    (lex over p)
		    (lex quick m)
		    (lex ran v)
		    (lex swam v)
		    (lex the d)
		    (lex through p)
		    (lex \. finish) 
		    (lex null start)

		    (parsed 0 null null)

		    (p 1 the)
		    (p 2 quick)
		    (p 3 brown)
		    (p 4 fox)
		    (p 5 jumped) 
		    (p 6 over) 
		    (p 7 the)
		    (p 8 lazy)
		    (p 9 dogs)
		    (p 10 \.)
))
