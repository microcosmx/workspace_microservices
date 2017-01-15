; *** case4.lisp 

(load-goal '(and (place 1 1 1) (place 2 1 2) (place 3 1 3)
		 (place 1 2 4) (place 2 2 5) (place 3 2 6)
	    ))

(load-start-state
           '((vnei 1 2) (vnei 2 3) (hnei 1 2) (hnei 2 3)
	     (ih 1) (ih 2) (ih 3) (iv 1) (iv 2) (iv 3)

	     (place 1 1 5) (place 2 1 6) (place 3 1 7)
	     (place 1 2 4) (place 2 2 1) (place 3 2 8)
	     (place 1 3 3) (place 2 3 2) (pla 3 3)
	    ))
