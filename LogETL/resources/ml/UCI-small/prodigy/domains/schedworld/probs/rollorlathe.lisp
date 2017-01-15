;******* VERSION 1-1 ******* Locked by nobody *******

(load-goal '(and (shape A CYLINDRICAL) (surface-condition A polished)))

(setq *END-TIME* 3)
(load-start-state
 '((shape A undetermined)
   (last-scheduled A 0)
   (has-clamp POLISHER)
   (temperature A COLD)
   (is-object A)
   (is-object B)
   (is-object C)
   (scheduled B LATHE 1)
   (scheduled B POLISHER 2)
   (scheduled C ROLLER 1)
   (last-time 3)))


