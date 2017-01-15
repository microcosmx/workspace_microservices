
%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%
% CHESS DOMAIN THEORY 
% GENERATES LEGAL MOVES
% Designed by flann@cs.orst.edu

%STATE REPRESENTATION:
%*********************
%In this domain theory each initial board state is denoted by a constant 
%such as state1.  The squares on the board are denoted by an X,Y coordinate
%with (1,1) being the lower left corner. The pieces are represented by 
%a list of their properties: their side and their type, for example
%[bishop,black] or [rook,white]. Empty squares are represented by an 
%imaginary piece called empty (essentially a null value).  With this
%representation, a board configuration is represented by 64 assertions. 
%For example, the board configuration in Figure4(a) (in Flann & Dietterich,
%1989) includes the following facts:

%on(state1,(8,1),[rook,white]).
%on(state1,(1,4),empty).
%on(state1,(4,8),[king,black]).

%The structure of the board is represented by the connected relation that
%defines how each location (Xf,Yf) on the board is connected to each 
%other location (Xt,Yt) via a vector (Dx,Dy) defined as:
%Xt = Xf + Dx,
%Yt = Yf + Dy
%Note: this is intended to be an ``operational'' predicate:
connected((Xf,Yf),(Xt,Yt),(Dx,Dy)):-
   (var(Xf),var(Yf) ->
    Xf is Xt - Dx,
    Yf is Yt - Dy,
    legal_location((Xf,Yf))
   |var(Xt),var(Yt) ->
    Xt is Xf + Dx,
    Yt is Yf + Dy,
    legal_location((Xt,Yt))
   |var(Dx),var(Dy) ->
    Dx is Xf - Xt,
    Dy is Yf - Yt
   |otherwise ->
    Xf is Xt - Dx,
    Yf is Yt - Dy
   ).

legal_location((X,Y)):-
   X > 0,
   X < 9,
   Y > 0,
   Y < 9.

%PIECE REPRESENTATION:
%*********************
%The pieces are defined by a list of properties. Each type is classified
%as either a single piece (one that can only move one square at at time)
%or a sliding piece (one that can move through open lines of squares).

% sliding pieces
sliding_piece(rook).
sliding_piece(queen).
sliding_piece(bishop).

% single pieces
single_piece(king).
single_piece(knight).
single_piece(pawn).

%%%%%%%%%%%
%We also include the fact that the two sides black and white are opposite:
opside(black,white).
opside(white,black).

%%%%%%%%%%%
%For each piece type we declare the directions that it can legally move:
% KING
legaldirection(king,( 1, 1)).
legaldirection(king,( 1,-1)).
legaldirection(king,(-1,-1)).
legaldirection(king,(-1, 1)).
legaldirection(king,( 1, 0)).
legaldirection(king,( 0, 1)).
legaldirection(king,(-1, 0)).
legaldirection(king,( 0,-1)).

% KNIGHT
legaldirection(knight,( 1, 2)).
legaldirection(knight,( 2, 1)).
legaldirection(knight,( 2,-1)).
legaldirection(knight,( 1,-2)).
legaldirection(knight,(-1,-2)).
legaldirection(knight,(-2,-1)).
legaldirection(knight,(-2, 1)).
legaldirection(knight,(-1, 2)).
	
% Bishop
legaldirection(bishop,( 1, 1)).
legaldirection(bishop,( 1,-1)).
legaldirection(bishop,(-1,-1)).
legaldirection(bishop,(-1, 1)).

% Rook
legaldirection(rook,( 1, 0)).
legaldirection(rook,( 0, 1)).
legaldirection(rook,(-1, 0)).
legaldirection(rook,( 0,-1)).

% Queen
legaldirection(queen,( 1, 1)).
legaldirection(queen,( 1,-1)).
legaldirection(queen,(-1,-1)).
legaldirection(queen,(-1, 1)).
legaldirection(queen,( 1, 0)).
legaldirection(queen,( 0, 1)).
legaldirection(queen,(-1, 0)).
legaldirection(queen,( 0,-1)).


%LEGAL MOVES
%***********
%We are now ready to declare the rule that generates legal moves:
%A legal move is a pseudo move which does not result in check for the 
%moving side

legal_move(S0,do(Op,S0),Side):-
   pseudo_move(S0,Op,Side),
   legal(S0,Op,Side).

legal(S0,Op,Side):-
  \+ in_check(do(Op,S0),Side).

%non-take moves:
%%%%%%%%%%%%%%%%

pseudo_move(S0,move(nm,SqF,SqT,[Tm,Si1],empty),Si1):-
  on(S0,SqF,[Tm,Si1]),
	  legaldirection(Tm,Direct),
  reachable(S0,Tm,SqF,SqT,Direct),
  on(S0,SqT,empty).  

