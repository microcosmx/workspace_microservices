#include <math.h>
#include <stdio.h>
float power(),f(),fprime();

float compute_ideal_breadth(new_guess, length, goals, accuracy)
long int length,goals;
float	 new_guess, accuracy;
/* This routine uses Newton's Method to compute the ideal branching factor. */

{  float fgoals, guessed_branch;
   int   count;
	
	if (goals!=0) {
		count = 0; 
		fgoals = (float) goals;
		do {
			guessed_branch = new_guess;
			new_guess = guessed_branch - 
					f(guessed_branch, length, fgoals) /
				   fprime(guessed_branch, length, fgoals);
			count++;
		   } while ((fabs(guessed_branch - new_guess) > accuracy)
			    && (count < 300));
		if (count==300) new_guess = -1.0;
		/*return an error if it did not converge to a solution */
	        }
	else new_guess=0.0;
	
	return(new_guess);
}


float f(b, l, t)
float b,t;
long int l;
{return (power(b,l+1) - b -(t*b) + t);}

float fprime(b, l, t)
float b,t;
long int l;
{ float a;
  a = ( (float)(l+1) * power(b,l)  - 1.0 - t);
  return (a==0.0 ? 1.0 : a);
}

float power(b,ex)
float b;
long  int ex;
/* this function computes b^ex where ex is an integer >= 0 */
{ float res;

  for (res=b; ex>1; res *= b, ex--) ;
  return(res);
}


