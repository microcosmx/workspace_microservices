/*************************************************************************/
/*************************************************************************/
/*                                                                       */
/*                    DATA GENERATION PROGRAM/2 V1.0                     */
/*                                                                       */
/* Copyright: Powell Benedict, Larry Rendell, and the University of      */
/*            Illinois, 1990                                             */
/*                                                                       */
/* Author   : Powell Benedict                                            */
/*            Inductive Learning Group                                   */
/*            Beckman Institute for Advanced Technology and Sciences     */
/*            University of Illinois at Urbana                           */
/*                                                                       */
/* This program and any results obtained may be freely used for any      */
/* purpose provided the above author's reference is appropraitely made.  */
/* If this program is modified then that should be stated in the         */
/* reference.                                                            */
/*                                                                       */
/* Please send comments, suggestions, bug reports, and requests for      */
/* updates to:                                                           */
/*            Powell Benedict                                            */
/*            benedict@cs.uiuc.edu                                       */
/*                                                                       */
/* This program was written and compiled under Borland Turbo C++. It does*/
/* not, however, use any C++ or Borland specific code and should compile */
/* under any standard C, with the possible exception of                  */
/* READ_INPUT_PARAMETERS (see comments in that function).                */
/*************************************************************************/
/*************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <alloc.h>
#include <math.h>
#include <float.h>
#include <dos.h>
#include <string.h>

/*************************************************************************/
/*                        GLOBAL CONSTANTS                               */
/*************************************************************************/

#define A0  2.30753          /* These four constants are used for the */
#define A1  0.27061          /* approximation function which yields   */
#define B0  0.99229          /* the standard deviation for generation */
#define B1  0.04481          /* of instances.                         */
#define PI  3.141592654
#define PARM_FILE "parms.gen" /* The name of the default parm file    */

/*************************************************************************/
/*                        GLOBAL VARIABLES                               */
/*************************************************************************/

FILE *out_file,              /* Output file file variable                */
     *stat_file;             /* Statistics file variable                 */

char out_file_name[20],      /* Filename of output file                  */
     stat_file_name[20];     /* Filename of statistics file              */

int  seed[3];                /* Seeds for the random number generator    */
int  num_features,           /* Number of features in the instance space */
     num_peaks,              /* Number of peaks in the instance space    */
     num_feature_values,     /* Number of feature values per feature     */
     max_feature_value,      /* Maximum feature value for all features   */
     num_instances_per_peak, /* Number of instances per peak             */
     num_instances;          /* Total number of instances in the sample  */

int text_out_flag,           /* Do or don't print run information        */
    pos,                     /* Number of positive instances generated   */
    neg,                     /* Number of negative instances generated   */
    proto_seed,              /* Initial seed used to gen required seeds  */
    trunc_flag;              /* Allows selection of what's to be done    */
			     /* with instances whose feature values are  */
			     /* out of range.  Since we use a normal     */
			     /* distribution function we can get numbers */
			     /* outside of [0,MAX_FEATURE_VALUE].        */
			     /*   0 - Include those instances.           */
			     /*       That is, let out of range instances*/
			     /*	      through                            */
			     /*   1 - Truncate the instance.  This       */
			     /*       preserves a correct distribution   */
			     /*       but can reduce the actual number   */
			     /*       of instances generated.            */
			     /*   2 - Regenerate out of range feature    */
			     /*       values.  This preserves            */
			     /*       the correct number of              */
			     /*       instances requested, but can       */
			     /*       corrupt the distribution resulting */
			     /*       more positive instances than       */
			     /*       requested.  Note because of the    */
			     /*       randomness of generation this is   */
			     /*       the same as regenerating the whole */
			     /*       instance.                          */

int num_pos_truncated = 0;   /* Number of positive instances truncated   */
			     /* if option 1 selected above (i.e.,        */
			     /* trunc_flag == 1)                         */
int num_neg_truncated = 0;   /* Number of negative instances...          */

