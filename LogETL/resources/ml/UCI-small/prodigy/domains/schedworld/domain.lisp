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



(setq *OPERATORS* '(

(POLISH
  (params (<obj-p> <time-p> <prev-time-p>))
  (preconds
    (and
      (is-object <obj-p>)
      (or (clampable <obj-p> POLISHER)
	  (shape <obj-p> RECTANGULAR))
;      (~ (painted <obj-p> <paint>))
      (last-scheduled <obj-p> <prev-time-p>)
      (later <time-p> <prev-time-p>)
      (idle POLISHER <time-p>)))
  (effects (
    (del (surface-condition <obj-p> <*7-p>))
    (add (surface-condition <obj-p> POLISHED))
    (del (last-scheduled <obj-p> <prev-time-p>))
    (add (last-scheduled <obj-p> <time-p>))
    (add (scheduled <obj-p> POLISHER <time-p>)))))

(ROLL
  (params (<obj-r> <time-r> <prev-time-r>))
  (preconds
    (and
      (is-object <obj-r>)
      (last-scheduled <obj-r> <prev-time-r>)
      (later <time-r> <prev-time-r>)
      (idle ROLLER <time-r>)))
  (effects (
     (del (shape <obj-r> <old-shape-r>))
     (del (temperature <obj-r> <old-temp-r>))
     (del (last-scheduled <obj-r> <prev-time-r>))
     (del (surface-condition <obj-r> <*1-r>))
     (del (painted <obj-r> <*2-r>))
     (del (has-hole <obj-r> <*3-r> <*4-r>>))    
     (add (temperature <obj-r> HOT))
     (add (shape <obj-r> CYLINDRICAL))
     (add (last-scheduled <obj-r> <time-r>))
     (add (scheduled <obj-r> ROLLER <time-r>)))))


(LATHE
  (params (<obj-l> <time-l> <shape-l> <prev-time-l>))
  (preconds
   (exists (<time-l>) (is-time <time-l>)
    (and
      (is-object <obj-l>)
      (last-scheduled <obj-l> <prev-time-l>)
      (later <time-l> <prev-time-l>)
      (idle LATHE <time-l>)
      (shape <obj-l> <shape-l>))))
  (effects (
     (del (shape <obj-l> <shape-l>))
     (del (surface-condition <obj-l> <*3-l>))
     (add (surface-condition <obj-l> ROUGH))
     (del (painted <obj-l> <*4-l>))
     (del (last-scheduled <obj-l> <prev-time-l>))
     (add (shape <obj-l> CYLINDRICAL)) ; <---take this out for failure
     (add (last-scheduled <obj-l> <time-l>))
     (add (scheduled <obj-l> LATHE <time-l>)))))

(GRIND
  (params (<obj-g> <time-g> <prev-time-g>))
  (preconds
    (and
      (is-object <obj-g>)
      (last-scheduled <obj-g> <prev-time-g>)
      (later <time-g> <prev-time-g>)
      (idle GRINDER <time-g>)))
  (effects (
     (del (surface-condition <obj-g> <*1-g>))
     (add (surface-condition <obj-g> SMOOTH))
     (del (painted <obj-g> <*2-g>))
     (del (last-scheduled <obj-g> <prev-time-g>))
     (add (last-scheduled <obj-g> <time-g>))
     (add (scheduled <obj-g> GRINDER <time-g>)))))

(PUNCH
  (params (<obj-u> <time-u> <hole-width-u> <orientation-u> <prev-time-u>))
  (preconds
    (and
      (is-object <obj-u>)
      (is-punchable <obj-u> <hole-width-u> <orientation-u>)
      (clampable <obj-u> PUNCH)
      (last-scheduled <obj-u> <prev-time-u>)
      (later <time-u> <prev-time-u>)
      (idle PUNCH <time-u>)
    ))
  (effects (
    (add (has-hole <obj-u> <hole-width-u> <orientation-u>))
    (del (surface-condition <obj-u> <*33-u>))
    (add (surface-condition <obj-u> ROUGH))
    (del (last-scheduled <obj-u> <prev-time-u>))
    (add (last-scheduled <obj-u> <time-u>))
    (add (scheduled <obj-u> PUNCH <time-u>)))))


(DRILL-PRESS
  (params (<obj-d> <time-d> <hole-width-d> <orientation-d> <prev-time-d>))
  (preconds
    (and
      (is-object <obj-d>)
      (is-drillable <obj-d> <orientation-d>)
; franz bug here, so commented out
;      (~ (surface-condition <obj-d> polished))
      (last-scheduled <obj-d> <prev-time-d>)
      (later <time-d> <prev-time-d>)
      (idle DRILL-PRESS <time-d>)
      (have-bit <hole-width-d>)
    ))
  (effects (
    (add (has-hole <obj-d> <hole-width-d> <orientation-d>))
    (del (last-scheduled <obj-d> <prev-time-d>))
    (add (last-scheduled <obj-d> <time-d>))
    (add (scheduled <obj-d> DRILL-PRESS <time-d>)))))



(BOLT
  (params (<obj1-b> <obj2-b> <time-b> <new-obj-b> <prev-time1-b> <prev-time2-b> <orientation-b> <width-b> <bolt-b>))
  (preconds 
      (and
        (is-object <obj1-b>)
	(is-object <obj2-b>)
	(can-be-bolted <obj1-b> <obj2-b> <orientation-b>)
	(is-bolt <bolt-b>)
        (is-width <width-b> <bolt-b>)
	(has-hole <obj1-b> <width-b> <orientation-b>)
	(has-hole <obj2-b> <width-b> <orientation-b>)
        (last-scheduled <obj1-b> <prev-time1-b>)
	(last-scheduled <obj2-b> <prev-time2-b>)
        (later <time-b> <prev-time1-b>)
	(later <time-b> <prev-time2-b>)
        (idle BOLTING-MACHINE <time-b>)
        (composite-object <new-obj-b> <orientation-b> <obj1-b> <obj2-b>)))
;	(shape <obj1-w> <shape1-w>)
;	(shape <obj2-w> <shape1-w>)
;        (composite-shape <new-shape-b> <orientation-b> <obj1-b> <obj2-b>)
  (effects (
     (del (last-scheduled <obj1-b> <prev-time1-b>))
     (del (last-scheduled <obj2-b> <prev-time2-b>))
     (add (last-scheduled <new-obj-b> <time-b>))
;     (del (shape <new-obj-b> <old-shape-*>))
;     (add (shape <new-obj-b> <new-shape-b>))
     (add (is-object <new-obj-b>))
     (del (is-object <obj1-b>))
     (del (is-object <obj2-b>))
     (add (joined <obj1-b> <obj2-b> <orientation-b>))
     (add (scheduled <new-obj-b> BOLTING-MACHINE <time-b>)))))


(WELD
  (params (<obj1-w> <obj2-w> <time-w> <new-obj-w> <prev-time1-w> <prev-time2-w> <orientation-w>))
  (preconds 
      (and
        (is-object <obj1-w>)
	(is-object <obj2-w>)
	(can-be-welded <obj1-w> <obj2-w> <orientation-w>)
        (last-scheduled <obj1-w> <prev-time1-w>)
	(last-scheduled <obj2-w> <prev-time2-w>)
        (later <time-w> <prev-time1-w>)
	(later <time-w> <prev-time2-w>)
        (idle WELDER <time-w>)
        (composite-object <new-obj-w> <orientation-w> <obj1-w> <obj2-w>)))
;	(shape <obj1-w> <shape1-w>)
;	(shape <obj2-w> <shape2-w>)
;        (composite-shape <new-shape-w> <orientation-w> <obj1-w> <obj2-w>)
  (effects (
     (del (last-scheduled <obj1-w> <prev-time1-w>))
     (del (last-scheduled <obj2-w> <prev-time2-w>))
     (add (last-scheduled <new-obj-w> <time-w>))
;     (del (shape <new-obj-w> <old-shape*3-w>))
;     (add (shape <new-obj-w> <new-shape-w>))
     (del (temperature <new-obj-w> <old-temp*>))
     (add (temperature <new-obj-w> HOT))
     (add (is-object <new-obj-w>))
     (del (is-object <obj1-w>))
     (del (is-object <obj2-w>))
     (add (joined <obj1-w> <obj2-w> <orientation-w>))
     (add (scheduled <new-obj-w> WELDER <time-w>)))))


(SPRAY-PAINT
  (params (<obj-s> <time-s> <paint-s> <prev-time-s>))
  (preconds
    (and
      (sprayable <paint-s>)
      (is-object <obj-s>)
      (shape <obj-s> <s-s>)
      (regular-shape <s-s>)
;      (primed <obj-s> <paint-s>)
      (clampable <obj-s> SPRAY-PAINTER)
      (last-scheduled <obj-s> <prev-time-s>)
      (later <time-s> <prev-time-s>)
      (idle SPRAY-PAINTER <time-s>)))
  (effects (
    (add (painted <obj-s> <paint-s>))
    (del (surface-condition <obj-s> <*2-s>))
    (del (last-scheduled <obj-s> <prev-time-s>))
    (add (last-scheduled <obj-s> <time-s>))
    (add (scheduled <obj-s> SPRAY-PAINTER <time-s>)))))

(IMMERSION-PAINT
  (params (<obj-i> <time-i> <paint-i> <prev-time-i>))
  (preconds
    (and
      (is-object <obj-i>)
;      (primed <obj-i> <paint-i>)
      (have-paint-for-immersion <paint-i>)
      (last-scheduled <obj-i> <prev-time-i>)
      (later <time-i> <prev-time-i>)
      (idle IMMERSION-PAINTER <time-i>)))
  (effects (
    (add (painted <obj-i> <paint-i>))
    (del (last-scheduled <obj-i> <prev-time-i>))
    (add (last-scheduled <obj-i> <time-i>))
    (add (scheduled <obj-i> IMMERSION-PAINTER <time-i>)))))
      




))


(setq *INFERENCE-RULES* '(

;  steve - add size restrictions...

(IS-CLAMPABLE
  (params (<obj1> <machine>))
  (preconds
    (and 
      (has-clamp <machine>)
      (temperature <obj1> COLD)))
  (effects ((add (clampable <obj1> <machine>)))))


  
(INFER-IDLE
;  (params (<obj2> <mach> <m> <time-t>))
  (params (<mach>  <time-t>))
  (preconds
     (forall (<obj2> <m>)
	     (scheduled <obj2> <m> <time-t>)
	     (not-equal <m> <mach>)))
;    (~ (exists (<obj2>) (scheduled <obj2> <mach> <time-t>)))
  (effects (
    (add (idle <mach> <time-t>)))))


))
