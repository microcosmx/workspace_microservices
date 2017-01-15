#|
*******************************************************************************
PRODIGY Version 2.01  
Copyright 1989 by Manuela Veloso

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


;
; Manuela Veloso - Jan 22, 88
;
; This file contains the set of operators for gaussian elimination
; 
; Note: Functions are all the predicates whose name is dashed with an f
;       Ex: range-f.
;

(setq *OPERATORS* '(
		    

(TRIANGULIZE
 (params (<st>))
 (preconds
  (and
   (dimension <n> <m>)
   (pred-f <n1> <n>)
   (forall (<k>) (range-f <k> 1 <n1>)
	   (put-k-zeros <k> 0))))
 (effects
  ((add (triangular <st>)))))


; Updates an element by scaling and adding a row with the current row
(UPDATE_ELT_SC_ADD
 (params (<wrow> <wcol> <val>))
 (preconds
  (and
    (rowpivotfor <prow> <wrow> <wcol> <val>)
    (const <wrow> <wcol> <val> <c>)
    (scaled&added <prow> <c> <wrow>)
    (allcopied)))
 (effects
  ((add (clearedelt <wrow> <wcol> <val>))
   (del (allcopied)))))

; Updates an element by scaling its row
(UPDATE_ELT_SC
 (params (<wrow> <wcol> <val> <c>))
 (preconds
  (and
   (const <wrow> <wcol> <val> <c>)
   (scaledrow <wrow> <c>)))
 (effects
  ((add (clearedelt <wrow> <wcol> <val>)))))


; Updates an element just by adding two rows
(UPDATE_ELT_ADD
 (params (<wrow> <prow>))
 (preconds
  (and
   (rowpivotfor <prow> <wrow> <wcol> <val>)
   (added <prow> <wrow>)))
 (effects
  ((add (clearedelt <wrow> <wcol> <val>)))))

   
; Adds row1 and row2 and substitutes row1 by the obtained sum
(ADD_ROWS
 (params (<row1> <row2>))
 (preconds
  (and
   (dimension <n> <m>)
   (forall (<col>) (range-f <col> 1 <m>)
	   (and (mat <row1> <col> <val1>)
		(mat <row2> <col> <val2>)
		(sum-f <val1> <val2> <valsum>)
		(changed <row1> <col> <val1> <valsum>)))))
 (effects
  ((add (added <row1> <row2>)))))


; Multiplies a row by mconst and substitutes row by the obtained result
(SCALE_ROW
 (params (<row> <c>))
 (preconds
  (and
   (dimension <n> <m>)
   (forall (<col>) (range-f <col> 1 <m>)
	   (and
	    (mat <row> <col> <val>)
	    (mult-f <val> <c> <valprod>)
	    (changed <row> <col> <val> <valprod>)))))
 (effects
  ((add (scaledrow <row> <c>)))))


; Scales a row pivot <prow> and adds it to the working row <wrow>
(SCALE_ADD
 (params (<prow> <wrow> <c>))
 (preconds
  (and
   (dimension <n> <m>)
   (forall (<col>) (range-f <col> 1 <m>)
	   (and
	    (mat <prow> <col> <pval>)
	    (mat <wrow> <col> <val>)
	    (mult-f <pval> <c> <mval>)
	    (sum-f <val> <mval> <nval>)
	    (changed <wrow> <col> <nval>)))))
 (effects 
  ((add (scaled&added <prow> <c> <wrow>)))))


; Selects a working row
(WORK_OUT_ROW
 (params (<wrow> <kzeros> <val>))
 (preconds
  (and
   (row <wrow>)
   (~ (workedout <wrow>))
   (forall (<col>) (range-f <col> 1 <kzeros>)
	   (clearedelt <wrow> <col> <val>))))
 (effects
  ((add (workedout <wrow>))
   (add (put-k-zeros <kzeros> 0)))))

; Selects a row for pivoting
(CHOOSE_ROW_PIVOT
 (params (<prow> <wrow>))
 (preconds
  (and
   (row <prow>)
   (diff-f <prow> <wrow>)))
 (effects
  ((add (rowpivotfor <prow> <wrow> <wcol> <val>)))))


; Creates the right multiplicative constant to get a zero element
(CREATE_CONST0
 (params (<pval> <val> <nfac>))
 (preconds
  (and 
   (rowpivotfor <prow> <wrow> <wcol> <fval>)
   (mat <prow> <wcol> <pval>)
   (mat <wrow> <wcol> <val>)
   (div-f <val> <pval> <fac>)
   (neg-f <fac> <nfac>)))
 (effects
  ((add (const <wrow> <wcol> 0 <nfac>)))))


; Creates the right multiplicative constant to get a specific 
; element
(CREATE_CONST_GEN
 (params (<old> <new> <fac>))
 (preconds
  (and
   (mat <wrow> <wcol> <old>)
   (div-f <new> <old> <fac>)))
 (effects
  ((add (const <wrow> <wcol> <new> <fac>)))))


; Creates temporary predicates
(CREATE-TEMP-ELEMENTS
 (params (<row> <col> <old> <new>))
 (preconds
  (mat <row> <col> <old>))
 (effects 
  ((add (changed <row> <col> <new>))
   (add (newmat <row> <col> <new>)))))

; Updates all
(COPYALL
 (params ())
 (preconds
  (and
   (dimension <n> <m>)
   (forall (<col>) (range-f <col> 1 <m>)
	   (~ (newmat <row> <col> <val>)))))
 (effects
  ((add (allcopied)))))

; Substitutes old elements for temporary ones
(PERFORM-UPDATES
 (params ())
 (preconds
  (and
   (newmat <row> <col> <new>)
   (mat <row> <col> <old>)))
 (effects
  ((del (newmat <row> <col> <new>))
   (del (mat <row> <col> <old>))
   (add (mat <row> <col> <new>))
   (if (and (clearedelt <row> <col> <val>)
	    (diff-f <new> <val>))
       (del (clearedelt <row> <col> <val>))))))

))


(setq *INFERENCE-RULES* '())