double stan_dev;             /* Standard deviation value for normal      */
			     /* distribution calculation                 */

double percentage;           /* Percentage of instances desired to be    */
			     /* within RADIUS of the peaks (i.e. % of    */
			     /* instances to be positive. Note this      */
			     /* value is approximated by the program,    */
			     /* the actual value may be slightly         */
			     /* different                                */

double range;                /* A n-space distance value used            */
			     /* to determine if a point is close enough  */
			     /* to a centroid to be a member of the      */
			     /* positive class                           */

double **peak_centroids,     /* Used to store peak centroids             */
       **instances;          /* Used to store instances.  Each instance  */
			     /* has, after MAKE_CLASS_ASSIGNMENTS runs,  */
			     /* a class membership value and a peak      */
			     /* membership value                         */

/*************************************************************************/
/* This section contains the function prototype definitions, broken up   */
/* into functional groups.                                               */
/*************************************************************************/

/* Memory management functions */
void *safe_malloc(int num_bytes);

/* Random number functions */
double urand(int s[3]);
double urand_range(int s[3], double min_range, double max_range);

/* Parameter input functions */
void read_input_parameters(int argc, char *argv[]);

/* Peak centroid functions */
void print_centroids(void);
void generate_peak_centroids(void);

/* Required standard deviation calculation functions */
double calc_required_standard_deviation(double range, double percentage);

/* Instance functions */
double *gen_normal_pair(double mean, double s_dev);
void generate_instances(void);

/* Class assignment functions */
int find_closest_peak(double *instance);
int instance_is_within_range(double *instance);
void make_class_assignments(void);

/* Output functions */
int in_bounds(double *instance);
void print_instances(void);
void print_statistics(void);

/************************************************************************/
/* This function insures that the memory requested exists.  If it       */
/* doesn't we just stop and inform the user.                            */
/************************************************************************/

void *safe_malloc(int num_bytes)

{
  void *mem_ptr;       /* Use a void pointer which is then cast by */
		       /* the call to this function.               */

  if (num_bytes == 0)  /* Check this first because MALLOC returns NULL */
    return(NULL);      /* if it fails.                                 */

  mem_ptr = malloc(num_bytes);

  if (mem_ptr == NULL)
    {
      printf("A memory allocation request has failed to return the\n");
      printf("requested memory.  This program will terminate.\n");
      exit(1);
    }

  return(mem_ptr);
}

/*************************************************************************/
/* This function returns a random number on the interval [0,1].  It is   */
/* taken from CACM                                                       */
/*************************************************************************/

double urand (int s[3])

{
  int z, k;

  k = s[0]/206;                            /* generate seed #1 */
  s[0] = 157*(s[0] - 206*k) - 21*k;
  if (s[0] < 0)
    s[0] = s[0] + 32363;
    
  k = s[1]/217;                            /* generate seed #2 */
  s[1] = 146*(s[1] - 217*k) - 45*k;
  if (s[1] < 0)
    s[1] = s[1] + 31727;

  k = s[2]/222;                            /* generate seed #3 */
  s[2] = 142*(s[2] - 222*k) - 133*k;
  if (s[2] < 0)
    s[2] = s[2] + 31657;

  z = s[0] - s[1];                      /* gen unif deviate */
  if (z > 706)
    z = z - 32362;
  z = z + s[2];
  if (z < 1)
    z = z + 32362;

  return (z/(double) 32363);
}

/*************************************************************************/
/* This function returns a random real on the interval [MIN_RANGE,       */
/* MAX_RANGE].                                                           */
/*************************************************************************/


double urand_range (int s[3], double min_range, double max_range)

{
  min_range -= 1.0;
  return ((max_range - min_range) * urand(s) + min_range + 1.0);
}

