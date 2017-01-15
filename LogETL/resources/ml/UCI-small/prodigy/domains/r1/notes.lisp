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


1. Make some of the effects of the elaboration operators static predicates.
2. Define the static predicates as functions.


Think about changing the state representation to a hash table indexed by 
the predicates true in the state, and having values of frames for each 
predicate.


(gethash predicate state) =>  (predicate 
			       (term-1 (value (domain values...)))
			       (term-2 (value (domain values...)))
			       (term-3 (value (domain values...))))


Alternatively, each predicate can maintain its own values for the entire
planning process, although this could be hairy to decipher...


(predicate
 (term-1 (state-1  (domain values...))
	 (state-2  (domain values...)))
 (term-2 (state-1  (domain values...))
	 (state-2  (domain values...))))



Right now though, the main problem is redundant predicates between states.
Craig suggested implementing static predicates as functions. 

Must find a way to map the prodigy predicates into some sort of KR. 

Given a predicate of arity n, how can we represent the relationship among 
the elements (or between the elements).

The relation among the elements is the predicate itself. 

Imagine verifying a predicate as parsing a grammar, rather than as match.
Is match a form of parsing? Match is involved in parsing. 

(order 1 jumper cable)
(1 jumper cable)




perhaps by defining a function for each static predicate which returns 
a list of binding lists for each variable term in the relation. 





eg.  

(object mental config)
(object mental mod)
(object physical board)
(object physical bp)
(object physical box)
(object physical cable)


using the relation mk-frame is transformed into ...

(object 
 (is-a (value (r1 relation))))

(board
 (object (value (r1 physical)))

(bp
 (object (value (r1 physical))))

(cable
 (object (value (r1 physical)))
 (kinds (value (r1 cross-box jumper))))

(box
 (object (value (r1 physical))))

(mod
 (object (value (r1 mental))))

(config
 (object (value (r1 mental)))
 (class (value (r1 unibus))))

(unibus 
 (class (value (r1 config))))

(cross-box 
 (type (value (r1 cable))))






In general... 



(<operator>
 (is-a (value (domain operator))))
 (params (value (domain <var1> <var2>)))
 (preconds (value (domain (and (foo bar baz)
			       (baz foo <var1>)
			       (bar <var2> baz)))))
 (effects (value (domain ((del (foo bar baz))
			  (add (foo <var2> <var1>)))))))


(<inference-rule>
 (is-a (value (domain inference-rule)))
 (params (value (domain <var1>)))
 (preconds (value (domain (and (foo <var1> baz)
			       (bar foo <var1>)))))
 (effects (value (domain ((add (baz <var1> foo)))))))



(<sc-rule>
 (is-a (value (domain search-control-rule)))
 (criticality (value (domain nil)))
 (params (value (domain <node> <goal> <oper>)))
 (lhs (value (domain (and (current-node <node>)
			  (current-goal <node> <goal>)
			  (is-first-goal <node>  <goal>)))))
 (rhs (value (domain (select goal <goal>)))))



I
(<predicate>
 (is-a (value (domain predicate))))



(<atom> 
 (is-a (value (domain atom))))






