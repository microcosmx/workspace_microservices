/*------------------------------------------------------------------11/Nov/87-*
/*  MAKEANIMAL
/*      Creates an entire trial size of tables. This is a meta program
/*  on top of the old make*.c files. Works by choosing a random # from 
/*  one to four to choose which template to use.
/* 
/*      This set of animals is carefully designed so that there are some 
/*  "good" attributes to leave out. Note that it's also for Classit-3 and
/*   up.
/*---------------------------------------------------------------------JHG---*/

#include	<sys/file.h>
#include	<math.h>

int	seed;

main(argc, argv)
int argc;
char *argv[];
{
    int         Nobj, i;
    char	name[20];

    if (argc!=3) {
        printf("usage: '%s <seed> <# of objects>'\n", argv[0]);
        return(0);
    }
    seed= atoi(argv[1]);
    Nobj= atoi(argv[2]);
    printf("%% make animal run for %d objects, starting with seed %d\n%%\n",
        Nobj, seed);


    for (i=0; i< Nobj; i++)
        switch (rand4()) {
          case 1: makedog("D"); break;
          case 2: makecat("C"); break;
          case 3: makehorse("H"); break;
          case 4: makegee("G"); break;
          default: printf("what the hell???\n"); exit(0);
        }
}

/*-----------------------------------------------------------------12/Aug/86-*/
/*  
/*	MAKEDOGGIE - a routine to generate a dog, as described by a set
/*		of cylinder and WMS-like attributes. This description is 
/*		printed onto standard out.
/*	Currently, all dogs created herein have the same orientation.
/* 
/*	Object numbers are not assigned by this program, nor is the object
/*	tree created, nor is the object normalized by overall size and mass.
/*	However, the location and axis of sub parts ARE object-centered.
/* 
/*--------------------------------------------------------------------JHG----*/