/*************************************************************************/
/* This function reads all input data required by this program from a    */
/* filename which is specified on the command line.  If no filename is   */
/* given it defaults to PRAMS.GEN.                                       */
/*                                                                       */
/* Note:                                                                 */
/* Only bother the user for a single seed, although the random number    */
/* generator used here needs three.  We take the one seed and use the    */
/* the turbo C++ random number generator to generate the three seeds     */
/* used by our random number generator.  When using a different C        */
/* compiler, the SRAND function (initializes the RAND generator) and     */
/* the RAND generator (returns a random number on the interval           */
/* [0,RAND_MAX] which in turbo C++ is (2^15) - 1), can either be         */
/* replaced with equivelant functions or the three seeds in SEED[] can   */
/* be prompted for.                                                      */
/*************************************************************************/

void read_input_parameters(int argc, char *argv[])

{
  FILE *parms;
  char temp[85],type[85],eq[85],val[85],parm_file_name[60],mess[80];
  int i,status,parm_flag[10],flag;
  
  if (argc == 2)
    strcpy(parm_file_name,argv[1]);
  else strcpy(parm_file_name,PARM_FILE);
  if ((parms = fopen(parm_file_name,"rt")) == NULL)
    {
      printf("+++ERROR -> Parm file not found: %s\n",parm_file_name);
      fclose(parms);
      exit(1);
    }
	
  for (i = 0; i < 10; i++)
    parm_flag[i] = 0;

  do {
    fgets(temp,82,parms);

    /* Allow 80 characters max on a single parm line */
    if (strlen(temp) >= 81)
      {
	printf("+++ERROR -> Line too long:\n     %s\n",temp);
	fclose(parms);
	exit(1);
      }

    /* If STATUS = -1 then the line was blank so we simply go to the next */
    /* line.  Otherwise STATUS contains the number of fields read by      */
    /* sscanf.  Also if the first non-blank character of the first field  */
    /* is a ; then the line is a comment so go to the next line.          */

    /* Note that every variable that holds either the complete parm line  */
    /* read in, or a piece of it must be able to hold the maximum line    */
    /* size.  For example, if TYPE couldn't hold more that 20 characters  */
    /* and the parm line had more that 20 characters as its first field   */
    /* then we would have an overflow when sscanf read TYPE from TEMP     */

    status = sscanf(temp,"%s %s %s",&type,&eq,&val);
    if ((type[0] != ';') && (status > -1))
      {
	if ((status != 3) || (strcmp(eq,"=") !=0))
	  {
	    printf("+++ERROR -> Bad parameter format:\n     %s\n",temp);
	    fclose(parms);
	    exit(1);
	  }

	if (strcmpi(type,"num_features") == 0)
	  { parm_flag[0] = 1; num_features = atoi(val); }
	else if (strcmpi(type,"max_feature_value") == 0)
	  { parm_flag[1] = 1; max_feature_value = atoi(val); }
	else if (strcmpi(type,"num_peaks") == 0)
	  { parm_flag[2] = 1; num_peaks = atoi(val); }
	else if (strcmpi(type,"num_instances") == 0)
	  { parm_flag[3] = 1; num_instances = atoi(val); }
	else if (strcmpi(type,"proto_seed") == 0)
	  { parm_flag[4] = 1; proto_seed = atoi(val); }
	else if (strcmpi(type,"range") == 0)
	  { parm_flag[5] = 1; range = (double) atof(val); }
	else if (strcmpi(type,"percentage") == 0)
	  { parm_flag[6] = 1; percentage = (double) atof(val) / 100.0; }
	else if (strcmpi(type,"trunc_flag") == 0)
	  { parm_flag[7] = 1; trunc_flag = atoi(val); }
	else if (strcmpi(type,"out_file_name") == 0)
	  { parm_flag[8] = 1; strcpy(out_file_name,val); }
	else if (strcmpi(type,"stat_file_name") == 0)
	  { parm_flag[9] = 1; strcpy(stat_file_name,val); }
	else
	  {
	    printf("+++ERROR -> Unrecognized parameter:\n     %s\n",temp);
	    fclose(parms);
	    exit(1);
	  }
      }
    } while (!feof(parms));

  flag = 0;
  for (i = 0; i < 10; i++)
    {
      if (parm_flag[i] == 0)
	{
	  flag = 1;
	  switch (i) {
	    case 0 : { strcpy(mess,"NUM_FEATURES");
		       break;
		     }
	    case 1 : { strcpy(mess,"MAX_FEATURE_VALUE");
		       break;
		     }
	    case 2 : { strcpy(mess,"NUM_PEAKS");
		       break;
		     }
	    case 3 : { strcpy(mess,"NUM_INSTANCES");
		       break;
		     }
	    case 4 : { strcpy(mess,"PROTO_SEED");
		       break;
		     }
	    case 5 : { strcpy(mess,"RANGE");
		       break;
		     }
	    case 6 : { strcpy(mess,"PERCENTAGE");
		       break;
		     }
	    case 7 : { strcpy(mess,"TRUNC_FLAG");
		       break;
		     }
	    case 8 : { strcpy(mess,"OUT_FILE_NAME");
		       break;
		     }
	    case 9 : { strcpy(mess,"STAT_FILE_NAME");
		       break;
		     }
	  }
	  printf("\nERROR -> %s is absent from the parameter file\n",mess);
	}
    }

  fclose(parms);
  if (flag == 1) exit(1);
	  
  /* Get three seeds from the proto_seed. */
  srand(proto_seed);
  seed[0] = rand();
  seed[1] = rand();
  seed[2] = rand();

}

