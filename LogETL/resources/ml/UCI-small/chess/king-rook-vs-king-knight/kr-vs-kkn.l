; (* JSchlimmer 12 Dec 1985 *) attributes for chess endgame (king and
;	knight versus king and rook) from (Quinlan 1979).
; (* JSchlimmer 13 Dec 1985 *) Gen-board.l generates board positions for
;	use by the leapfrog program in classifying King-Knight vs King-Rook
;	endgames where the Knight is pinned, the Rook cannot be left
;	vulnerable, and it is King-Knight's turn to move.
; (* JSchlimmer 28 Jan 1986 *) Added the functions gen-can-mv and
;	gen-cannot-mv which represent generating boards where the black
;	king can move adjacent to the knight and boards where the black
;	king cannot.
; (* JSchlimmer 29 Jan 1986 *) Added the additional values for relationship
;	between squares -- on same rectilinear line, on same diagonal, or
;	other.
; (* JSchlimmer 3 Nov 87 *) Converted the file to Common Lisp
; (* JSchlimmer 18 Feb 88 *) Added global variable which names the class
;	variable.  E.g., if *class-name* = 'class, then gen-eg returns what
;	it always did; if *class-name* = distance-bk-kn, then gen-eg
;	returns an example with class based on this attribute, and the
;	class becomes an attribute itself.
; (* JSchlimmer 3 Oct 88 *) Added interface functions to generate files of
;	observations in Quinlan's format.

; ----------------------------------------------------------------------
;		      G L O B A L   V A R I A B L E S
; ----------------------------------------------------------------------

;*class-name*:
;   This variable controls which of the attribute-values serves as the
; class attribute.  As Doug Fisher as pointed out, any of the attributes
; could serve as the class attribute, and by replicating a natural domain
; in this way, several, viable artificial domains can be generated.
; Therefore this variable can take on the values of class (safe/lost),
; dist-bk-wk, dist-bk-rk, ... , rel-bk-wk, ... , bk-type, ... , kn-type.

