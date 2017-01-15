#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Steven Minton, Craig Knoblock, Dan Kuokka and Jaime Carbonell

The PRODIGY System was designed and built by Steven Minton, Craig Knoblock,
Dan Kuokka and Jaime Carbonell.  Additional contributors include Henrik Nordin,
Yolanda Gil, Manuela Veloso, Robert Joseph, Santiago Rementeria, Alicia Perez, 
Ellen Riloff, Michael Miller, and Dan Kahn.

The PRODIGY system is experimental software for research purposes only.
This software is made available under the following conditions:
1) PRODIGY will only be used for internal, noncommercial research purposes.
2) The code will not be distributed to other sites without the explicit 
   permission of the designers.  PRODIGY is available by request.
3) Any bugs, bug fixes, or extensions will be forwarded to the designers. 

Send comments or requests to: prodigy@cs.cmu.edu or The PRODIGY PROJECT,
School of Computer Science, Carnegie Mellon University, Pittsburgh, PA 15213.
*******************************************************************************|#


;                     Search Control Rule Evolution
;
; 1. ALREADY-THERE: (build-step)
;      To infer that a location was occupied, the RMG tried to move a small block 
;   into this location even though a big block was already there.  Clearly, this
;   location was already occupied by the big block.  I originally wrote the rule
;   ALREADY-THERE to fix the problem until I realized that CLOSEST-SIDE-FOR-
;   OCCUPIED was firing and telling the RMG to prefer the small block over the 
;   big block because the small block was closer to him.  I changed
;   CLOSEST-SIDE-FOR-OCCUPIED so that it first checks to see if a block is already 
;   *covering* the sqloc. Previously it only checked to see if a block's location 
;   was *equal* to the sqloc.  
;        Later on, I insisted once again that ALREADY-THERE was needed because the 
;   above situation occurred.  However this time the culprit was SUPPORTED-NOW 
;   which I had just recently added.  This rule was telling the RMG to prefer the
;   small block since the big block was sitting in a location that would be
;   supporting something later.  Obviously, leaving the block in this location
;   would not have upset its supporting role later.  So, SUPPORTED-NOW needed
;   to check that the block's location was not already covering the location
;   that would be supporting something later.
; 
;
; 2. SUPPORTING-OBJ: (easy-stairs)
;       To build a staircase in a top-down fashion, the RMG decided to use
;    block3 as the top step.  In order to put block3 down, however, the 
;    location underneath it had to be supported.  In this subgoaling step, it
;    picked up block3 to use as the supporting object.  Well, it put block3 down
;    as the bottom step and then picked it up again to put it down as the top step.
;    Since the top location was no longer supported he had to put it back
;    down and try to support this location again.  It is quite obvious that
;    this is not quite the optimal chain of events.
;       In response, I added this rule which checks to see if a block
;    is in a location such that it will be supporting an object later.  This check
;    is done by looking on the goal stack for an object that will need to 
;    be above the block or if the block is already supporting an object.  If so, 
;    then it will prefer another object.
; 
; 
; 3. TOWARD-SUPERGOAL: (easy-stairs)
;       To build the staircase, the RMG had to move out of a location so that
;    he could put down a block at this location.  Since he was only concerned
;    about vacating the square, he didn't care which square he moved to.  
;    Invariably, he chose to move to a location away from all of the objects.
;    Intuitively, he should try to move toward either 1) the next object that
;    he needs to pick-up, or 2) the next location that he needs to move to.
;    In this case, since he was vacating a location in order to put a block in
;    this location, he should have a supergoal to put a block "at" a location. 
;    So, I added this rule which looks for this supergoal on the goal stack
;    and prefers to move the RMG to a location which is closest to the block that
;    he will need to pick up.
;       When I added TOWARD-SUPERGOAL, the RMG decided that the location
;    closest to the block was the location that the block was sitting on.  So, he
;    calmly picked up the block and sat down in its location.  Well, this was fine
;    and dandy because his next goal was to pick up the block anyway.  This was the
;    right thing to do, but for the wrong reason.  He should not prefer a location
;    if it is already occupied.  So, I added this check in TOWARD-SUPERGOAL and 
;    told the RMG to give it another try.  This time, he decided that the location
;    closest to the block was the location directly on top of the block.  He 
;    promptly jumped on top of the block and sat down. What a bright little guy!  
;    The chaos that resulted when he then realized that he had to pick up the 
;    block he was sitting on is too horrible to mention.  At this point, I added
;    a clause to TOWARD-SUPERGOAL that prevents him from preferring a location
;    that is directly above the block that he is going toward. Finally, he did the
;    wrong thing again, but for the right reasons.
;    
; 
; 4. NEXT-TO-OBJ-BEFORE-VACATING: (easy-stairs)
;       This rule was written to make Prodigy do the right thing for the right
;    reasons in the following situation: the RMG wants to move out of the way in 
;    order to put the block sitting next to him in his current location.  
;    Intuitively, he should pick up the block first, move to the block's old 
;    location, and then put it down in his old location.  A higher level way of 
;    fixing this would be to have Prodigy reorder its goals so that he would try 
;    to pick it up before moving out of the way.  The ordering that needs to be 
;    changed, however, is the ordering defined by the operator PUT-DOWN.  Since I 
;    didn't want to mess with the operator, I decided to try to capture this case 
;    in a search control rule.  If the RMG is moving out of his location 
;    (presumably to put a block there), then it looks for the next object that he 
;    will need to pick up (i.e. the next "at"  on the goal stack) and checks to see 
;    if this object is currently at a location that he could move to in order to 
;    get out of the way. Since the RMG can only move to adjacent locations, the 
;    object must be adjacent to him. If so, then he should prefer this location 
;    which will have the side effect of causing him to pick up the block first so 
;    that he can move there.
; 
; 5. UNOCCUPIED-SQLOC-FOR-PICK-UP: (easy-stairs)
;       When the RMG went to pick up a block, the rule CLOSEST-SIDE-FOR-PICK-UP
;    told him to prefer locations next to the block which were closest to him.  In
;    this case there were two locations adjacent to the block which were both next
;    to the RMG.  One of them, however, was already occupied by another block.  
;    Since both locations were equally far,  the RMG should have preferred the 
;    location which was empty.  Since he had not already inferred that the empty 
;    location was vacant, however, both locations had vacant-loc as an unmatched 
;    condition and so he could see no difference between them.  Hence, I added 
;    this rule to prefer a location which is currently unoccupied.  Unless, of 
;    course, it is currently occupied by the RMG.
; 
;   

