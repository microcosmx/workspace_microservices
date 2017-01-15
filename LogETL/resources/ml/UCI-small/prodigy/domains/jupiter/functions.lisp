#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Santiago Rementeria

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



;;  
;; Jupiter Functions....
;; 


(proclaim '(special *X-OBJECT* *Y-OBJECT* *X-OBJ-DESTIN* *Y-OBJ-DESTIN*
		    *TIME-STEP* *UPD-PER-CYCLE* *THEO-F-AP-TIME*
		    *P-ELAST* *P-REST* *O-ELAST* *O-REST*))



;; This function calculates the components of the force Prodigy must apply to
;; itself in order to reach point (x,y) from point (xi,yi). The movement is 
;; two-dimensional, the environment is viscous and there is no rotational
;; motion. The force obtained is independent of Prodigy's mass. In order to
;; Prodigy's knowledge to be the same as WM's, we will supose that Stokes' law
;; does not apply.

(defun force-to-move (x y xi yi radius linear-viscosity timestep upd-per-cycle
			theo-f-ap-time fx fy)
  (if (and (every #'numberp (list x y xi yi radius linear-viscosity timestep
				  upd-per-cycle theo-f-ap-time)) 
	   (is-variable fx) (is-variable fy))
      (progn (setq *TIME-STEP* timestep)
	(setq *UPD-PER-CYCLE* upd-per-cycle)
	(setq *THEO-F-AP-TIME* theo-f-ap-time)
	(let ((x-distance (- x xi))
	      (y-distance (- y yi))
	      (force-application-time (get-force-application-time)))
	  (cond ((<= radius 0) (error "Prodigy's radius must be positive !!"))
		((minusp linear-viscosity)
		 (error "Viscosity is supposed to be positive."))
		((zerop linear-viscosity)
		 (error "If the linear viscosity of the environment is null,~
		 ~%any force will cause Prodigy to displace up to infinity."))
		((zerop force-application-time) 0);If application time -> 0
		                         	  ;force magnitude -> infinity.
                                	 	  ;We do force magnitude = 0
         		                          ;because WM does. It means
		                                  ;that Prodigy just ignores
                                                  ;this case.
		(t `(((,fx ,(* (/ force-application-time) radius
			       linear-viscosity x-distance))
		      (,fy ,(* (/ force-application-time) radius
			       linear-viscosity y-distance))))))))
                               ; Stokes' law: (* 6 pi radius)
      'no-match-attempted))



;; The WM Master does not always handle the application time it theoretically
;; should (the one chosen by the user). This function calculates the REAL time
;; the force actuates upon Prodigy, which therefore should be used by this in
;; its calculations.

(defun get-force-application-time ()
  (cond ((or (minusp *TIME-STEP*) (minusp *UPD-PER-CYCLE*)
	     (minusp *THEO-F-AP-TIME*))
	 (error "All the time constants involved must be positive !!"))
	((or (zerop *TIME-STEP*) (zerop *UPD-PER-CYCLE*))
	 (error "Prodigy must be provided with the positive non-zero~
	 ~%time constants WM is going to use."))
	((zerop *THEO-F-AP-TIME*) 0)
	(t (let* ((time-update (/ *TIME-STEP* *UPD-PER-CYCLE*))
		  (aux (/ *THEO-F-AP-TIME* time-update)))
	     (cond ((integerp aux) 
		    (if (integerp (/ *THEO-F-AP-TIME* *TIME-STEP*))
			(+ *THEO-F-AP-TIME* time-update)
			*THEO-F-AP-TIME*))
		   (t (* time-update (ceiling aux))))))))



;; POINT-BEFORE-BUMPING calculates a point in the line containing both the
;; body to push and the point it has to be pushed to, where Prodigy will move.
;; This point is calculated in a way such that the distance between the
;; centres of Prodigy and the body be greater than twice the sum of their
;; radii.

(defun point-before-bumping (x-object y-object x-obj-destin y-obj-destin 
				      radius-prod radius-obj
				      x-to-go y-to-go)
  (and (every #'numberp (list x-object y-object x-obj-destin y-obj-destin
			      radius-prod radius-obj))
       (setq *X-OBJECT* x-object)
       (setq *Y-OBJECT* y-object)
       (setq *X-OBJ-DESTIN* x-obj-destin)
       (setq *Y-OBJ-DESTIN* y-obj-destin)
       (let* ((p (point-for-alignment x-object y-object
				      x-obj-destin y-obj-destin
				      radius-prod radius-obj))
	      (x (car p))
	      (y (cadr p)))
	 (cond ((and (is-variable x-to-go) (is-variable y-to-go))
		`(((,x-to-go ,x) (,y-to-go ,y))))
	       ((and (numberp x-to-go) (numberp y-to-go)
		     (= x-to-go x) (= y-to-go y)))
	       ((and (numberp x-to-go) (numberp y-to-go)
		     (apart-enough-from-each-other x-to-go y-to-go 
						   x-object y-object 
						   radius-prod radius-obj)))
	       (t 'no-match-attempted)))))



;; This function calculates the point to which Prodigy should move to
;; be aligned with an object's current position and destination and 
;; returns a list containing the x and y coordinates of that point.

(defun point-for-alignment (oxi oyi ox oy rad-prod rad-obj)
  (if (= oxi ox)
      (do* ((y-to-move oyi (next y-to-move 1))
	    (x-to-move oxi))
	   ((apart-enough-from-each-other
	     x-to-move y-to-move oxi oyi rad-prod rad-obj)
	    (list x-to-move y-to-move)))
      (let* ((m (/ (- oyi oy) (- oxi ox)))
	     (c (- oyi (* m oxi))))
	(do* ((x-to-move oxi (next x-to-move 1))
	      (y-to-move oyi (+ (* m x-to-move) c)))
	     ((apart-enough-from-each-other
	       x-to-move y-to-move oxi oyi rad-prod  rad-obj)
	      (list x-to-move y-to-move))))))






;; POINT-TO-AVOID-OBJECT calculates a point in the line perpendicular to
;; that containing Prodigy, the body to push and the point Prodigy has to move
;; to. This point is calculated in a way such that the distance between the
;; centres of Prodigy and the body be greater than twice the sum of their
;; radii. Prodigy will move to this point in order to avoid the other object.

(defun point-to-avoid-object (x-object y-object  x-prodigy  y-prodigy
				       x-prod-destin  y-prod-destin 
				       radius-prod radius-obj x-to-go y-to-go)
  (and (every #'numberp (list x-object y-object x-prodigy y-prodigy 
			      x-prod-destin y-prod-destin
			      radius-prod radius-obj))
       (let* ((p (point-perpend-to-alignment x-object y-object
						 x-prod-destin y-prod-destin
						 radius-prod radius-obj))
	      (x (car p))
	      (y (cadr p)))
	 (cond ((and (is-variable x-to-go) (is-variable y-to-go))
		`(((,x-to-go ,x) (,y-to-go ,y))))
	       ((and (numberp x-to-go) (numberp y-to-go)
		     (= x-to-go x) (= y-to-go y)))
	       (t 'no-match-attempted)))))



(defun point-perpend-to-alignment (oxi oyi px py rad-prod rad-obj)
  (cond ((= oyi py) (do* ((x-to-move oxi)
			  (y-to-move oyi (1+ y-to-move)))
			 ((apart-enough-from-each-other
			   x-to-move y-to-move oxi oyi rad-prod rad-obj)
			  (list x-to-move y-to-move))))
	((= oxi px) (do* ((x-to-move oxi (1+ x-to-move))
			  (y-to-move oyi))
			 ((apart-enough-from-each-other
			   x-to-move y-to-move oxi oyi rad-prod rad-obj)
			  (list x-to-move y-to-move))))
	(t (let* ((n (/ (- oyi py) (- oxi px)))
		  (m (/ -1 n))
		  (c (- oyi (* m oxi))))
	     (do* ((x-to-move oxi (if (> py oyi)
				      (1+ x-to-move) (1- x-to-move)))
		   (y-to-move oyi (+ (* m x-to-move) c)))
		  ((apart-enough-from-each-other
		    x-to-move y-to-move oxi oyi rad-prod rad-obj)
		   (list x-to-move y-to-move)))))))

    

;; This function calculates the x or y coordinate of the next point to which
;; Prodigy could move, keeping the body to push between it and the objective
;; point where it must be pushed.

(defun next (coor incr)
  (cond ((> *X-OBJECT* *X-OBJ-DESTIN*) (+ coor incr))
	((< *X-OBJECT* *X-OBJ-DESTIN*) (- coor incr))  
	(t (cond ((> *Y-OBJECT* *Y-OBJ-DESTIN*) (+ coor incr))
		 ((< *Y-OBJECT* *Y-OBJ-DESTIN*) (- coor incr))
		 (t (error "The body is already where it should be !"))))))




;; FORCE-TO-BUMP calculates the components of the force Prodigy must apply to
;; itself for the previously static sphere it will collide with to be pushed
;; to the point (ox,oy). It also binds variables px and py,Prodigy's position's
;; coordinates when it stops after the impact.
;; Both bodies are supposed to be aligned with that objective point, and the
;; collision between them is considered to be "head-on", i.e. central.

(defun force-to-bump (pxi pyi oxi oyi ox oy prad orad lvisco
		      timestep upd-per-cycle theo-f-ap-time
		      pmass omass pelast oelast prest orest px py fx fy)
  (if (and (every #'numberp (list pxi pyi oxi oyi ox oy prad orad lvisco
				  timestep upd-per-cycle theo-f-ap-time
				  pmass omass pelast oelast prest orest))
	   (is-variable px) (is-variable py) (is-variable fx) (is-variable fy))
      (progn (setq *P-ELAST* pelast)
	(setq *P-REST* prest)
	(setq *O-ELAST* oelast)
	(setq *O-REST* orest)
	(setq *TIME-STEP* timestep)
	(setq *UPD-PER-CYCLE* upd-per-cycle)
	(setq *THEO-F-AP-TIME* theo-f-ap-time)
	(let* ((dist-until-collision (- (distance pxi pyi oxi oyi) prad orad))
	       (x-dist-until-collision (* dist-until-collision
					  (cos (direction pxi pyi oxi oyi))))
	       (y-dist-until-collision (* dist-until-collision
					  (sin (direction pxi pyi oxi oyi))))
	       (x-dist-to-coast (- ox oxi))
	       (y-dist-to-coast (- oy oyi))
	       (force-application-time (get-force-application-time))
	       (e (get-global-coef-of-restitution)))
	  (cond ((or (<= prad 0) (<= orad 0))
		 (error "Radii must be positive !!"))
		((or (<= pmass 0) (<= omass 0))
		 (error "Masses should be positive."))
		((minusp lvisco)
		 (error "The coefficient of linear viscosity is ~
		 ~%supposed to be positive."))
		((zerop lvisco)
		 (error "If the linear viscosity of the environment is~
		 ~%null and any force is applied, bodies will never stop."))
		((zerop force-application-time) 0)
		((illegal-case-p prad orad pmass omass oxi oyi)
		 (error "~2%The previous Warning will appear whenever~
		 ~2%      radius-obj * (mass-prod - (global-coef-restitution *~
		 ~%                                  mass-obj))~
		 ~%is greater than or equal to~
		 ~2%      mass-obj * rad-prod * (1 + global-coef-restitution)
		 ~2%"))
		(t `(((,fx ,(* prad lvisco (/ force-application-time)
			       (+ x-dist-until-collision
				  (/ (* orad x-dist-to-coast (+ pmass omass))
				     (* omass prad (+ 1 e))))))
		      (,fy ,(* prad lvisco (/ force-application-time)
			       (+ y-dist-until-collision
				  (/ (* orad y-dist-to-coast (+ pmass omass))
				     (* omass prad (+ 1 e))))))
		      (,px ,(+ pxi x-dist-until-collision 
			       (/(* orad x-dist-to-coast (- pmass (* e omass)))
				 (* omass prad (+ 1 e)))))
		      (,py ,(+ pyi y-dist-until-collision 
			       (/(* orad y-dist-to-coast (- pmass (* e omass)))
				 (* omass prad (+ 1 e)))))))))))
      'no-match-attempted))


(defun illegal-case-p (rad1 rad2 mass1 mass2 x2i y2i)
  (let ((e (get-global-coef-of-restitution)))
    (and (>= (* rad2 (- mass1 (* e mass2)))
	     (* mass2 rad1 (+ 1 e)))
	 (not (format 
	       t "~%    Warning:~%    Prodigy can only handle simple collisions i.e. those in which no \"rebound\"~
	       ~%happens. ~%    The constants involved in this particular problem -- masses - radii - coef.~
	       ~%of restitution and moduli of elasticity -- are such that Prodigy and the sphere~
	       ~%located in \(~:D  ~:D\) will collide again after the first impact.~
	       ~%    If you want Prodigy\'s plan to interact with a real or simulated environment~
	       ~%\(e.g. World Modeler\) you should change \(at least some of\) these constants. If~
	       ~%anyway you still want to go on type \"continue\" \(otherwise press any key fo -~
	       ~%llowed by \'Return\'\).~
	       ~%    In case you decide to continue, you are reminded that the numerical values~
	       ~%used from now on make no sense, and should not be accounted for !! ~2%" x2i y2i))
	 (not (equalp (read) 'continue)))))




;; Calculates the global coefficient of restitution of the collision, depending
;; on the values of the coefficients of restitution and the moduli of elasti -
;; city of the colliding spheres.

(defun get-global-coef-of-restitution ()
  (cond ((or (not (<= 0 *P-ELAST* 1)) (not (<= 0 *O-ELAST* 1))
	     (not (<= 0 *P-REST* 1)) (not (<= 0 *O-REST* 1)))
	 (error "All coefficients of restitution and moduli of ~
	 ~%elasticity must be between 0 and 1 (both included)"))
	(t (/ (+ (* *P-REST* *P-ELAST*) (* *O-REST* *O-ELAST*))
	      (+ *P-ELAST* *O-ELAST*)))))



(defun hori-path-blocked-p (x2 y2 rad2 x1 y1 rad1 x3 y3)
  (or (<= (- y1 rad1) (+ y2 rad2) (+ y1 rad1))
      (<= (- y1 rad1) (- y2 rad2) (+ y1 rad1))
      (and (< (+ y1 rad1) (+ y2 rad2)) (< (- y2 rad2) (- y1 rad1)))))



(defun vert-path-blocked-p (x2 y2 rad2 x1 y1 rad1 x3 y3)
  (or (<= (- x1 rad1) (+ x2 rad2) (+ x1 rad1))
      (<= (- x1 rad1) (- x2 rad2) (+ x1 rad1))
      (and (< (+ x1 rad1) (+ x2 rad2)) (< (- x2 rad2) (- x1 rad1)))))



(defun ordinate-of-upper-tangent-to (x-centre y-centre rad x-ini y-ini
					      x-target y-target abcise)
  (let* ((x-tg (if (or (and (< x-ini x-target) (< y-ini y-target))
		       (and (> x-ini x-target) (> y-ini y-target)))
		   (- x-centre (/ rad 
				  (sqrt (+ 1 (sq (/ (- x-target x-ini)
						    (- y-target y-ini)))))))
		   (+ x-centre (/ rad 
				  (sqrt (+ 1 (sq (/ (- x-target x-ini)
						    (- y-target y-ini)))))))))
	 (y-tg (- y-centre (* (- x-tg x-centre) (/(- x-target x-ini)
						  (- y-target y-ini))))))
    (+ y-tg (* (- abcise x-tg) (/ (- y-target y-ini)
				  (- x-target x-ini))))))



(defun ordinate-of-lower-tangent-to (x-centre y-centre rad x-ini y-ini
					      x-target y-target abcise)
  (let* ((x-tg (if (or (and (< x-ini x-target) (< y-ini y-target))
		       (and (> x-ini x-target) (> y-ini y-target)))
		   (+ x-centre (/ rad 
				  (sqrt (+ 1 (sq (/ (- x-target x-ini)
						    (- y-target y-ini)))))))
		   (- x-centre (/ rad 
				  (sqrt (+ 1 (sq (/ (- x-target x-ini)
						    (- y-target y-ini)))))))))
	 (y-tg (- y-centre (* (- x-tg x-centre) (/(- x-target x-ini)
						  (- y-target y-ini))))))
    (+ y-tg (* (- abcise x-tg) (/ (- y-target y-ini)
				  (- x-target x-ini))))))


;; This function checks whether the (straight) path beetwen the spheres 
;; centered in (x1,y1) and (x3,y3) is blocked by the one in (x2,y2) or not. 

(defun path-blocked-by-object (x2 y2 rad2 x1 y1 rad1 x3 y3)
  (cond ((and (ordered x1 y1 x2 y2 x3 y3)
	      (every #'numberp (list x2 y2 rad2 x1 y1 rad1 x3 y3)))
	 (cond ((= y1 y3) (hori-path-blocked-p x2 y2 rad2 x1 y1 rad1 x3 y3))
	       ((= x1 x3) (vert-path-blocked-p x2 y2 rad2 x1 y1 rad1 x3 y3))
	       (t (or (<= (ordinate-of-lower-tangent-to x1 y1 rad1
							x1 y1 x3 y3 x2)
			  (ordinate-of-upper-tangent-to x2 y2 rad2 
							x1 y1 x3 y3 x2)
			  (ordinate-of-upper-tangent-to x1 y1 rad1 
							x1 y1 x3 y3 x2))
		      (<= (ordinate-of-lower-tangent-to x1 y1 rad1 
							x1 y1 x3 y3 x2)
			  (ordinate-of-lower-tangent-to x2 y2 rad2
							x1 y1 x3 y3 x2)
			  (ordinate-of-upper-tangent-to x1 y1 rad1
							x1 y1 x3 y3 x2))
		      (and (< (ordinate-of-upper-tangent-to x1 y1 rad1
							    x1 y1 x3 y3 x2)
			      (ordinate-of-upper-tangent-to x2 y2 rad2
							    x1 y1 x3 y3 x2))
			   (< (ordinate-of-lower-tangent-to x2 y2 rad2 
							    x1 y1 x3 y3 x2)
			      (ordinate-of-lower-tangent-to x1 y1 rad1 x1
							    y1 x3 y3 x2)))))))
	(t nil)))



;; Checks if points (x1,y1),(x2,y2) and (x3,y3) are aligned, i.e. belong to
;; the same line. This function may be modified in order to accept as good the
;; result that the third point belongs to the line containing the first two
;; points whenever the difference between y3 and (m * x3 + c) is less than,
;; say 0.1.
  
(defun aligned (x1 y1 x2 y2 x3 y3)
  (cond ((every #'numberp (list x1 y1 x2 y2 x3 y3))
	 (or (= x1 x2 x3)
	     (and (/= x1 x2) (let* ((m (/ (- y1 y2) (- x1 x2)))
				    (c (- y1 (* m x1))))
			       (= y3 (+ (* m x3) c))))))
	(t 'no-match-attempted)))
	

	 
(defun ordered (x1 y1 x2 y2 x3 y3)
  (cond ((every #'numberp (list x1 y1 x2 y2 x3 y3))
	 (or (> x1 x2 x3)  (< x1 x2 x3) 
	     (> y1 y2 y3)  (< y1 y2 y3)))
	(t 'no-match-attempted)))



(defun apart-enough-from-each-other (x1 y1 x2 y2 rad1 rad2)
  (> (distance x1 y1 x2 y2)
     (+ 20 (* 2 (+ rad1 rad2)))))



(defun distance (x1 y1 x2 y2)
  (sqrt (+ (sq (- y2 y1)) (sq (- x2 x1)))))



(defun direction (x1 y1 x2 y2)
  (cond ((= x1 x2)
	 (cond ((> y1 y2) (/ pi -2))
	       ((< y1 y2) (/ pi 2))
	       (t (error  "A direction is defined by two points.~
	        ~%The problem of finding the former is meaningless~
		~%if the latters coincide !!"))))
	(t (let ((abs-direc (atan (/ (- y2 y1) (- x2 x1)))))
	     (cond ((> x1 x2) (cond  ((/= y1 y2) (+ abs-direc pi))
				     (t pi)))
		   (t abs-direc))))))


(defun not-overlapping (pxi pyi oxi oyi rad-prod rad-obj)
  (cond ((every #'numberp (list pxi pyi oxi oyi rad-prod rad-obj))
	 (cond ((> (sqrt (+ (sq (- pxi oxi)) 
			    (sq (- pyi oyi))))
		   (+ rad-prod rad-obj)))
	       (t (error "Prodigy and the other object are overlapping !!!"))))
	(t 'no-match-attempted)))



(defun sq (a)
  (* a a))