/*************************************************************************/
/* This function is primarily for information purposes and debugging.    */
/* It is not normally called but would print out the peak centroids.     */
/*************************************************************************/

void print_centroids()
{
  int i,j;
  for (i = 0; i < num_peaks; i++)
    {
      printf("\nPeak %i centroid is : ",i);
      for (j = 0; j < num_features; j++)
	printf("%f ",*(*(peak_centroids+i)+j));
      printf("\n");
    }
}

/*************************************************************************/
/* This function generates all of the peak centroids. The peaks are      */
/* confined to a region in the instance space, where the allowed values  */
/* are from:                                                             */
/*   (MAX_FEATURE_VALUE / 2.0) - (RANGE / 2.0) to                        */
/*   (MAX_FEATURE_VALUE / 2.0) + (RANGE / 2.0).                          */
/* This ensures that no peaks centroids are generated near enough to a   */
/* instance space boundary, which can cause either the loss of a number  */
/* of instances or cause many instances to be out of bounds (depending   */
/* on the setting of TRUNC_FLAG).                                        */
/*                                                                       */
/* A change is to generate peaks with a normal distribution around a     */
/* peak.  This might place the peaks more meaningfully near each other.  */
/* PEAK_CENTROIDS is an array, each row of which is a peak centroid      */
/* vector.  This array, as all major storage variables in this code, is  */
/* malloc'd for maximum space savings.                                   */
/*************************************************************************/

void generate_peak_centroids()

{
  int i,j;

  if (text_out_flag == 1)
    printf("\nGenerating peak centroids\n");

  peak_centroids = (double **) safe_malloc(sizeof(double *) * num_peaks);

  for (i = 0; i < num_peaks; i++)
    {
      *(peak_centroids + i) = (double *) safe_malloc(sizeof(double) * num_features);
      for (j = 0; j < num_features; j++)
	*(*(peak_centroids + i) + j) =
	  urand_range(seed,
	    ((double) max_feature_value / 2.0) - ((double) range / 2.0),
	    ((double) max_feature_value / 2.0) + ((double) range / 2.0));
    }
}

/*************************************************************************/
/* This function calculates the standard deviation required by           */
/* GEN_NORMAL to generate the instances so that approximately PERCENTAGE */
/* of the total number of instances fall within RADIUS of the peaks      */
/* making them positive instances (i.e. make PERCENTAGE of the instances */
/* generated positive).  This approximation is taken from page 933 of    */
/* The Handbook of Math Functions by Abramowitz & Stegun.                */
/*************************************************************************/