(setq *SCR-NODE-SELECT-RULES* nil)
(setq *SCR-GOAL-SELECT-RULES* 
      '((SELECT-FIRST-GOAL
  	  (lhs (and (current-node <node>)
	            (list-of-candidate-goals <node> <goals>)
	            (is-first-goal <goal> <goals>)))
           (rhs (select goal <goal>)))))

(setq *SCR-OP-SELECT-RULES* nil)
(setq *SCR-BINDINGS-SELECT-RULES*
	'((PUT-DOWN-ANYWHERE
	     (lhs (and (current-node <node>)
		       (current-goal <node> (~ (holding <ob>)))
		       (current-op <node> PUT-DOWN)
		       (known <node>
			      (and (at RMG <RMG-loc>)
	                           (next-to <RMG-loc> <new-loc>)))))
	     (rhs (select bindings 
			  (<ob> <new-loc> <RMG-loc>))))

	  (MOVE-DELETE-RMG
	      (lhs (and (current-node <node>)
			(current-goal <node> (~ (at RMG <adj-loc>)))
			(current-op <node> MOVE)
			(known <node>
			       (t-adjacent-locs <adj-loc> <to-loc>))))
	      (rhs (select bindings (<adj-loc> <to-loc>))))

))

(setq *SCR-NODE-REJECT-RULES*
       '((BLOCK-SUPPORT-RULE-1  
	    (lhs (and (candidate-node <node>)
		      (candidate-goal <node> (at <obj> <loc1>))
		      (on-goal-stack <node> (at <obj> <loc2>))
			;  DONT really need KNOWN test
		      (known <node> (under-loc <loc1> <loc2>))))
	    (rhs (reject node <node>)))

	 (DONT-CLEAR-BY-PICKING-UP
	   (lhs (and (candidate-node <node>)
		     (candidate-goal <node> (~ (at <obj> <loc>)))
		     (direct-supergoal-of <node> (~ (at <obj> <loc>))
			 (clear <obj>))))
	   (rhs (reject node <node>)))))