makedog(name)
char *name;
{
    float	bellrand();

    float	height, radius, axis[3], location[3];
    int		texture, fd;
    float	leglength, torsoH, torsoR;

    printf("%%\t\t\t------------ A Dog ------------\n");
    printf("%% created with %d\n%%\n", seed);
    printf("%s\tname\n", name);
    printf("8\tNcomponents\n");     /* For Now.... */

    printf("%%    TORSO\n");
    printf("9		Natts\n");
    location[0]= location[1]= location[2]= 0;
    axis[0]= 1; axis[1]= 0; axis[2]= 0;
    torsoH= height= bellrand(22.0, 28.0);
    torsoR= radius= bellrand(5.5, 7.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG1\n");
    printf("9		Natts\n");
    leglength= bellrand(10.0, 15.0);
    location[0]= torsoH/2; 
    location[1]= torsoR; 
    location[2]= -torsoR - leglength/2;
       /* this leg might be canted forward up to 45 degrees: */
    axis[0]= bellrand(0.0,1.0); 
    axis[1]= 0; 
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.8, 1.2);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG2\n");
    printf("9		Natts\n");
    location[0]= torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.8, 1.2);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG3\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.8, 1.2);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG4\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= bellrand(0.0,1.0);
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.8, 1.2);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    NECK\n");
    printf("9		Natts\n");
    height= bellrand(5.0, 6.0);
    location[0]= torsoH/2 + height/2;
    location[1]= 0;
    location[2]= torsoR;
    axis[0]= 1;
    axis[1]= 0;
    axis[2]= 1;
    radius= bellrand(2.0, 3.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    HEAD\n");
    printf("9		Natts\n");
    location[0]= torsoH/2 + height; /* height is neck length */
    location[1]= 0;
    location[2]= torsoR;
    axis[0]= 1;
    axis[1]= bellrand(-1.0, 1.0);	/* he's looking to the right or left */
    axis[2]= 0;
    height= bellrand(10.0, 13.0);
    radius= bellrand(2.5, 3.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    TAIL\n");
    printf("9		Natts\n");
    height= bellrand(6.0, 8.0);    /* a short tailed dog */
    location[0]= -torsoH + height;
    location[1]= 0;
    location[2]= 0;
    axis[0]= -1.0;
    axis[1]= bellrand(-1.0,1.0);   /* pointing any which way */
    axis[2]= bellrand(-1.0,1.0);
    radius= bellrand(0.2, 0.4);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

} /* end of makedog */

/*----------------------------------------------------------------12/Aug/86-*/
/*	MAKEKITTY  - a routine to generate a cat, as described by a set
/*		of cylinder and WMS-like attributes. This description is 
/*		printed onto standard out.
/* 
/*-------------------------------------------------------------------JHG----*/

makecat(name)
char *name;
{
    float	bellrand();

    float	height, radius, axis[3], location[3];
    int		texture;
    float	leglength, torsoH, torsoR;

    printf("%%\t\t\t------------ A Cat ------------\n");
    printf("%% created with %d\n%%\n", seed);
    printf("%s\tname\n", name);
    printf("8\tNcomponents\n");     /* For Now.... */

    printf("%%    TORSO\n");
    printf("9		Natts\n");
    location[0]= location[1]= location[2]= 0;
    axis[0]= 1; axis[1]= 0; axis[2]= 0;
    torsoH= height= bellrand(15.0, 22.0);
    torsoR= radius= bellrand(2.5, 4.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG1\n");
    printf("9		Natts\n");
    leglength= bellrand(4.0, 9.0);
    location[0]= torsoH/2; 
    location[1]= torsoR; 
    location[2]= -torsoR - leglength/2;
       /* this leg might be canted forward up to 45 degrees: */
    axis[0]= bellrand(0.0,1.0); 
    axis[1]= 0; 
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.4, 0.8);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG2\n");
    printf("9		Natts\n");
    location[0]= torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.4, 0.8);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG3\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.4, 0.8);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG4\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= bellrand(0.0,1.0);
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(0.4, 0.8);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    NECK\n");
    printf("9		Natts\n");
    height= bellrand(2.0, 4.0);
    location[0]= torsoH/2 + height/2;
    location[1]= 0;
    location[2]= torsoR;
    axis[0]= 1;
    axis[1]= 0;
    axis[2]= 1;
    radius= bellrand(1.5, 2.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    HEAD\n");
    printf("9		Natts\n");
    location[0]= torsoH/2 + height; /* height is neck length */
    location[1]= 0;
    location[2]= torsoR;
    axis[0]= 1;
    axis[1]= bellrand(-1.0, 1.0);	/* he's looking to the right or left */
    axis[2]= 0;
    height= bellrand(3.0, 5.0);
    radius= bellrand(1.5, 2.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    TAIL\n");
    printf("9		Natts\n");
    height= bellrand(10.0, 18.0);    /* a long tailed cat */
    location[0]= -torsoH + height;
    location[1]= 0;
    location[2]= 0;
    axis[0]= -1.0;
    axis[1]= bellrand(-1.0,1.0);   /* pointing any which way */
    axis[2]= bellrand(-1.0,1.0);
    radius= bellrand(0.3, 0.7);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

} /* end of main */


/*------------------------------------------------------------------04/Nov/86-*
/*  
/*	MAKEHORSEY - a routine to generate a horse, as described by a set
/*		of cylinder and WMS-like attributes. This description is 
/*		printed onto standard out.
/*	Currently, all horses created herein have the same orientation.
/* 
/*	Object numbers are not assigned by this program, nor is the object
/*	tree created, nor is the object normalized by overall size and mass.
/*	However, the location and axis of sub parts ARE object-centered.
/* 
/*-------------------------------------------------------------------JHG----*/

makehorse(name)
char *name;
{
    float	bellrand();

    float	height, radius, axis[3], location[3];
    int		texture;
    float	leglength, torsoH, torsoR;

    printf("%%\t\t\t------------ A Horse ------------\n");
    printf("%% created with %d\n%%\n", seed);
    printf("%s\tname\n", name);
    printf("8\tNcomponents\n");     /* For Now.... */

    printf("%%    TORSO\n");
    printf("9		Natts\n");
    location[0]= location[1]= location[2]= 0;
    axis[0]= 1; axis[1]= 0; axis[2]= 0;
    torsoH= height= bellrand(50.0, 60.0);
    torsoR= radius= bellrand(10.0, 14.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG1\n");
    printf("9		Natts\n");
    leglength= bellrand(36.0, 44.0);
    location[0]= torsoH/2; 
    location[1]= torsoR; 
    location[2]= -torsoR - leglength/2;
       /* this leg might be canted forward up to 30 degrees: */
    axis[0]= bellrand(0.0,0.5); 
    axis[1]= 0; 
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 3.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG2\n");
    printf("9		Natts\n");
    location[0]= torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 3.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG3\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 3.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG4\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= bellrand(0.0,0.5);
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 3.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    NECK\n");
    printf("9		Natts\n");
    height= bellrand(12.0, 16.0);
    location[0]= torsoH/2 + height/2.828;
    location[1]= 0;
    location[2]= torsoR + height/2.828;
    axis[0]= 1;
    axis[1]= 0;
    axis[2]= 1;
    radius= bellrand(5.0, 7.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    HEAD\n");
    printf("9		Natts\n");
    location[0]= torsoH/2 + height/1.414; /* height is neck length */
    location[1]= 0;
    location[2]= torsoR + height/1.414;
    axis[0]= 1;
    axis[1]= bellrand(-1.0, 1.0);	/* he's looking to the right or left */
    axis[2]= 0;
    height= bellrand(18.0, 22.0);
    radius= bellrand(4.0, 6.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    TAIL\n");
    printf("9		Natts\n");
    height= bellrand(26.0, 33.0);    /* a long tailed horse */
    location[0]= -torsoH + height;
    location[1]= 0;
    location[2]= 0;
    axis[0]= -1.0;
    axis[1]= bellrand(-1.0,1.0);   /* pointing any which way */
    axis[2]= bellrand(-1.0,0.0);
    radius= bellrand(1.0, 2.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

} /* end of makehorse */


/*----------------------------------------------------------------12/Aug/86-*/
/*  
/*	MAKEGIRAFFE - a routine to generate a giraffe, as described by a set
/*		of cylinder and WMS-like attributes. This description is 
/*		printed onto standard out.
/*	Currently, all giraffes created herein have the same orientation.
/* 
/*	Object numbers are not assigned by this program, nor is the object
/*	tree created, nor is the object normalized by overall size and mass.
/*	However, the location and axis of sub parts ARE object-centered.
/* 
/*-------------------------------------------------------------------JHG----*/

makegee(name)
char *name;
{
    float	bellrand();

    float	height, radius, axis[3], location[3];
    int		texture;
    float	leglength, torsoH, torsoR;


    printf("%%\t\t\t------------ A Giraffe ------------\n");
    printf("%% created with %d\n%%\n", seed);
    printf("%s\tname\n", name);
    printf("8\tNcomponents\n");     /* For Now.... */

    printf("%%    TORSO\n");
    printf("9		Natts\n");
    location[0]= location[1]= location[2]= 0;
    axis[0]= 1; axis[1]= 0; axis[2]= 0;
    torsoH= height= bellrand(60.0, 72.0);
    torsoR= radius= bellrand(12.5, 17.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG1\n");
    printf("9		Natts\n");
    leglength= bellrand(58.0, 70.0);
    location[0]= torsoH/2; 
    location[1]= torsoR; 
    location[2]= -torsoR - leglength/2;
       /* this leg might be canted forward up to 30 degrees: */
    axis[0]= bellrand(0.0,0.5); 
    axis[1]= 0; 
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 4.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG2\n");
    printf("9		Natts\n");
    location[0]= torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 4.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG3\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= 0;
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 4.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    LEG4\n");
    printf("9		Natts\n");
    location[0]= -torsoH/2;
    location[1]= -torsoR;
    location[2]= -torsoR - leglength/2;
    axis[0]= bellrand(0.0,0.5);
    axis[1]= 0;
    axis[2]= -1;
    height= leglength;
    radius= bellrand(2.0, 4.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    NECK\n");
    printf("9		Natts\n");
    height= bellrand(45.0, 55.0);
    location[0]= torsoH/2 + height/2.828;
    location[1]= 0;
    location[2]= torsoR + height/2.828;
    axis[0]= 1;
    axis[1]= 0;
    axis[2]= 1;
    radius= bellrand(5.0, 9.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    HEAD\n");
    printf("9		Natts\n");
    location[0]= torsoH/2 + height/1.414; /* height is neck length */
    location[1]= 0;
    location[2]= torsoR + height/1.414;
    axis[0]= 1;
    axis[1]= bellrand(-1.0, 1.0);	/* he's looking to the right or left */
    axis[2]= 0;
    height= bellrand(18.0, 22.0);
    radius= bellrand(3.5, 5.5);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

    printf("%%    TAIL\n");
    printf("9		Natts\n");
    height= bellrand(20.0, 25.0);    /* a short tailed giraffe */
    location[0]= -torsoH + height;
    location[1]= 0;
    location[2]= 0;
    axis[0]= -1.0;
    axis[1]= bellrand(-1.0,1.0);   /* pointing any which way */
    axis[2]= bellrand(-1.0,0.0);
    radius= bellrand(0.5, 1.0);
    texture= bellrand(170.0, 180.0);
    printpart(height, radius, texture, axis, location);

} /* end of makegee */


/*-----------------------------------------------------------------12/Aug/86-*/
/*  Procedure  - PRINTPART
/* 
/*  Inputs    -> height, radius, texture, axis, location.
/* 
/*  Actions   -> prints these values to stnd out. 
/*--------------------------------------------------------------------JHG----*/

printpart(h,r,t, axis, loc)
float h,r, axis[], loc[];
int t;
{
    double sqrt();

    printf("%5.2f\tlocX\n %5.2f\tlocY\n %5.2f\tlocZ\n", loc[0],loc[1],loc[2]);
    printf("%5.2f\taxisX\n %5.2f\taxisY\n %5.2f\taxisZ\n", 
	   axis[0], axis[1], axis[2]);

    printf("%5.2f\t height\n", h);
    printf("%5.2f\t radius\n", r);
    printf("%d\t texture\n%%\n", t);

} /* end of printpart */


/*-----------------------------------------------------------------10/Jan/87-*/
/*  Function  - BELLRAND
/* 
/*  Inputs    -> st, fin: the range of the random number. Also uses the 
/*	global "seed".
/* 
/*  Returns   ->  A crude approximation of a bell shaped distribution over
/*	the specified range. Values at the center of the range are 3 times
/*	more likely to occur than those at the edges.
/*-------------------------------------------------------------------JHG----*/

float bellrand(st, fin)
float st, fin;
{
	float interval;
	float random();

	interval= (fin-st)/5;
	switch (rand3()) {
	    case 1: return(random(st, fin));
	    case 2: return(random(st+interval, fin-interval));
	    case 3: return(random(st+2*interval, fin-2*interval));
	    default: printf("what the hell???\n"); exit(0);
	}
}

/*------------------------------------------------------------------13/Feb/86-*/
/*  Function  - RANDOM
/* 
/*  Inputs    -> st, fin: these define the range of the random number.
/*		NOTE: this also needs a global called "seed".
/* 
/*  Returns   -> a (pseudo) random number. (of type float)
/*---------------------------------------------------------------------JHG----*/

#define	MOD 65537
#define	MULT 25173
#define	INC 13849

#define	ABS(x) ((x>0) ? x : -x)

float random(st,fin)
float st, fin;
{
	float  range;
	float  tmp;
	range = fin - st;

        /*  "%" is the modulus operator */

	seed = (MULT * seed + INC) % MOD;  
	tmp = ABS( seed / (float)MOD ) ;
	return(tmp * range + st);

} /* end of random */

/***************************************************************************/
/*  A local function which returns a random integer between 1 and 4.  
/***************************************************************************/

int rand3()
{
	seed= (MULT *seed + INC) %MOD;
	return( ABS(seed/ (float)MOD ) * 3.0 + 1.0);
}



int rand4()
{
	seed= (MULT *seed + INC) %MOD;
	return( ABS(seed/ (float)MOD ) * 4.0 + 1.0);
}


