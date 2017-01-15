/* ======================================================================
   David W. Aha
   March, 1988
   Instance creation routine for the LED Display domain.
   7 attribute version
   Pretty lousy code!  Sorry.
   Programming language: C
   ====================================================================== */

#include <stdio.h>
#include <strings.h>

#define NUMBER_ARGS 5

/*==== Inputs ====*/
int numtrain;           /*==== Input #1 ====*/
unsigned seed;          /*==== Input #2 ====*/
char outputfile[100];   /*==== Input #3 ====*/
int percentnoise;       /*==== Input #4: usually set to 10 (percent) ====*/

/*==== Other global values ====*/
int noisy[7];            /* Contains boolean values (attribute noise) */
int instance[10][7];     /* Original 10 instances */
int n = 0;               

/* ====================================================================== 
   Main 
   ====================================================================== */
main (argc, argv)
   int argc;
   char *argv[];
{
   void createworld();
   
   if (argc != NUMBER_ARGS)
     { printf("Arguments: numtrain seed outputfile noise.\n");
       printf(" numtrain is the number of training instances requested.\n");
       printf(" seed is a integer seed for the random number generator.\n");
       printf(" outputfile: output file name for the generated instances.\n");
       printf(" noise is the percent probability of noise per attribute.\n");
       printf(" (usually set to 10%...and reported by the program)\n");
     }
   else
    { numtrain = atoi(argv[1]);
      seed = atoi(argv[2]);
      strcpy(outputfile,argv[3]);
      percentnoise = atoi(argv[4]);
      createworld();
    }
}

/* ======================================================================
   Getting the instances. 
   ====================================================================== */
void createworld()
{       
   FILE *fopen(), *fout;

   int select_noisy_indexes(),
       noisy_index();
   int count, selected, i;
   int noisy_instance[7];
   float actual;
    
   srandom(seed);  

   instance[0][0] = 1;
   instance[0][1] = 1;
   instance[0][2] = 1;
   instance[0][3] = 0;
   instance[0][4] = 1;
   instance[0][5] = 1;
   instance[0][6] = 1;
   
   instance[1][0] = 0;
   instance[1][1] = 0;
   instance[1][2] = 1;
   instance[1][3] = 0;
   instance[1][4] = 0;
   instance[1][5] = 1;
   instance[1][6] = 0;

   instance[2][0] = 1;
   instance[2][1] = 0;
   instance[2][2] = 1;
   instance[2][3] = 1;
   instance[2][4] = 1;
   instance[2][5] = 0;
   instance[2][6] = 1;

   instance[3][0] = 1;
   instance[3][1] = 0;
   instance[3][2] = 1;
   instance[3][3] = 1;
   instance[3][4] = 0;
   instance[3][5] = 1;
   instance[3][6] = 1;

   instance[4][0] = 0;
   instance[4][1] = 1;
   instance[4][2] = 1;
   instance[4][3] = 1;
   instance[4][4] = 0;
   instance[4][5] = 1;
   instance[4][6] = 0;

   instance[5][0] = 1;
   instance[5][1] = 1;
   instance[5][2] = 0;
   instance[5][3] = 1;
   instance[5][4] = 0;
   instance[5][5] = 1;
   instance[5][6] = 1;

   instance[6][0] = 1;
   instance[6][1] = 1;
   instance[6][2] = 0;
   instance[6][3] = 1;
   instance[6][4] = 1;
   instance[6][5] = 1;
   instance[6][6] = 1;

   instance[7][0] = 1;
   instance[7][1] = 0;
   instance[7][2] = 1;
   instance[7][3] = 0;
   instance[7][4] = 0;
   instance[7][5] = 1;
   instance[7][6] = 0;

   instance[8][0] = 1;
   instance[8][1] = 1;
   instance[8][2] = 1;
   instance[8][3] = 1;
   instance[8][4] = 1;
   instance[8][5] = 1;
   instance[8][6] = 1;

   instance[9][0] = 1;
   instance[9][1] = 1;
   instance[9][2] = 1;
   instance[9][3] = 1;
   instance[9][4] = 0;
   instance[9][5] = 1;
   instance[9][6] = 1;

   fout = fopen(outputfile,"w");
   for(count=0; count<numtrain; count++)
    { select_noisy_indexes();
      selected = (random() % 10);
      for(i=0; i<7; i++)
	{ noisy_instance[i] = instance[selected][i];
          if (noisy[i])
            noisy_instance[i] = !noisy_instance[i];
	}
      fprintf(fout,"%d,%d,%d,%d,%d,%d,%d,%d\n",
                   noisy_instance[0],
                   noisy_instance[1],
                   noisy_instance[2],
                   noisy_instance[3],
                   noisy_instance[4],
                   noisy_instance[5],
                   noisy_instance[6],
                   selected);
    }
   
   actual = 100.0 * (float)n/(float)(7*numtrain);
   printf("Percent Noise: Requested %d, Actual %f\n",percentnoise,actual);
   fclose(fout);
}

/* ======================================================================
   Sets up noisy indexes, based on the required number of instances and
   the percentage of noise required.
   ====================================================================== */
int select_noisy_indexes()
{
   int i;
   float actual;

   for(i=0; i<7 ;i++)
     { noisy[i] = 0;
       if ((1 + (random() % 100)) <= percentnoise)
         { noisy[i] = 1;
           n++;
         }
     }
}
