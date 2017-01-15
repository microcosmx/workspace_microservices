(load-goal '(and (dr-closed dr12) (inroom robot rm1)))

(load-start-state '(
	     (arm-empty)
	     (inroom dr12 rm2)
             (inroom dr12 rm1)

	     (dr-to-rm dr12 rm1)
	     (dr-to-rm dr12 rm2)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (dr-open dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (pushable B)
             (pushable A)
             (is-object B)
             (is-object A)
             (inroom robot rm2)
             (inroom B rm2)
             (inroom A rm1)))