double calc_required_standard_deviation(double range, double percentage)

{
  double t,p,n,d,x,s;

  if (text_out_flag == 1)
   printf("\nCalculating required standard deviation\n");

  percentage = pow(percentage, (1.0 / (double) num_features));
  p = 0.5 - (percentage / 2.0);
  t = sqrt(log(1.0 / pow(p,2.0)));
  n = A0 + (A1 * t);
  d = 1.0 + (B0 * t) + (B1 * pow(t,2.0));
  x = t - (n / d);
  s = range / x;

  return(s);
}

/*************************************************************************/
/* This function generates a pair of numbers with a normal distribution  */
/* about MEAN as determined by SDEV.  First we generate two random       */
/* X1 and X2.  Then they are modified by the two sinusoidal functions    */
/* which are then modified into the normal pair.  The normal pair is     */
/* then bounds checked based on the value of TRUNC_FLAG.  Both trig      */
/* functions are required to provide a complete and proper normal        */
/* distribution.  This method is called the Box-Muller Transformation    */
/* and is taken from p. 261-262 of Basic Sampling Distribution Theory by */
/*                                                                       */
/*************************************************************************/

double *gen_normal_pair(double mean,
			double sdev)
{
  double x1,x2,z1,z2,normal_pair[2];

  do {
    x1 = urand(seed);
    x2 = urand(seed);
    z1 = sqrt(-2.0 * log(x1)) * cos(2.0 * PI * x2);
    z2 = sqrt(-2.0 * log(x1)) * sin(2.0 * PI * x2);
    normal_pair[0] = z1 * sdev + mean;
    normal_pair[1] = z2 * sdev + mean;

    if (trunc_flag != 2) /* Have not selected to re-gen feature values */
      return(normal_pair);
    /* Here we have selected to re-gen out of range feature values */
    /* so loop if they're out of range else return them            */
    if ((normal_pair[0] >= 0) &&
	(normal_pair[0] <= max_feature_value) &&
	(normal_pair[1] >= 0) &&
	(normal_pair[1] <= max_feature_value))
      return(normal_pair);

  } while (1 == 1);

}

/*************************************************************************/
/* This function generates all the instances.  First it determines the   */
/* number of instances per peak.  This may require a recalculation of    */
/* the total number of instances if NUM_PEAKS does not divide evenly     */
/* into NUM_INSTANCES.  If this happens, a notice is generated.  Next,   */
/* for each peak, NUM_INSTANCES_PER_PEAK instances are generated whose   */
/* frequency of occurance has a normal distibution in n-space around     */
/* each peak.  Finally, the peak number in the instance is set (i.e.     */
/* which peak the instance "belongs" to, and the                         */
/* class membership variable is initialized.  Note that peak and class   */
/* values start at 0 internally, and are translated to start at 1 only   */
/* on output.                                                            */
/*************************************************************************/

void generate_instances()
{
  int i, /* Instance index */
      j, /* Feature value index */
      k; /* peak index */
  double *normal_pair;

  if (text_out_flag == 1)
    printf("\nGenerating instances\n");

  /* Calc NUM_INSTANCES_PER_PEAK and re-calc NUM_INSTANCES if required */
  num_instances_per_peak = num_instances / num_peaks;
  if (num_instances_per_peak * num_peaks != num_instances)
    {
      num_instances = num_instances_per_peak * num_peaks;
      if (text_out_flag == 1)
	{
	  printf("\nNOTE: Because the number of instances per peak is constant\n");
	  printf("across peaks, the number of instances in the sample has been\n");
	  printf("recalculated to be : %i\n", num_instances);
	}
    }

  instances = (double **) safe_malloc(sizeof(double *) * num_instances);
  normal_pair = (double *) safe_malloc(sizeof(double) * 2);

  for (i = 0; i < num_instances; i++)
    {
      *(instances + i) = (double *) safe_malloc(sizeof(double) *
					    (num_features + 2));
      *(*(instances + i) + num_features) = 0.0;
      *(*(instances + i) + num_features + 1) = -1.0;
    }

  for (k = 0; k < num_peaks; k++)
    for (j = 0; j < num_features; j++)
      for (i = k * num_instances_per_peak;
	   i < k * num_instances_per_peak + num_instances_per_peak; i+=2)
	{
	  normal_pair = gen_normal_pair(*(*(peak_centroids + k) + j), stan_dev);
	  *(*(instances + i) + j) = *(normal_pair);
	  if ((i + 1) < (k * num_instances_per_peak + num_instances_per_peak))
	    *(*(instances + i + 1) + j) = *(normal_pair +1);
	}
}

