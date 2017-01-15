
(load-goal '(and (surface-condition B POLISHED)
		 (has-hole B (6 mm) ORIENTATION-4)))

(setq *end-time* 3)

(load-start-state
           '((last-time 3)
             (is-bolt (B1 (2 mm)))
             (last-scheduled B 0)
             (last-scheduled A 0)
             (has-hole B (2 mm) ORIENTATION-2)
             (painted B (REGULAR RED))
             (temperature B COLD)
             (shape B UNDETERMINED)
             (is-object B)
             (surface-condition A ROUGH)
             (temperature A COLD)
             (shape A IRREGULAR)
             (is-object A)))
