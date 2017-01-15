
(load-goal '(and (inroom robot rm2) (next-to b1 b2)))

(load-start-state 
    '((arm-empty)
      (inroom robot rm1)
      (pushable b1)
      (inroom b1 rm3)
      (inroom b2 rm3)

      (is-object b1)
      (is-object b2)
      (is-room rm1)
      (is-room rm2)
      (is-room rm3)
      (is-door dr12)
      (is-door dr23)

      (dr-to-rm dr12 rm2)
      (dr-to-rm dr12 rm1)
      (dr-to-rm dr23 rm3)
      (dr-to-rm dr23 rm2)

      (connects dr12 rm1 rm2)
      (connects dr12 rm2 rm1)
      (connects dr23 rm2 rm3)
      (connects dr23 rm3 rm2)
      (dr-open dr23)
      (dr-open dr12)))