/*************************************************************************/
/* This function takes an instance and computes the n-space Euclidean    */
/* distance to each of the peaks, keeping track of the closest peak.     */
/* That peak number is returned.  Consequently, the "shape" of positive  */
/* instances around a peak is hyper-spherical.                           */
/*************************************************************************/

int find_closest_peak(double *instance)
{

 int i,j,closest_peak;
 double sum,temp_sum;

 sum = 0.0;
 closest_peak = 0;
 for (i = 0; i < num_peaks; i++)  /* Try all peaks */
   {
     temp_sum = 0.0;
     /* Calc distance.  Note that we don't perform the SQRT here. */
     /* If the sums are smaller so will be the SQRTs.             */
     for (j = 0; j < num_features; j++)
       temp_sum += pow((*(instance + j) - *(*(peak_centroids + i) + j)), 2.0);

     if (i == 0)  /* Keep track of smallest SUM and its associated peak */
       {
	 sum = temp_sum;
	 closest_peak = i;
       }
     else
       if (temp_sum < sum)
	 {
	   sum = temp_sum;
	   closest_peak = i;
	 }
   }

   return(closest_peak); /* This value indicates the peak the instance */
			 /* is closest to                              */
}

/*************************************************************************/
/* This function determines if a given instance is within RANGE of the   */
/* closest peak.  If it is, this function returns the peak number, else  */
/* it returns -1 indicating no peak was within range.  This is done as   */
/* part of MAKE_CLASS_ASSIGNMENTS to insure that the peaks kept track of */
/* for each positive instance reflect the closest peak.                  */
/*************************************************************************/

int instance_is_within_range(double *instance)
{
  int j,peak;
  double jth_feature;

  peak = find_closest_peak(instance);
  for (j = 0; j < num_features; j++)
    {
      jth_feature = fabs(*(instance + j) - *(*(peak_centroids + peak) + j));
      if (jth_feature > range)
	return(-1);  /* Finished, the jth feature was out of RANGE */
    }
  return(peak);    /* Finished, the instance was within range */
}

/*************************************************************************/
/* This function assigns every instance to either the positive or        */
/* negative class depending on its Euclidean distance to the nearest     */
/* peak being within RANGE.  If the instacnce is further than RANGE from */
/* the nearest peak than assign it to the negative class, else to the    */
/* positive class.                                                       */
/*************************************************************************/

void make_class_assignments()
{
  int i,closest_peak;

  if (text_out_flag == 1)
    printf("\nMaking class assignments\n");

  neg = 0; pos = 0;
  for (i = 0; i < num_instances; i++)
    {
      /* If the instance is within RANGE of a peak, CLOSEST_PEAK is */
      /* returned the peak's number, else it gets -1.0 which        */
      /* indicates negative class membership                        */
      closest_peak = instance_is_within_range(*(instances + i));
      if (closest_peak > -1)
	{
	  pos += 1;
	  *(*(instances + i) + num_features) = 0.0; /* Positive class */
	  *(*(instances + i) + num_features + 1) = (double) closest_peak;
	}
	else
	  {
	    neg += 1;
	    *(*(instances + i) + num_features) = 1.0; /* Negative class */
	  }
    }
}