(setq *SCR-GOAL-REJECT-RULES* nil)
(setq *SCR-OP-REJECT-RULES* nil)
(setq *SCR-BINDINGS-REJECT-RULES* nil)
(setq *SCR-NODE-PREFERENCE-RULES* nil)
(setq *SCR-GOAL-PREFERENCE-RULES* nil)
(setq *SCR-OP-PREFERENCE-RULES* nil)
(setq *SCR-BINDINGS-PREFERENCE-RULES*
      '((TOWARDS
          (lhs (and (current-node <node>)
	 	    (current-op <node> MOVE)
	            (candidate-bindings <node> (<adj-sq2> <to-sq2>))
	            (candidate-bindings <node> (<adj-sq1> <to-sq1>))
	   	    (known <node>
			   (and (at RMG <from-loc>)
	                        (distance <from-loc> <adj-sq1> <dis1>)
	                        (distance <from-loc> <adj-sq2> <dis2>)
	             		(less-than <dis1> <dis2>)))))
	  (rhs (prefer bindings (<adj-sq1> <to-sq1>)
	    	                (<adj-sq2> <to-sq2>))))
	
        (CLOSEST-SIDE-FOR-PICK-UP
	  (lhs (and (current-node <node>)
	 	    (current-op <node> PICK-UP)
	            (candidate-bindings <node> (<b1> <b2> <bloc>))
	            (candidate-bindings <node> (<a1> <a2> <aloc>))
  	            (known <node> 
			   (and (at RMG <rmg-loc>)
    			        (distance <rmg-loc> <aloc> <dis1>)
			        (distance <rmg-loc> <bloc> <dis2>)
			        (less-than <dis1> <dis2>)))))
	  (rhs (prefer bindings (<a1> <a2> <aloc>)
	 		        (<b1> <b2> <bloc>))))

(CLOSEST-SIDE-FOR-PUT-DOWN 
    (lhs (and (current-node <node>)
   	      (current-op <node> PUT-DOWN)
	      (candidate-bindings <node> (<a> <b> <loc1>))
	      (candidate-bindings <node> (<e> <f> <loc2>))
	      (known <node> 
		     (and (at RMG <rmg-loc>)
			  (distance <rmg-loc> <loc1> <dis1>)
			  (distance <rmg-loc> <loc2> <dis2>)
			  (less-than <dis2> <dis1>)))))
    (rhs (prefer bindings (<e> <f> <loc2>)
			  (<a> <b> <loc1>))))

;(PREFER-OBJ-ALREADY-THERE
;    (lhs (and (current-node <node>)
;   	      (current-op <node> INFER-OCCUPIED-SQLOC)
;	      (current-goal <node> (occupied-sqloc <sqloc>))
;	      (candidate-bindings <node> (<sqloc> <obj1> <b> <c>))
;	      (candidate-bindings <node> (<sqloc> <obj2> <e> <f>))
;	      (known <node> (at <obj2> <sqloc>))))
;    (rhs (prefer bindings (<sqloc> <obj2> <e> <f>)
;			  (<sqloc> <obj1> <b> <c>))))