%take moves:
%%%%%%%%%%%%
pseudo_move(S0,move(tm,SqF,SqT,[Tm,Si1],[Tt,Si2]),Si1):-
  on(S0,SqF,[Tm,Si1]), 
  legaldirection(Tm,Direct),
  reachable(S0,Tm,SqF,SqT,Direct),
  opside(Si1,Si2), %must be opposite side
  on(S0,SqT,[Tt,Si2]).


%%%%%%%%%%%%%
reachable(S0,Type,SqF,SqT,Direct):-
   sliding_piece(Type),
   openline(S0,SqF,SqT,Direct).

reachable(S0,Type,SqF,SqT,Direct):-
   single_piece(Type),
   connected(SqF,SqT,Direct).

%%%%%%%%%%%%
openline(S0,From,To,Direct):-
   connected(From,To,Direct).

openline(S0,From,To,Direct):-
   connected(From,To1,Direct),
   on(S0,To1,empty),
   openline(S0,To1,To,Direct).

%in_check
%%%%%%%%%

in_check(S0,Side1):-
   opside(Side1,Side2),
   on(S0,From,[Typet,Side2]),
	   on(S0,To,[king,Side1]),
   legaldirection(Typet,Direct),
   reachable(S0,Typet,From,To,Direct).

%FRAME AXIOMS
%************
%These are written for theon facts that define the initial state:

%when we have just moved out of square SqF
on(do(move(nm,SqF,SqT,Of,Ot),S0),Sq,O):-
   Sq = SqF,
   O = empty.

%when we have just moved into square SqT:
on(do(move(nm,SqF,SqT,Of,Ot),S0),Sq,O):-
   Sq = SqT,
   O = Of.

%otherwise 
on(do(move(nm,SqF,SqT,Of,Ot),S0),Sq,O):-
   on(S0,Sq,O),
   Sq \== SqF,
   Sq \== SqT.

%when we have just moved out of square SqF
on(do(move(tm,SqF,SqT,Of,Ot),S0),Sq,O):-
   Sq = SqF,
   O = empty.

%when we have just moved into square SqT:
on(do(move(tm,SqF,SqT,Of,Ot),S0),Sq,O):-
   Sq = SqT,
   O = Of.

%otherwise
on(do(move(tm,SqF,SqT,Of,Ot),S0),Sq,O):-
   on(S0,Sq,O),
   Sq \== SqF,
   Sq \== SqT.



%EXAMPLE STATE1
%*********************
%Example of state, called state1 from Flann & Dietterich, 1989 Figure 4(d)
%Suitable for use with the chess_flann_new domain theory

on(state1,(1,1),[rook,white]).
on(state1,(2,1),empty).
on(state1,(3,1),empty).
on(state1,(4,1),empty).
on(state1,(5,1),empty).
on(state1,(6,1),empty).
on(state1,(7,1),empty).
on(state1,(8,1),empty).
on(state1,(1,2),[pawn,white]).
on(state1,(2,2),empty).
on(state1,(3,2),[king,white]).
on(state1,(4,2),empty).
on(state1,(5,2),empty).
on(state1,(6,2),[pawn,white]).
on(state1,(7,2),empty).
on(state1,(8,2),empty).
on(state1,(1,3),empty).
on(state1,(2,3),[pawn,white]).
on(state1,(3,3),empty).
on(state1,(4,3),empty).
on(state1,(5,3),[queen,white]).
on(state1,(6,3),empty).
on(state1,(7,3),empty).
on(state1,(8,3),empty).
on(state1,(1,4),empty).
on(state1,(2,4),empty).
on(state1,(3,4),empty).
on(state1,(4,4),empty).
on(state1,(5,4),empty).
on(state1,(6,4),empty).
on(state1,(7,4),[knight,white]).
on(state1,(8,4),empty).
on(state1,(1,5),empty).
on(state1,(2,5),empty).
on(state1,(3,5),empty).
on(state1,(4,5),empty).
on(state1,(5,5),empty).
on(state1,(6,5),empty).
on(state1,(7,5),empty).
on(state1,(8,5),empty).
on(state1,(1,6),[pawn,black]).
on(state1,(2,6),empty).
on(state1,(3,6),[rook,black]).
on(state1,(4,6),empty).
on(state1,(5,6),empty).
on(state1,(6,6),empty).
on(state1,(7,6),empty).
on(state1,(8,6),empty).
on(state1,(1,7),empty).
on(state1,(2,7),empty).
on(state1,(3,7),empty).
on(state1,(4,7),empty).
on(state1,(5,7),empty).
on(state1,(6,7),empty).
on(state1,(7,7),[pawn,black]).
on(state1,(8,7),empty).
on(state1,(1,8),[rook,black]).
on(state1,(2,8),empty).
on(state1,(3,8),[bishop,black]).
on(state1,(4,8),empty).
on(state1,(5,8),empty).
on(state1,(6,8),empty).
on(state1,(7,8),[king,black]).
on(state1,(8,8),empty).