/************************************************************************/
/* This function is associated with the function print_instances.  It   */
/* used when TRUNC_FLAG = 1 to check if the instances is within the     */
/* confines of the instance space (remember when TRUNC_FLAG = 1 out of  */
/* bounds instances are not printed).                                   */
/************************************************************************/

int in_bounds(double *instance)
{
  int i;
  for (i = 0; i < num_features; i++)
    {
      if ((*(instance + i) < 0) ||
	  (*(instance + i) > max_feature_value))
	{
	  if (*(instance + num_features) == 0) /* Positive class */
	    num_pos_truncated += 1;
	  else
	    num_neg_truncated += 1;
	  return(0);
	}
    }
  return(1);    /* Finished, the instance was in bounds */
}

	
/*************************************************************************/
/* This function prints the instances along with their class membership  */
/* value, 0 or 1, and their peak membership value, 1 - p.  This function */
/* makes 2 passes over the instance list, printing them out in class     */
/* order.                                                                */
/*                                                                       */
/* This function is the one that may have to customized for each         */
/* application of this program to different learning algorithms.  By the */
/* time we get to this point the instances are stored in the following   */
/* fashion in the psuedo-array INSTANCES, where:                         */
/*                                                                       */
/*    INSTANCES is accessed as *(*(instances + n) + m), where:           */
/*        n is the instance number, 0 through NUM_INSTANCES - 1, and      */
/*        m is the feature value, 0 through NUM_FEATURES - 1.            */
/*                                                                       */
/* In addition, the class membership value for instance n is in:         */
/*        *(*(instances + n) + num_features                              */
/* where positive instances are assigned a membership value of 0 and     */
/* and negative instances a value of 1.                                  */
/*                                                                       */
/* and the peak to which positive instance n belongs is:                 */
/*        *(*(instances + n) + num_features + 1)                         */
/* where peak numbers run from 0 to NUM_PEAKS - 1 for positive instances,*/
/* negative instances have a peak number of -1.                          */
/*                                                                       */
/* Also, up to this point the feature values are double numbers.  Since  */
/* in this example we want them to be integers, we must round them in a  */
/* special way:                                                          */
/*                                                                       */
/* if the instance is positive                                           */
/*   {                                                                   */
/*     if the feature value is < the corresonding peak centroid value    */
/*       then ceil(feautre value) [move it up to within RANGE]           */
/*     else                                                              */
/*       floor(feature value) [move it down to within RANGE]             */
/*   } else                                                              */
/*   {                                                                   */
/*     if the feature value is < the corresponding peak centroid value   */
/*       then floor(feautre value) [move it down out of RANGE]           */
/*     else                                                              */
/*       ceil(feature value) [move it up out of RANGE]                   */
/*   }                                                                   */
/*                                                                       */
/* Also in this particular print function we want the negative instances */
/* to be printed first with a class membership value of 0, the positive  */
/* instance next with a class value of 1.  In adition, feature values    */
/* separated with commas.  This is simply the way I needed it for my     */
/* experiments.                                                          */
/*************************************************************************/

