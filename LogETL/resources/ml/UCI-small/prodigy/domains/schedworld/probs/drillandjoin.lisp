(load-goal '(joined A B ORIENTATION-2))

(setq *END-TIME* 3)

(load-start-state
           '((last-time 3)
             (is-bolt (B1 (1.2 cm)))
             (is-bolt (B2 (1.4 cm)))
             (last-scheduled B 0)
             (last-scheduled A 0)
             (has-hole B (1.4 cm) ORIENTATION-3)
             (temperature B COLD)
             (shape B CYLINDRICAL)
             (is-object B)
             (has-hole A (2 mm) ORIENTATION-1)
             (painted A (WATER-RES RED))
             (temperature A COLD)
             (shape A IRREGULAR)
             (is-object A)))
