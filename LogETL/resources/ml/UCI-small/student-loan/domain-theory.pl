never_left_school(Student) :-
  longest_absense_from_school(Student,Units),
  6 > Units.

enrolled_in_more_than_n_units(Student,N) :-
  enrolled(Student,School,Units),
  school(School),
  Units > N.

no_payment_due(Student) :-
  continuously_enrolled(Student).
no_payment_due(Student) :-
  eligible_for_deferment(Student).

continuously_enrolled(Student) :-
  never_left_school(Student),
  enrolled_in_more_than_n_units(Student,5).

eligible_for_deferment(Student) :-
  military_deferment(Student).
eligible_for_deferment(Student) :-
  peace_corps_deferment(Student).
eligible_for_deferment(Student) :-
  financial_deferment(Student).
eligible_for_deferment(Student) :-
  student_deferment(Student).
eligible_for_deferment(Student) :-
  disability_deferment(Student).

military_deferment(Student) :-
  enlist(Student,Org),
  armed_forces(Org).

peace_corps_deferment(Student) :-
  enlist(Student,Org),
  peace_corps(Org).
  
financial_deferment(Student) :-
  filed_for_bankrupcy(Student).
financial_deferment(Student) :-
  unemployed(Student).

student_deferment(Student) :-
  enrolled_in_more_than_n_units(Student,11).

disability_deferment(Student) :-
  disabled(Student).