(defvar *class-name* 'class)

; ----------------------------------------------------------------------
;	    D A T A   S T R U C T U R E   D E F I N I T I O N S
; ----------------------------------------------------------------------

;piece = (x y color type)
(defstruct piece x y color type)

;instance = (class-name <<attribute-name . value>>)
(defstruct (obs
	     (:print-function print-obs))
  class attribute-values)

; ----------------------------------------------------------------------
;			 M A I N   F U N C T I O N
; ----------------------------------------------------------------------

(defun gen-obs ()
  (pieces-to-board (gen-pieces)))

; (* JSchlimmer 13 Dec 1985 *) gen-pieces generates a random board
; position satisfying the constraints listed above.
; (* JSchlimmer 3 Nov 87 *) Changed to leave the conversion to
; symbolic attributes to another function (pieces-to-board).

(defun gen-pieces ()
  (let* ((b-king (make-piece :x (1+ (random 8)) :y (1+ (random 8))
			     :color 'black :type 'king))
	 (w-rook (choose-rook b-king))
	 (b-knight (choose-knight b-king w-rook))
	 (w-king (choose-white-king b-king b-knight w-rook)))
    (list b-king b-knight w-king w-rook)))

; To choose a location for the white rook, first decide whether the
; rook will be on the same row or column as the king.  Then, decide
; where on the row or column it will be.  It must be at least 2
; squares away in order for the knight to be pinned.

; ----------------------------------------------------------------------
;		      C H O I C E   F U N C T I O N S
; ----------------------------------------------------------------------

(defun choose-rook (b-king)
  (cond ((= (random 1) 0)		; row
	 (make-piece :x
		     (rand-car (remove (1- (piece-x b-king))
				       (remove (piece-x b-king)
					       (remove (1+ (piece-x b-king))
						       '(1 2 3 4 5 6 7 8)))))
		     :y (piece-y b-king)
		     :color 'white :type 'rook))
	(t				; column
	 (make-piece :x (piece-x b-king)
		     :y
		     (rand-car (remove (1- (piece-y b-king))
				       (remove (piece-y b-king)
					       (remove (1+ (piece-y b-king))
						       '(1 2 3 4 5 6 7 8)))))
		     :color 'white :type 'rook))))

; To choose a location for the knight, put it somewhere between the
; king and the rook.

(defun choose-knight (b-king w-rook)
  (cond ((= (piece-x b-king) (piece-x w-rook)) ; they are on the same row
	 (make-piece :x (piece-x b-king)
		     :y
		     (rand-car (open-locs (piece-y b-king) (piece-y w-rook)))
		     :color 'black :type 'knight))
	((= (piece-y b-king) (piece-y w-rook)) ;they are on the same column
	 (make-piece :x
		     (rand-car (open-locs (piece-x b-king) (piece-x w-rook)))
		     :y (piece-y b-king)
		     :color 'black :type 'knight))
	(t (error "The black king and white rook are unaligned."))))

(defun choose-white-king (b-king b-knight w-rook)
  (let ((col (1+ (random 8))) (row (1+ (random 8))))
    (cond ((occupied-p col row (list b-king b-knight w-rook))
	   (choose-white-king b-king b-knight w-rook))
	  ((between-p col row b-knight w-rook) ; not a pin
	   (choose-white-king b-king b-knight w-rook))
	  ((and (< (abs (- col (piece-x b-king))) 2) ; too close to king
		(< (abs (- row (piece-y b-king))) 2))
	   (choose-white-king b-king b-knight w-rook))
	  ((and (eq (square-type b-king) 'corner) ; no stalemates
		(or (= (abs (- (piece-x b-king) (piece-x b-knight))) 1)
		    (= (abs (- (piece-y b-king) (piece-y b-knight))) 1))
		(or (< (abs (- (piece-x b-king) col)) 2)
		    (< (abs (- (piece-y b-king) row)) 2)))
	   (choose-white-king b-king b-knight w-rook))
	  (t (make-piece :x col :y row :color 'white :type 'king)))))

(defun rand-car (l)
    (nth (random (length l)) l))

; ----------------------------------------------------------------------
;			  C O M P U T A T I O N S
; ----------------------------------------------------------------------

; (* JSchlimmer 5 Nov 87 *) open-locs returns a list of the integers
; between position1 and position2 exclusively. 

(defun open-locs (position1 position2)
  (let ((smallest (1+ (min position1 position2)))
	(locs nil))
    (dotimes (i (1- (abs (- position1 position2))))
	     (push (+ i smallest) locs))
    locs))

; (* JSchlimmer 5 Nov 87 *) square-type indicates whether the piece is
; on a boundary square or somewhere in the middle.  It returns either
; 'corner, 'edge, or 'open.

(defun square-type (piece)
  (cond ((or (= (piece-x piece) 1) (= (piece-x piece) 8))
	 (cond ((or (= (piece-y piece) 1) (= (piece-y piece) 8)) 'corner)
	       (t 'edge)))
	((or (= (piece-y piece) 1) (= (piece-y piece) 8)) 'edge)
	(t 'open)))

(defun pieces-to-board (pieces)
  (let* ((avs (pieces-to-board-1 pieces))
	 (class (assoc *class-name* avs)))
    (make-obs :class (cdr class)
	      :attribute-values (delete class avs))))

(defun pieces-to-board-1 (pieces)
  (let ((avs (pieces-to-attributes pieces)))
    `((class . ,(safe-p avs)) ,@avs)))
	 
; (* JSchlimmer 14 Dec 1985 *) Convert pieces to attribute value
;	representation.  The assumed order of the attributes follows that
;	listed later in the file.
; (* JSchlimmer 28 Jan 1986 *) Changed this conversion to be minimal -- only
;	the distances and the piece square types are listed.

(defun pieces-to-attributes (pieces)
  (let (bk kn wk rk)
    (dolist (p pieces)
	    (case (piece-type p)
		  (king (case (piece-color p) (black (setf bk p))
			      (white (setf wk p))))
		  (rook (setf rk p)) (knight (setf kn p))))
    `((distance-bk-kn . ,(king-moves bk kn))
      (distance-bk-rk . ,(king-moves bk rk))
      (distance-bk-wk . ,(king-moves bk wk))
      (distance-wk-kn . ,(king-moves wk kn))
      (distance-wk-rk . ,(king-moves wk rk))
      (distance-rk-kn . ,(king-moves rk kn))
      (rel-bk-kn . ,(board-relationship bk kn))
      (rel-bk-rk . ,(board-relationship bk rk))
      (rel-bk-wk . ,(board-relationship bk wk))
      (rel-wk-kn . ,(board-relationship wk kn))
      (rel-wk-rk . ,(board-relationship wk rk))
      (rel-rk-kn . ,(board-relationship rk kn))
      (bk-type . ,(square-type bk))
      (kn-type . ,(square-type kn))
      (wk-type . ,(square-type wk))
      (rk-type . ,(square-type rk)))))

(defun king-moves (piece1 piece2)
  (case (max (abs (- (piece-x piece1) (piece-x piece2)))
	     (abs (- (piece-y piece1) (piece-y piece2))))
	(0 'zero)
	(1 'one)
	(2 'two)
	(t 'three)))

(defun board-relationship (piece1 piece2)
  (cond ((or (= (piece-x piece1) (piece-x piece2))
	     (= (piece-y piece1) (piece-y piece2)))
	 'rect)
	((= (abs (- (piece-x piece1) (piece-x piece2)))
	    (abs (- (piece-y piece1) (piece-y piece2))))
	 'diag)
	(t 'other)))

; ----------------------------------------------------------------------
;		   P R E D I C A T E   F U N C T I O N S
; ----------------------------------------------------------------------

; (* JSchlimmer 5 Nov 87 *) between-p is non-nil iff the square
; denoted by x and y is between piece1 and piece2.

(defun between-p (x y piece1 piece2)
  (or (and (= x (piece-x piece1) (piece-x piece2))
	   (or (and (> y (piece-y piece1)) (< y (piece-y piece2)))
	       (and (> y (piece-y piece2)) (< y (piece-y piece1)))))
      (and (= y (piece-y piece1) (piece-y piece2))
	   (or (and (> x (piece-x piece1)) (< x (piece-x piece2)))
	       (and (> x (piece-x piece2)) (< x (piece-x piece1)))))))

; (* JSchlimmer 13 Dec 1985 *) can-move? can the black king move adjacent to
;	the black knight without moving into check?
; (* JSchlimmer 30 Jan 1986 *) Missed the possiblity that the king might be
;	next to the knight but be unable to make another move and still be
;	next to it.

(defun can-move-p (attribute-values)
  (cond ((cmp-test distance-bk-kn (three))
	 nil)				; bk is too far away
	((and (cmp-test bk-type   (edge corner))
	      (cmp-test kn-type   (edge))
	      (cmp-test rel-wk-kn (rect))) ; wk may keep bk away
	 (cond ((and (cmp-test distance-wk-kn (one))
		     (cmp-test distance-bk-kn (two)))
		nil)			; bk can't move next to kn
	       ((and (cmp-test distance-wk-kn (two))
		     (cmp-test distance-bk-kn (one)))
		nil)			; bk can only move away
	       (t t)))			; wk doesn't crowd bk
	(t t)))

(defmacro cmp-test (key clist)
  `(member (cdr (assoc ',key attribute-values)) ',clist))

; (* JSchlimmer 5 Nov 87 *) occupied-p is non-nil iff one of pieces is
; on the square denoted by x and y.

(defun occupied-p (x y pieces)
  (cond ((null pieces) nil)
	((and (= x (piece-x (car pieces))) (= y (piece-y (car pieces)))))
	(t (occupied-p x y (cdr pieces)))))

(defun safe-p (attribute-values)
  (if (and (can-move-p attribute-values)
	   (cmp-test distance-wk-kn (two three)))
      'safe
    'lost))

; ----------------------------------------------------------------------
;		       P R I N T   F U N C T I O N S
; ----------------------------------------------------------------------

; print-obs:
;   Called whenever print tries to print an obs$.

(defun print-obs (obs stream level)
  (let ((i (* level 2)))
    (indent i stream)
    (format stream "(obs$ :class ~S" (obs-class obs))
    (indent (+ i 2) stream)
    (format stream ":attribute-values (~S"
	    (car (obs-attribute-values obs)))
    (dolist (av (cdr (obs-attribute-values obs)))
	    (indent (+ i 3 18) stream)
	    (prin1 av))
    (format stream "))")))

; print-quinlan:
;   Prints an obs$ in Quinlan's data format.

(defun print-quinlan (obs &optional (stream *standard-output*)
			  &aux (*print-case* :downcase))
  (format stream "~&~S" (obs-class obs))
  (dolist (av (obs-attribute-values obs))
	  (format stream ",~S" (cdr av))))

; print-quinlan-to-file:
;   Prints n obs$ to file fname in Quinlan's data format.

(defun print-quinlan-to-file (fname n)
  (with-open-file (fp fname :direction :output :if-exists :supersede)
		  (dotimes (i n)
			   (print-quinlan (gen-obs) fp))
		  (format fp "~%")))

; indent:
;   Starts on a newline and indents the given number of spaces.

(defun indent (n stream)
  (format stream "~&")
  (dotimes (i n) (format stream " ")))

; ----------------------------------------------------------------------
;		    L O A D - T I M E   C O M M A N D S
; ----------------------------------------------------------------------

(defun seed (&optional n)
  (or n (setf n (mod (get-internal-run-time) 1000)))
  (dotimes (i n) (random 2)))

(format t "~&; Seeding the random number generator")
(seed)

(format t "~&; Generating 100 observations.~%; Saving to file `knight-pins'~%")
(print-quinlan-to-file "knight-pins" 100)
