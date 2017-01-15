#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Steven Minton, Craig Knoblock, Dan Kuokka and Jaime Carbonell

The PRODIGY System was designed and built by Steven Minton, Craig Knoblock,
Dan Kuokka and Jaime Carbonell.  Additional contributors include Henrik Nordin,
Yolanda Gil, Manuela Veloso, Robert Joseph, Santiago Rementeria, Alicia Perez, 
Ellen Riloff, Michael Miller, and Dan Kahn.

The PRODIGY system is experimental software for research purposes only.
This software is made available under the following conditions:
1) PRODIGY will only be used for internal, noncommercial research purposes.
2) The code will not be distributed to other sites without the explicit 
   permission of the designers.  PRODIGY is available by request.
3) Any bugs, bug fixes, or extensions will be forwarded to the designers. 

Send comments or requests to: prodigy@cs.cmu.edu or The PRODIGY PROJECT,
School of Computer Science, Carnegie Mellon University, Pittsburgh, PA 15213.
*******************************************************************************|#



(setq *OPERATORS*
 '(

   (GET-ORDERED-OBJECT
    ;;
    ;; for an object on the requisition (order), decrement the 
    ;; quantity remaining, and generate one instance of it.
    ;;
    (params (<i>))
    (preconds (and (class <t> <o>)
		   (order <n> <t> <o>)
		   (decrement <n> 1 <n-1>)
		   (is-name <t> <o> <i>)))
    (effects ((del (order <n> <t> <o>))
	      (if (plusp <n-1>) (add (order <n-1> <t> <o>)))
	      (add (instance <i> <t> <o>)))))




   (ADD-NEW-OBJECT-TO-ORDER
    ;; 
    ;; for an object which is not on the requisition, 
    ;; instantiate it, and declare that it was added.
    ;;
    (params (<i>))
    (preconds (and (can-add-items-to-order <yorn>)
		   (equal-p <yorn> yes)
		   (class <t> <o>)
		   (~ (order <n> <t> <o>))
		   (~ (added <a> <t> <o>))
		   (is-name <t> <o> <i>)))
    (effects ((add (added 1 <t> <o>))
	      (add (instance <i> <t> <o>)))))




   (ADD-ANOTHER-OBJECT-TO-ORDER
    ;;
    ;; for a class of objects which have been previously 
    ;; added to the requisition, add another one.
    ;; 
    (params (<i>))
    (preconds (and (can-add-items-to-order <yorn>)
		   (equal-p <yorn> yes)
		   (class <t> <o>)
		   (~ (order <n> <t> <o>))
		   (added <a> <t> <o>)
		   (increment <a> 1 <a+1>)
		   (is-name <t> <o> <i>)))
    (effects ((del (added <a> <t> <o>))
	      (add (added <a+1> <t> <o>))
	      (add (instance <i> <t> <o>)))))




   (PLACE-BOARD-INSIDE-BP
    ;;
    ;; if the unibus load of the board's module is less than the available
    ;; unibus configuration load, and there is a bp with enough power to
    ;; support an unconfigured board, and there is a  slot within the bp 
    ;; having the same width and pinning type as the board, then place the 
    ;; board into the slot.
    ;;
    (params (<bp> <b> <bpst>))
    (preconds (and (instance <b> <bt> board)
		   (class <bt> board)
		   (pin-type <bt> board <b-pin>)
		   (width <bt> board <b-width>)
		   (pos-5  <bt> board <b-p5>)
		   (pos-15 <bt> board <b-p15>)
		   (neg-15 <bt> board <b-n15>)

		   (has-board <m> <b>)
		   (instance <m> <mt> mod)
		   (class <mt> mod)
		   (equal-p <mt> <bt>)
		   (unibus-load <mt> mod <m-load>)
		   
		   (instance <ub> unibus config)
		   (ub-load <ub> <ub-load>)
		   (greater-or-equal <ub-load> <m-load>)

		   (instance <bp> <bpt> bp)
		   (class <bpt> bp)
		   (slot <bp> <bps>)
		   (slot-number <bpt> <bpst> <bps>)
		   (~ (occupied-slot <bp> <bps>))
		   (pin-type <bpt> <bpst> <s-pin>)
		   (width <bpt> <bpst> <s-width>)
		   (equal-p <s-pin> <b-pin>)
		   (equal-p <s-width> <b-width>)
		   
		   (inside-box <bp> <bx> section  <bxs>)
		   (section <bx> <bxs>)
		   (instance <bx> <bxt> box)
		   (class <bxt> box)
		   (cabled <bp>)

		   (p5a <bx> <bxs> <s-p5>)
		   (p15a <bx> <bxs> <s-p15>)
		   (n15a <bx> <bxs> <s-n15>)
		   (greater-or-equal <s-p5> <b-p5>)
		   (greater-or-equal <s-p15> <b-p15>)
		   (greater-or-equal <s-n15> <b-n15>)
		   
		   (decrement <s-p5> <b-p5> <p5>)
		   (decrement <s-p15> <b-p15> <p15>)
		   (decrement <s-p15> <b-n15> <n15>)
		   (decrement <ub-load> <m-load> <load>)))
    (effects ((del (p5a <bx> <bxs> <s-p5>))
	      (del (p15a <bx> <bxs> <s-p15>))
	      (del (n15a <bx> <bxs> <s-n15>))
	      (del (ub-load <ub> <ub-load>))
	      (add (inside-bp <b> <bp> slot <bps>))
	      (add (occupied-slot <bp> <bps>))
	      (add (p5a <bx> <bxs> <p5>))
	      (add (p15a <bx> <bxs> <p15>))
	      (add (n15a <bx> <bxs> <n15>))
	      (add (ub-load <ub> <load>)))))


 
   (PLACE-BP-INSIDE-BOX
    ;;
    ;; if there a unibus configuration with  enough length to support
    ;; a bp, and there is a box section with sufficient space for this
    ;; backplane, place the box inside that section. 
    ;;
    (params (<bx> <bxs> <bp>))
    (preconds (and (instance <u> unibus config)
		   (instance <bp> <bpt> bp)
		   (class <bpt> bp)
		   (ub-length <u> <u-length>)
		   (unibus-length <bpt> bp <bp-length>)
		   (greater-or-equal <u-length> <bp-length>)
		   (instance <bx> <bxt> box)
		   (class <bxt> box)
		   (section <bx> <bxs>)
		   (sua <bx> <bxs> <s-su>)
		   (system-units <bpt> bp <bp-su>)
		   (greater-or-equal <s-su> <bp-su>)
		   (decrement <s-su> <bp-su> <su>)
		   (decrement <u-length> <bp-length> <length>)))
    (effects ((del (sua <bx> <bxs> <s-su>))
	      (del (ub-length <u> <u-length>))
	      (add (inside-box <bp> <bx> section <bxs>))
	      (add (ub-length <u> <length>))
	      (add (sua <bx> <bxs> <su>)))))


	      


   (CABLE-FIRST-BP-TO-POWER-SUPPLY
    ;;
    ;; if there are no bp's connected to other bps,
    ;; then cable the first backplane to the power supply.
    ;;
    (params (<bp>))
    (preconds (and (instance <bp> <bpt> bp)
		   (class <bpt> bp)
		   (instance <bx> <bxt> box)
		   (class <bxt> box)
		   (section <bx> <bxs>)
		   (inside-box <bp> <bx> section <bxs>)
		   (forall (<-bp->) (instance <-bp-> <-bpt-> bp)
			   (and (class <-bpt-> bp)
				(~ (cabled <-bp->))))

		   (instance <c> jumper cable)
		   (~ (connects  <c> <anything> <anything2>))))
    (effects ((add (cabled <bp>))
	      (add (connects <c> <bp> power-supply))
	      (add (previously-cabled-bp <bp>)))))




   (CABLE-BPS-WITH-CROSS-BOX-CABLE
    ;;
    ;; if two bps are in different boxes, 
    ;; then use a cross-box cable to connect them.
    ;; 
    (params (<c> <bp1> <bp2>))
    (preconds (and (previously-cabled-bp <bp2>)
		   (instance <bp2> <bpt2> bp)
		   (class <bpt2> bp)

		   (inside-box <bp2> <bx2> section <bxs2>)
		   (instance <bx2> <bxt2> box)
		   (class <bxt2> box)
		   (section <bx2> <bxs2>)

		   (instance <bp1> <bpt1> bp)
		   (class <bpt1> bp)
		   (not-equal-p <bp1> <bp2>)
		   (~ (cabled <bp1>))
		   
		   (inside-box <bp1> <bx1> section <bxs1>)
		   (instance <bx1> <bxt1> box)
		   (class <bxt1> box)
		   (section <bx1> <bxs1>)
		   (not-equal-p <bx1> <bx2>)

		   (instance <c> cross-box cable)
		   (~ (connects <c> <anything> <anything2>))))
    (effects ((del (previously-cabled-bp <bp2>))
	      (add (previously-cabled-bp <bp1>))
	      (add (connects <c> <bp1> <bp2>))
	      (add (cabled <bp1>)))))



   (CABLE-BPS-WITH-JUMPER-CABLE
    ;;
    ;; if two bps are in the same box,
    ;; then use a jumper cable to connect them.
    ;; 
    (params (<c> <bp1> <bp2>))
    (preconds (and (previously-cabled-bp <bp2>)
		   (instance <bp2> <bpt2> bp)
		   (class <bpt2> bp)

		   (inside-box <bp2> <bx2> section <bxs2>)
		   (instance <bx2> <bxt2> box)
		   (class <bxt2> box)
		   (section <bx2> <bxs2>)

		   (inside-box <bp1> <bx1> section <bxs1>)
		   (instance <bp1> <bpt1> bp)
		   (class <bpt1> bp)
		   (not-equal-p <bp1> <bp2>)
		   (~ (cabled <bp1>))

		   (instance <bx1> <bxt1> box)
		   (class <bxt1> box)
		   (section <bx1> <bxs1>)
		   (equal-p <bx1> <bx2>)

		   (instance <c> jumper cable)
		   (~ (connects <c> <anything> <anything2>))))
    (effects ((del (previously-cabled-bp <bp2>))
	      (add (previously-cabled-bp <bp1>))
	      (add (connects <c> <bp1> <bp2>))
	      (add (cabled <bp1>)))))


;; ***************************************************************************


   (ELABORATE-UNIBUS-CONFIG-INSTANCE
    ;;
    ;; a unibus configuration.
    ;;
    (params (<u>))
    (preconds (instance <u> unibus config))
    (effects ((add (elaborated <u> unibus config))
	      (add (ub-length <u> 600))
	      (add (ub-load <u> 19)))))


;; ***************************************************************************



   (ELABORATE-STANDARD-BOX-INSTANCE
    ;;
    ;; a full instance of a standard box.
    ;;
    (params (<b>))
    (preconds (instance <b> standard box))
    (effects ((add (elaborated <b> standard box))
	      (add (section <b> 1))
	      (add (section <b> 2))
	      (add (p5a <b> 1 10))
	      (add (p15a <b> 1 10)) 
	      (add (n15a <b> 1 10))
	      (add (sua <b> 1 2))
	      (add (p5a <b> 2 10))
	      (add (p15a <b> 2 10))
	      (add (n15a <b> 2 10))
	      (add (sua <b> 2 3)))))


;; ***************************************************************************


   (ELABORATE-REPEATER-BP-INSTANCE
    ;;
    ;; a repeater backplane/mod configuration
    ;; (to increase unibus length/load).
    ;;
    (params (<bp>))
    (preconds (and (instance <bp> repeater bp)
		   (is-name repeater mod <m>)))
    (effects ((add (elaborated <bp> repeater bp))
	      (add (instance <m> repeater mod))
	      (add (slot <bp> 1)))))



   (ELABORATE-FOUR-SLOT-BP-INSTANCE
    ;;
    ;; a full instance of a four-slot bp.
    ;;
    (params (<b>))
    (preconds (instance <b> four-slot bp))
    (effects ((add (elaborated <b> four-slot bp))
	      (add (slot <b> 1))
	      (add (slot <b> 2))
	      (add (slot <b> 3))
	      (add (slot <b> 4)))))



   (ELABORATE-NINE-SLOT-BP-INSTANCE
    ;;
    ;; a full instance of a nine-slot bp. 
    ;;
    (params (<b>))
    (preconds (instance <b> nine-slot bp))
    (effects ((add (elaborated <b> nine-slot bp))
	      (add (slot <b> 1))
	      (add (slot <b> 2))
	      (add (slot <b> 3))
	      (add (slot <b> 4))
	      (add (slot <b> 5))
	      (add (slot <b> 6))
	      (add (slot <b> 7))
	      (add (slot <b> 8))
	      (add (slot <b> 9)))))




   (ELABORATE-RK611-BP-INSTANCE
    ;;
    ;; a full instance of an rk611 bp.
    ;;
    (params (<b>))
    (preconds (instance <b> rk611 bp))
    (effects ((add (elaborated <b> rk611 bp))
	      (add (slot <b> 1))
	      (add (slot <b> 2))
	      (add (slot <b> 3))
	      (add (slot <b> 4))
	      (add (slot <b> 5))
	      (add (slot <b> 6))
	      (add (slot <b> 7))
	      (add (slot <b> 8))
	      (add (slot <b> 9)))))


;; ***************************************************************************


   (ELABORATE-REPEATER-MOD-INSTANCE 
    ;;
    ;; a repeater module.
    ;;
    (params (<m>))
    (preconds (and (instance <m> repeater mod)
		   (is-name repeater board <b>)))
    (effects ((add (elaborated <m> repeater mod))
	      (add (instance <b> repeater board))
	      (add (has-board <m> <b>)))))





   (ELABORATE-KMC11-MOD-INSTANCE
    ;;
    ;; a kmc11 mod.
    ;;
    (params (<m>))
    (preconds (and (instance <m> kmc11 mod)
		   (is-name kmc11 board <b>)))
    (effects ((add (elaborated <m> kmc11 mod))
	      (add (instance <b> kmc11 board))
	      (add (has-board <m> <b>)))))



   (ELABORATE-RK611-MOD-INSTANCE
    ;;
    ;; an rk611 mod. 
    ;;
    (params (<m>))
    (preconds (and (instance <m> rk611 mod)
		   (is-name rk611 board <b1>)
		   (is-name rk611 board <b2>)
		   (is-name rk611 board <b3>)
		   (is-name rk611 board <b4>)
		   (is-name rk611 board <b5>)))
    (effects ((add (elaborated <m> rk611 mod))
	      (add (instance <b1> rk611 board))
	      (add (instance <b2> rk611 board))
	      (add (instance <b3> rk611 board))
	      (add (instance <b4> rk611 board))
	      (add (instance <b5> rk611 board))
	      (add (has-board <m> <b1>))
	      (add (has-board <m> <b2>))
	      (add (has-board <m> <b3>))
	      (add (has-board <m> <b4>))
	      (add (has-board <m> <b5>)))))


   (ELABORATE-DEUNA-AA-MOD-INSTANCE
    (params (<m>))
    (preconds (and (instance <m> deuna-aa mod)
		   (is-name deuna-aa board <b1>)
		   (is-name deuna-aa board <b2>)))
    (effects ((add (elaborated <m> deuna-aa mod))
	      (add (instance <b1> deuna-aa board))
	      (add (instance <b2> deuna-aa board))
	      (add (has-board <m> <b1>))
	      (add (has-board <m> <b2>)))))



   (ELABORATE-DR11-W-MOD-INSTANCE
    ;;
    ;; a dr11-w mod.
    ;;
    (params (<m>))
    (preconds (and (instance <m> dr11-w mod)
		   (is-name dr11-w board <b1>)
		   (is-name dr11-w board <b2>)))
    (effects ((add (elaborated <m> dr11-w mod))
	      (add (instance <b1> dr11-w board))
	      (add (instance <b2> dr11-w board))
	      (add (has-board <m> <b1>))
	      (add (has-board <m> <b2>)))))




   (ELABORATE-DZ11-B-MOD-INSTANCE
    ;;
    ;; a dz11-b mod.
    ;;
    (params (<m>))
    (preconds (and (instance <m> dz11-b mod)
		   (is-name dz11-b board <b>)))
    (effects ((add (elaborated <m> dz11-b mod))
	      (add (instance <b> dz11-b board))
	      (add (has-board <m> <b>)))))



   (ELABORATE-LP11-MOD-INSTANCE
    ;;
    ;; an lp11 mod.
    ;;
    (params (<m>))
    (preconds (and (instance <m> lp11 mod)
		   (is-name lp11 board <b>)))
    (effects ((add (elaborated <m> lp11 mod))
	      (add (instance <b> lp11 board))
	      (add (has-board <m> <b>)))))



   (ELABORATE-UDA50-A-MOD-INSTANCE
    ;;
    ;; a uda50-a mod.
    ;;
    (params (<m>))
    (preconds (and (instance <m> uda50-a mod)
		   (is-name uda50-a board <b>)))
    (effects ((add (elaborated <m> uda50-a mod))
	      (add (instance <b> uda50-a board))
	      (add (has-board <m> <b>)))))

))




