
(load-goal '(inroom robot rm2))


(load-start-state 
    '((arm-empty)
      (inroom robot rm1)
      (is-room rm1)
      (is-room rm2)
      (is-door dr12)

      (dr-to-rm dr12 rm1)
      (dr-to-rm dr12 rm2)

      (connects dr12 rm1 rm2)
      (connects dr12 rm2 rm1)
      (dr-open dr12)))
