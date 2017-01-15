;;
;;                      n  n  n     
;; The definition of (a  b  c  ),  from 
;;
;; Left-associative Grammar: Theory and Implementation, 
;; by Roland Hausser 6/3/87




(load-goal '(parsed-queue))


(load-start-state '((current 0)
		    (lex a xy)
		    (lex b x)
		    (lex c y)
		    (lex \. done)
		    (parsed 0 null null null)
		    (queue 0 null)
		    (queue 1 a)
		    (queue 2 a)
		    (queue 3 a)
		    (queue 4 b)
		    (queue 5 b)
		    (queue 6 b)
		    (queue 7 c)
		    (queue 8 c)
		    (queue 9 c)
		    (queue 10 \.)))


