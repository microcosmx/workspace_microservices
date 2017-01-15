
(load-goal '(and (joined A B ORIENTATION-4)
                 (surface-condition A SMOOTH)))

(setq *end-time* 3)

(load-start-state
           '((last-time 3)
             (is-bolt (B1 (2 mm)))
             (last-scheduled B 0)
             (last-scheduled A 0)
             (temperature B COLD)
             (has-hole B (2 mm) ORIENTATION-4)
             (has-hole A (2 mm) ORIENTATION-4)
             (shape B IRREGULAR)
             (is-object B)
             (has-hole A (6 mm) ORIENTATION-2)
             (painted A (REGULAR RED))
             (temperature A COLD)
             (shape A UNDETERMINED)
             (is-object A)))
