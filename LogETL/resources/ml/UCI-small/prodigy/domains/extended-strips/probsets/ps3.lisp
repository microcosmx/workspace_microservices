; seven probs, SW1, 2 goal, 4 objs

(setq *TEST-PROBS*
    '((SS3-1 (and (dr-closed dr12) (inroom key12 rm1))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (next-to B A)
             (next-to A B)
             (unlocked dr12)
             (dr-open dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (pushable B)
             (carriable A)
             (is-object key12)
             (is-object B)
             (is-object A)
             (inroom B rm1)
             (inroom A rm1)
             (inroom key12 rm2)
             (inroom robot rm1)
             (carriable key12)
             (is-key dr12 key12)))
     (SS3-2 (and (inroom C rm1) (inroom A rm1))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (dr-closed dr12)
             (locked dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (carriable C)
             (carriable A)
             (is-object key12)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom C rm2)
             (inroom B rm1)
             (inroom A rm1)
             (inroom key12 rm2)
             (inroom robot rm2)
             (carriable key12)
             (is-key dr12 key12)))
     (SS3-3 (and (inroom key12 rm2) (inroom B rm2))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (unlocked dr12)
             (dr-closed dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (pushable D)
             (carriable B)
             (pushable A)
             (is-object key12)
             (is-object D)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom D rm1)
             (inroom C rm2)
             (inroom B rm2)
             (inroom A rm2)
             (inroom key12 rm1)
             (inroom robot rm2)
             (carriable key12)
             (is-key dr12 key12)))
     (SS3-4 (and (unlocked dr12) (inroom C rm1))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (next-to C A)
             (next-to A C)
             (dr-closed dr12)
             (locked dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (pushable C)
             (pushable A)
             (is-object key12)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom C rm1)
             (inroom B rm1)
             (inroom A rm1)
             (inroom key12 rm1)
             (inroom robot rm1)
             (carriable key12)
             (is-key dr12 key12)))
     (SS3-5 (and (inroom robot rm2) (inroom C rm1))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (next-to D A)
             (next-to A D)
             (unlocked dr12)
             (dr-closed dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (carriable C)
             (carriable A)
             (is-object key12)
             (is-object D)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom D rm1)
             (inroom C rm2)
             (inroom B rm2)
             (inroom A rm1)
             (inroom key12 rm2)
             (inroom robot rm1)
             (carriable key12)
             (is-key dr12 key12)))
     (SS3-6 (and (holding B) (next-to C A))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (dr-closed dr12)
             (locked dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (carriable C)
             (carriable A)
             (carriable B)
             (is-object key12)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom C rm1)
             (inroom B rm1)
             (inroom A rm1)
             (inroom key12 rm1)
             (inroom robot rm1)
             (carriable key12)
             (is-key dr12 key12)))

     (SS3-6A (and (holding B)(unlocked dr12))
            ((arm-empty)
             (dr-to-rm dr12 rm2)
             (dr-to-rm dr12 rm1)
             (connects dr12 rm2 rm1)
             (connects dr12 rm1 rm2)
             (dr-closed dr12)
             (locked dr12)
             (is-room rm2)
             (is-room rm1)
             (is-door dr12)
             (carriable B)
             (carriable A)
             (is-object key12)
             (is-object C)
             (is-object B)
             (is-object A)
             (inroom C rm1)
             (inroom B rm1)
             (inroom A rm1)
             (inroom key12 rm1)
             (inroom robot rm1)
             (carriable key12)
             (is-key dr12 key12)))

    ))




(setq *AUX-COMMANDS*
 '((SS2-2 (optimal-path (cadr *ALL-NODES*)))))
