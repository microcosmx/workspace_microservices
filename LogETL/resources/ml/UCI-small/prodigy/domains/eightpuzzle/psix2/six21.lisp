; *** six21

(load-goal '(and (place 1 1 1) (place 2 1 2)))

(load-start-state
           '((vnei 1 2) (vnei 2 3) (hnei 1 2)  ;   (before down)
	     (ih 1) (ih 2) (ih 3) (iv 1) (iv 2)
	     (pla 2 1)
	     (place 1 1 1)
	     (place 3 1 2)
	     (place 1 2 3)
	     (place 2 2 4)
	     (place 3 2 5)
	    ))
