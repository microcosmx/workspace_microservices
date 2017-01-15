
#|============================================================================

		  READ IN LISP UCI and C4.5 DATA FILES

  $Id: read-data.cl,v 1.2 1995/04/13 00:50:23 stefanos Exp $

  Last Edited: Apr 12/95 19:40 CDT by stefanos@batmobil (Stefanos Manganaris)

  Written by Stefanos Manganaris, Computer Sciences, Vanderbilt University.
  stefanos@vuse.vanderbilt.edu
  http://www.vuse.vanderbilt.edu/~stefanos/stefanos.html

  PURPOSE.  This code reads UCI and C4.5 data files directly into LISP.
  Instances are transformed into any form, as specified by a user-defined
  LISP function.  One typically has one such function for each learner.
  Functions can also be written to extract features or otherwise manipulate
  the data.  

  EXAMPLE.  Suppose a learner XYZ expects instances in this format:

  (class ((attr_1 . val_1) (attr_2 . val_2)  ... ))

  Then,

  (defun make-XYZ-instance (class attr-values)
    "Returns an instance in CLASS with ATTR-VALUES, in format appropriate
  for the XYZ learner."
    (cons class (list (loop
			for v in attr-values and i from 1
			collect (cons (format nil "attr_~D" i) v)))))

  To read the tic-tac-toe endgame UCI database for the XYZ learner, do

  (setq *instances* (read-data-file "tic-tac-toe.data" #'make-XYZ-instance))

  Save *instances* in a file, if you need to.
  Change `make-XYZ-instance' to suit the needs of your learner.

  Note, some UCI databases show the class as the first (rather than the last)
  attribute.  With such databases, `make-XYZ-instance' should use its class
  argument as the value of the last attribute of the instance; for the class,
  it should use (car attr-values).

============================================================================|#

(in-package "USER")

(defvar *eol* nil)

#|____________________________________________________________Sat Feb  4/95____

   Function  - READ-DATA-FILE

       Reads the UCI or C4.5 FILE and returns a list of instances.  Each
   instance is created by supplying its class and attribute values to
   MAKE-INSTANCE-F.  Files are expected to contain one instance per line.
   Each line should contain the values of the attributes in order followed
   by the class, with all entries separated by commas.     

   Note:
   * Some UCI databases do not follow the file conventions exactly (see also
     the note at the example).
   * The list of instances is returned in reverse order.
   * Spaces are not allowed as part of class names or values.
   * Make sure there is a new line before EOF.

   Inputs    -> file make-instance-f

   Returns   -> list of instances

   History   -> 
     Sat Feb  4/95: Created 
_______________________________________________________________________Stef__|#

(defun read-data-file (file make-instance-f)
  "Args: file make-instance-f
   Reads the UCI or C4.5 FILE and returns a list of instances."
  (let ((instances nil)
	(last-token nil))
    (multiple-value-bind (f-comma commap)
	(get-macro-character #\,)
      (set-macro-character #\, #'comma-reader nil)
      (set-macro-character #\newline #'newline-reader nil)
      (with-open-file (stream file :direction :input)
	(loop
	  (setq *eol* nil)
	  (setq last-token
		(do ((token (read stream t)
			    (read stream t))
		     (attribute-values nil))
		    (*eol*
		     (if last-token
			 (push (funcall make-instance-f
					last-token
					attribute-values)
			       instances))
		     (return token))
		  (if last-token
		      (setq attribute-values
			    (nconc attribute-values
				   (cons last-token nil))))
		  (setq last-token token)))
	  (if (null last-token)
	      (return))))
      (set-macro-character #\, f-comma commap)
      (set-syntax-from-char #\newline #\newline))
    (return-from read-data-file instances)))


#|____________________________________________________________Sat Feb  4/95____

   Function  - COMMA-READER

       Special reader function for comma characters in UCI and C4.5 files.

   Inputs    -> stream char

   Returns   -> 

   History   -> 
     Sat Feb  4/95: Created 
_______________________________________________________________________Stef__|#

(defun comma-reader (stream char)
  "Args: stream char
   Special reader function for comma characters in UCI and C4.5 files."
  (declare (ignore stream char))
  (values))


#|____________________________________________________________Sat Feb  4/95____

   Function  - NEWLINE-READER

       Special reader function for newline characters in UCI and C4.5 files.

   Inputs    -> stream char

   Returns   -> 

   History   -> 
     Sat Feb  4/95: Created 
_______________________________________________________________________Stef__|#

(defun newline-reader (stream char)
  "Args: stream char
   Special reader function for newline characters in UCI and C4.5 files."
  (declare (ignore char))
  (setq *eol* t)
  (read stream nil nil t))

;; EOF