(setq *INFERENCE-RULES*
 '(


   (INFER-CONFIGURATION-COMPLETE
    ;;
    ;; configuration is completed when there are no requisitioned 
    ;; objects and all instances of objects have been elaborated 
    ;; and configured. (The elaboration is needed for efficiency).
    ;;
    (params ())
    (preconds (and (forall (<t> <o>) (class <t> <o>)
			   (~ (order <n> <t> <o>)))
		   (forall (<i> <t>) (instance <i> <t> config)
			   (elaborated <i> <t> config))
		   (forall (<i> <t>) (instance <i> <t> box)
			   (elaborated <i> <t> box))
		   (forall (<i> <t>) (instance <i> <t> bp)
			   (elaborated <i> <t> bp))
		   (forall (<i> <t>) (instance <i> <t> mod)
			   (elaborated <i> <t> mod))
		   (forall (<i> <t> <o>) (instance <i> <t> mod)
				(configured mod <i>))))
    (effects ((add (configuration-complete)))))




   (INFER-UNIBUS-CONFIGURED
    ;;
    ;; a unibus must have all modules objects configured.
    ;; 
    (params (<u>))
    (preconds (forall (<o>) (object physical <o>)
		      (forall (<i> <t>) (instance <i> <t> mod)
			      (configured <o> <i>))))
    (effects ((add (configured config <u>)))))




   (INFER-BOX-CONFIGURED
    ;;
    ;; a box is configured when all bps inside it are configured.
    ;;
    (params (<bx>))
    (preconds (and (instance <bx> <bxt> box)
		   (class <bxt> box)
		   (forall (<bp>) (inside-box <bp> <bx> section <bxs>)
			   (and (instance <bp> <bpt> bp)
				(class <bpt> bp)
				(section <bx> <bxs>)
				(cabled <bp>)))))
    (effects ((add (configured box <bx>)))))



   (INFER-BP-CONFIGURED
    ;;
    ;; a bp is configured when it is inside a box and cabled.
    ;;
    (params (<bp> <bx> <bxs>))
    (preconds (and (instance <bp> <bpt> bp)
		   (class <bpt> bp)
		   (cabled <bp>)
		   (inside-box <bp> <bx> section <bxs>)
		   (instance <bx> <bxt> box)
		   (class <bxt> box)
		   (section <bx> <bxs>)))
    (effects ((add (configured bp <bp>)))))



   (INFER-CABLE-CONFIGURED
    ;;
    ;; a cable is configured when it connects at least one bp.
    ;;
    (params (<c> <b1> <b2>))
    (preconds (and (instance <c> <ct> cable)
		   (class <ct> cable)
		   (instance <b1> <bpt> bp)
		   (connects <c> <b1> <b2>)))
    (effects ((add (configured cable <c>)))))




   (INFER-MOD-CONFIGURED
    ;;
    ;; a mod is configured when all boards are configured.
    ;;
    (params (<m>))
    (preconds (and (instance <m> <mt> mod)
		   (class <mt> mod)
		   (elaborated <m> <mt> mod)
		   (forall (<b>) (has-board <m> <b>)
			   (configured board <b>))))
    (effects ((add (configured mod <m>)))))



   (INFER-BOARD-CONFIGURED 
    ;;
    ;; a board is configured when it is inside a slot.
    ;;
    (params (<b> <bp> <bps>))
    (preconds (and (instance <b> <bt> board)
		   (class <bt> board)
		   (inside-bp <b> <bp> slot <bps>)
		   (instance <bp> <bpt> bp)
		   (class <bpt> bp)
		   (slot <bp> <bps>)))
    (effects ((add (configured board <b>)))))


))