(CLOSEST-SIDE-FOR-OCCUPIED 
    (lhs (and (current-node <node>)
   	      (current-op <node> INFER-OCCUPIED-SQLOC)
	      (candidate-bindings <node> (<sqloc> <obj1>))
	      (candidate-bindings <node> (<sqloc> <obj2>))
	      (~ (equal-p <obj1> <obj2>))
	      (known <node>
		     (and (at RMG <rmg-loc>)
			  (at <obj1> <loc1>)
			  (~ (in-location <sqloc> <loc1>))  ; not there already
			  (at <obj2> <loc2>)
			  (distance <rmg-loc> <loc1> <dis1>)
			  (distance <rmg-loc> <loc2> <dis2>)
			  (less-than <dis2> <dis1>)))))
    (rhs (prefer bindings (<sqloc> <obj2>)
			  (<sqloc> <obj1>))))


 (SUPPORTING-OBJ
    (lhs (and (current-node <node>)
              (current-op <node> INFER-OCCUPIED-SQLOC)
              (candidate-bindings <node> (<sqloc> <ob1>))
	      (candidate-bindings <node> (<sqloc> <ob2>))
	      (~ (equal-p <ob1> <ob2>))
	      (known <node>
	             (and (on-goal-stack <node> (supported-loc <loc>))
		          (or (at <ob1> <ob-loc>)
			      (on-goal-stack <node> (at <ob1> <ob-loc>)))
			  (~ (in-location <sqloc> <ob-loc>))
			     			 ;  not already there
			  (under-loc <loc> <ob-loc>)))))
    (rhs (prefer bindings (<sqloc> <ob2>)
                          (<sqloc> <ob1>))))

; (ALREADY-THERE
;    (lhs (and (current-node <node>)
;              (current-op <node> INFER-OCCUPIED-SQLOC)
;              (candidate-bindings <node> (<sqloc> <ob1>))
;	      (candidate-bindings <node> (<sqloc> <ob2>))
;	      (known <node>
;	             (and (at <ob2> <ob-loc>)
;			  (in-location <sqloc> <ob-loc>)))))
;    (rhs (prefer bindings (<sqloc> <ob2>)
;                          (<sqloc> <ob1>))))


  (TOWARD-SUPERGOAL
      (lhs (and (current-node <node>)
		(current-op <node> MOVE)
		(current-goal <node> (~ (at RMG <rmg-loc>)))
		(candidate-bindings <node> (<rmg-loc> <to-sq2>))
		(candidate-bindings <node> (<rmg-loc> <to-sq1>))
		(~ (equal-p <to-sq1> <to-sq2>))
		(known <node>
		       (and (on-goal-stack <node> (at <obj> <rmg-loc>))
			    (at <obj> <loc>)
			    (distance <loc> <to-sq1> <dis1>)
			    (distance <loc> <to-sq2> <dis2>)
			    (less-than <dis1> <dis2>)
			    (~ (under-loc <to-sq1> <loc>))
			    (forall (<ob-loc>) (at <ob> <ob-loc>) 
				    (~ (in <to-sq1> <ob-loc>)))))))
				    
;			    (~ (is-occupied-sqloc <node> <to-sq1>))))))
      (rhs (prefer bindings (<rmg-loc> <to-sq1>)
		            (<rmg-loc> <to-sq2>))))


   (NEXT-TO-OBJ-BEFORE-VACATING
       (lhs (and (current-node <node>)
		 (current-op <node> MOVE)
		 (current-goal <node> (~ (at RMG <rmg-loc>)))
 		 (candidate-bindings <node> (<rmg-loc> <to-sq2>))
		 (candidate-bindings <node> (<rmg-loc> <to-sq1>))
                 (~ (equal-p <to-sq1> <to-sq2>))		 
		 (known <node>
		        (and (on-goal-stack <node> (at <obj> <rmg-loc>))
			     (at <obj> <loc>)
			     (in-location <to-sq1> <loc>)))))
       (rhs (prefer bindings (<rmg-loc> <to-sq1>)
		             (<rmg-loc> <to-sq2>))))
			     

   (UNOCCUPIED-SQLOC-FOR-PICK-UP
       (lhs (and (current-node <node>)
		 (current-op <node> PICK-UP)
		 (candidate-bindings <node> (<ob2> <ob-loc2> <rmg-loc2>))
		 (candidate-bindings <node> (<ob1> <ob-loc1> <rmg-loc1>))
		 (known <node>
			(and (is-occupied-sqloc <node> <rmg-loc2>)
			     (~ (at RMG <rmg-loc2>))))))
       (rhs (prefer bindings (<ob1> <ob-loc1> <rmg-loc1>)
	                     (<ob2> <ob-loc2> <rmg-loc2>))))

   


       ))


