;; 
;; problem:  r1-soar-prob.lisp
;;
;; 
;; Configure 10 modules (12 boards), and one box on a unibus system.
;; No more than 2 backplanes, and 1 jumper cable should be added to 
;; the requisition.
;;


(load-goal '(configuration-complete))


(load-start-state '((order 4 dz11-b mod)
		    (order 3 lp11 mod)
		    (order 1 uda50-a mod)
		    (order 1 dr11-w mod)
		    (order 1 deuna-aa mod)
		    (order 2 nine-slot bp)
		    (order 2 four-slot bp)
		    (order 2 standard box)
		    (order 1 unibus config)

		    (can-add-items-to-order yes)

		    (object mental config)
		    (object mental mod)
		    (object physical bp)
		    (object physical board)
		    (object physical box)
		    (object physical cable)

		    (class cross-box cable)
		    (class jumper cable)
		    (class standard box)
		    (class unibus config)



		    (class uda50-a board)
		    (pin-type uda50-a board s-p-c)
		    (pos-5 uda50-a board 3)
		    (neg-15 uda50-a board 3)
		    (pos-15 uda50-a board 3)
		    (width uda50-a board 6)
		    (class uda50-a mod)
		    (unibus-load uda50-a mod 1)


		    (class lp11 board)
		    (pin-type lp11 board s-p-c)
		    (pos-5 lp11 board 3)
		    (pos-15 lp11 board 3)
		    (neg-15 lp11 board 3)
		    (width lp11 board 4)
		    (class lp11 mod)
		    (unibus-load lp11 mod 1)

		    
		    (class rk611 board)
		    (pos-5 rk611 board 1)
		    (pos-15 rk611 board 1)
		    (neg-15 rk611 board 1)
		    (pin-type rk611 board rk611)
		    (width rk611 board 6)
		    (class rk611 mod)
		    (unibus-load rk611 mod 1)



		    (class deuna-aa board)
		    (pin-type deuna-aa board s-p-c)
		    (pos-5 deuna-aa board 1.5)
		    (pos-15 deuna-aa board 1.5)
		    (neg-15 deuna-aa board 1.5)
		    (width deuna-aa board 6)
		    (class deuna-aa mod)
		    (unibus-load deuna-aa mod 1)

		    
		    (class dr11-w board)
		    (pin-type dr11-w board s-p-c)
		    (pos-5 dr11-w board 3)
		    (pos-15 dr11-w board 3)
		    (neg-15 dr11-w board 3)
		    (width dr11-w board 6)
		    (class dr11-w mod)
		    (unibus-load dr11-w mod 1)


		    (class dz11-b board)
		    (pin-type dz11-b board s-p-c)
		    (pos-5 dz11-b board 3)
		    (pos-15 dz11-b board 3)
		    (neg-15 dz11-b board 3)
		    (width dz11-b board  6)
		    (class dz11-b mod)
		    (unibus-load dz11-b mod 1)


		    (class kmc11 board)
		    (pin-type kmc11 board s-p-c)
		    (pos-5 kmc11 board 5)
		    (pos-15 kmc11 board 5)
		    (neg-15 kmc11 board 5)
		    (width kmc11 board 6)
		    (class kmc11 mod)
		    (unibus-load kmc11 mod 1)		    

		    
		    (class repeater board)
		    (pin-type repeater board repeater)
		    (pos-5 repeater board 0)
		    (neg-15 repeater board 0)
		    (pos-15 repeater board 0)
		    (width repeater board repw)
		    (class repeater mod)
		    (unibus-load repeater mod -19)
		    

		    (class rk611 bp)
		    (system-units rk611 bp 2)
		    (unibus-length rk611 bp 24)
		    (pin-type rk611 slot-1 s-p-c)
		    (pin-type rk611 slot-2 s-p-c)
		    (pin-type rk611 slot-3 s-p-c)
		    (pin-type rk611 slot-4 rk611)
		    (pin-type rk611 slot-5 rk611)
		    (pin-type rk611 slot-6 rk611)
		    (pin-type rk611 slot-7 rk611)
		    (pin-type rk611 slot-8 rk611)
		    (pin-type rk611 slot-9 rk611)
		    (slot-number rk611 slot-1 1)
		    (slot-number rk611 slot-2 2)
		    (slot-number rk611 slot-3 3)
		    (slot-number rk611 slot-4 4)
		    (slot-number rk611 slot-5 5)
		    (slot-number rk611 slot-6 6)
		    (slot-number rk611 slot-7 7)
		    (slot-number rk611 slot-8 8)
		    (slot-number rk611 slot-9 9)
		    (width rk611 slot-1 4)
		    (width rk611 slot-2 6)
		    (width rk611 slot-3 6)
		    (width rk611 slot-4 6)
		    (width rk611 slot-5 6)
		    (width rk611 slot-6 6)
		    (width rk611 slot-7 6)
		    (width rk611 slot-8 6)
		    (width rk611 slot-9 4)


		    (class nine-slot bp)
		    (system-units nine-slot bp 2)
		    (unibus-length nine-slot bp 24)
		    (slot-number nine-slot slot-1 1)
		    (slot-number nine-slot slot-2 2)
		    (slot-number nine-slot slot-3 3)
		    (slot-number nine-slot slot-4 4)
		    (slot-number nine-slot slot-5 5)
		    (slot-number nine-slot slot-6 6)
		    (slot-number nine-slot slot-7 7)
		    (slot-number nine-slot slot-8 8)
		    (slot-number nine-slot slot-9 9) 
		    (pin-type nine-slot slot-1 s-p-c) 
		    (pin-type nine-slot slot-2 s-p-c)
		    (pin-type nine-slot slot-3 s-p-c)
		    (pin-type nine-slot slot-4 s-p-c)
		    (pin-type nine-slot slot-5 s-p-c)
		    (pin-type nine-slot slot-6 s-p-c)
		    (pin-type nine-slot slot-7 s-p-c)
		    (pin-type nine-slot slot-8 s-p-c)
		    (pin-type nine-slot slot-9 s-p-c)
		    (width nine-slot slot-1 4)
		    (width nine-slot slot-2 6)
		    (width nine-slot slot-3 6)
		    (width nine-slot slot-4 6)
		    (width nine-slot slot-5 6)
		    (width nine-slot slot-6 6)
		    (width nine-slot slot-7 6)
		    (width nine-slot slot-8 6)
		    (width nine-slot slot-9 4)

		    
		    (class four-slot bp)
		    (system-units four-slot bp 1)
		    (unibus-length four-slot bp 0)
		    (slot-number four-slot slot-1 1)
		    (slot-number four-slot slot-2 2)
		    (slot-number four-slot slot-3 3)
		    (slot-number four-slot slot-4 4)
		    (pin-type four-slot slot-1 s-p-c)
		    (pin-type four-slot slot-2 s-p-c)
		    (pin-type four-slot slot-3 s-p-c)
		    (pin-type four-slot slot-4 s-p-c)
		    (width four-slot slot-1 4)
		    (width four-slot slot-2 6)
		    (width four-slot slot-3 6)
		    (width four-slot slot-4 4)
		    
		    
		    (class repeater bp)
		    (system-units repeater bp 1)
		    (unibus-length repeater bp -600)
		    (slot-number repeater slot-1 1)
		    (pin-type repeater slot-1 repeater)
		    (width repeater slot-1 repw)

))


(forget-prior-object-names)
