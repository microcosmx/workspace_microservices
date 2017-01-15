(load-goal '(and (shape A CYLINDRICAL) (has-hole B (1 cm) ORIENTATION-2)))

(setq *end-time* 3)

(load-start-state
           '((last-time 3)
             (is-bolt (B1 (6 mm)))
             (last-scheduled B 0)
             (last-scheduled A 0)
             (surface-condition B POLISHED)
             (temperature B COLD)
             (shape B UNDETERMINED)
             (is-object B)
             (temperature A COLD)
             (shape A UNDETERMINED)
             (is-object A)))
