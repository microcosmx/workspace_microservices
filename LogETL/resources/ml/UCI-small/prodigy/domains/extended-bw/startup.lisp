#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Robert Joseph

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



(setq *WORLD-PATH* (pathname "/usr/prodigy/version2/domains/extended-bw/"))
(setq *load-verbose* t)

;  No functions file!

(format t "Loading domain~%")
(load-path *world-path* "domain")
(format t "Loading functions~%")
(load-path *world-path* "function")
(format t "Loading sc-rules~%")
(load-path *world-path* "sc-rules")
(format t "Running load-domain~%")
(load-domain)