void print_instances()
{
  int i,j,k,f_value,class,peak;
  double c_value;

  if (text_out_flag == 1)
    printf("\nPrinting instances\n");
  out_file = fopen(out_file_name, "wt");

  for (k = 0; k < 2; k++)   /* Print both classes, Positive first */
    for (i = 0; i < num_instances; i++)
      {
	/* Simplify the indexing */
	class = (int) *(*(instances + i) + num_features);
	/* If the class membership value equals K: 0 or positive for */
	/* first pass, 1 or negative for second pass */
	if (class == k)
	  {
	    if ((trunc_flag == 0) || /* Let through all instances */
		((trunc_flag == 1) &&   /* Let through if in bounds */
		 (in_bounds(*(instances + i)) == 1)))
	      {
		/* Simplify the indexing */
		peak = (int) *(*(instances + i) + num_features + 1);

		/* Here we switch boolen class values, making 1 stand for */
		/* positive and 0 for negative.  This is the way my       */
		/* current experiments require it.  Note this is only for */
		/* printing, the actual value is not changed.             */

		if (class == 0)  /* Positive class, print 1 */
		  fprintf(out_file,"   1,");
		else
		  fprintf(out_file,"   0,");  /* else print 0 */

		for (j = 0; j < num_features; j++)
		  {
		    /* This is the peak centroid value corresponding to */
		    /* the current feautre value                        */
		    c_value = *(*(peak_centroids + peak) + j);
		    if (class == 1)  /* negative class */
		      {
			if (*(*(instances + i) + j) < c_value)
			  f_value = floor(*(*(instances + i) + j));
			else
			  f_value = ceil(*(*(instances + i) + j));
		      }
		    else  /* positive class */
		      {
			if (*(*(instances + i) + j) < c_value)
			  f_value = ceil(*(*(instances + i) + j));
			else
			  f_value = floor(*(*(instances + i) + j));
		      }
		    /* f_value contains the rounded feature value. */
		    /* if it is not the last feature value in the  */
		    /* instance print a comma after it.            */
		    if (j < num_features - 1)
		      fprintf(out_file, "%4i,", f_value);
		    else
		      fprintf(out_file, "%4i\n", f_value);
		  }
	      }
	  }
      }
  fclose(out_file);
}

/************************************************************************/
/* This function simply prints statistics associated with this a given  */
/* run of DGP/2.  If no filename is specified in the parm file then it  */
/* prints the stats to the null device (defined in NUL_DEV).  Otherwise */
/* the stats are appended to the stats file so that they can be kept    */
/* track of over many runs.                                             */
/************************************************************************/

void print_statistics()

{
  int new_pos, new_total;

  /* We append to the file so that over many runs, all the stats can  */
  /* be collected.  If the stat file doesn't exist append will create */
  /* it.                                                              */
  stat_file = fopen(stat_file_name,"at");

  fprintf(stat_file,"\nNumber of features           : %i\n", num_features);
  fprintf(stat_file,"Maximum feature value        : %i\n", max_feature_value);
  fprintf(stat_file,"Number of peaks              : %i\n", num_peaks);
  fprintf(stat_file,"Number of instances          : %i\n", num_instances);
  fprintf(stat_file,"Range around peak(s)         : %g\n", range);
  fprintf(stat_file,"Random number generator seed : %i\n", proto_seed);
  fprintf(stat_file,"Percent positive instances   : %g\n", percentage);
  fprintf(stat_file,"Actual positive instances    : %i\n", pos);
  fprintf(stat_file,"Actual / Total               : %g\n",
	 ((float) pos / (float) num_instances) * 100.0);
  if (trunc_flag == 1)
    {
      fprintf(stat_file,"Number of positive instances truncated : %i\n" , num_pos_truncated);
      fprintf(stat_file,"Number of negative instances truncated : %i\n" , num_neg_truncated);
      new_pos = pos - num_pos_truncated;
      new_total = num_instances - (num_pos_truncated + num_neg_truncated);
      fprintf(stat_file,"New Actual / Total                     : %g\n",
	 ((float) new_pos / (float) new_total) * 100.0);
    }
  fprintf(stat_file,"\n--------------------------------------\n");
}

/************************************************************************/
/* This is the main function which does as it reads.  I believe in      */
/* straight forward main functions (self documenting code ?).           */
/************************************************************************/

void main(int argc, char *argv[])

{
  printf("\nDGP/2 Version 1.0\n");

  text_out_flag = 1; /* Print run progress information.  Set to 0 to */
		     /* disable.                                     */

  read_input_parameters(argc,argv);

  stan_dev = calc_required_standard_deviation(range, percentage);

  generate_peak_centroids();

  generate_instances();

  make_class_assignments();

  print_instances();

  print_statistics();

}
