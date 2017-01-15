/*------------------------------------------------------------------------*/
/*                                                                        */
/*  CONVERT.CPP                                                           */
/*                                                                        */
/*  Scott Derrick                                                         */
/*  cmp140  spring quarter ucsc						  */
/*									  */
/*  convert character representation                                      */
/*                                                                        */
/*------------------------------------------------------------------------*/
/*

I wrote this utilty to convert the artificial character dataset's 
representation from a list of line segments to a 12x8 array of 1's and 0's. 
You may copy, use ,distribute or modify this code. 

I compiled this with Borland's NT 4.0 & OS/2 1.5 compilers
       use > bcc convert.cpp

I compiled this with gcc
       use % g++ convert.cc -o convert -ml

 outputs two files: pattern,  this is the input test patterns
                  : target,   this is the target patterns

 outputs one file: pattern, input pattern followed by target pattern

 outputs the pattern file in two formats,
 both formats represent the character as a 12x8 grid of pixels, a 0 is
 off pixel, a 1 is a on pixel.
 the input file represents the character as a collection of line segments
 specified as two pairs of x,y coords and a diagonal ratio indicating the
 minimal rectagle that would contain the character. The output character
 is sized to fit the grid if needed.



 This is the input file, the first number represents the character, 1 == a.
 The second number is the line segment number. The string 'line' is
 extraneous. The next four digits represent the two xy pairs, x1 y1 x2 y2.
 The next float is the line segment length, The next float is the diagonal
 size of the rectangle that would contain the character.

1  0 line  0  0  0 13  13.00  45.28
1  1 line 20  0 22 15  15.13  45.28
1  2 line  0 13 22 15  22.09  45.28
1  3 line  0 13  0 27  14.00  45.28
1  4 line 22 15 23 39  24.02  45.28
1  5 line  0 27 23 39  25.94  45.28

 This is the array representation of the output pattern file.

   1 2 3 4 5 6 7 8  columns

1  0 0 0 0 0 0 1 1
2  0 0 0 0 1 1 0 1
3  0 0 1 1 0 0 0 1
4  1 1 0 0 0 0 0 1
5  1 0 0 0 0 0 1 0
6  1 0 0 0 0 0 1 0
7  1 0 0 0 0 0 1 0
8  1 1 1 1 1 1 1 0
9  1 0 0 0 0 0 1 0
10 1 0 0 0 0 0 1 0
11 1 0 0 0 0 0 1 0
12 1 0 0 0 0 0 1 0

r
o
w
s

 Two different pattern files may be generated.

 The first presents the pattern to the network as a one large pattern. The
 rows are concated together in one string -> 123456789101112

 The second presents the pattern to the network as 6 grids. The upper left
 quadrant, followed by the upper right, followed by the middle left......
 The pixels would be in row column representation
          -> 11 12 13 14 21 22 23 24 31 32 33 34 41 42 43 44
 for the first quadrant, then followed by the pixels for the next quadrant.

 The target file is a 1x10 array, each slot represents a character. All slots
 are zero except for the correct character which is set to 1.

 1 0 0 0 0 0 0 0 0 0

 This would be the target for the character 'A'.

*/

#define NODEBUG  1  // comment out for debug output
#define MAXPATH 167 // added for bug in dir.h gcc version

#ifndef __DIR_H
// #include <dir.h>
#endif

#ifndef __STRING_H
#include <string.h>
#endif

#ifndef __STDLIB_H
#include <stdlib.h>
#endif

#ifndef __STDIO_H
#include <stdio.h>
#endif

#ifndef __MATH_H
#include <math.h>
#endif

#ifndef __FSTREAM_H
#include <fstream.h>
#endif

#ifndef __IOSTREAM_H
#include <iostream.h>
#endif

// missing prototype from gcc headers
char *strlwr(char *);

enum { ONEARRAY, SIXARRAY, ONEFILE, TWOFILE };

#define LINELENGTH 50

// input a float, return an int rounded up if > xx.5 , rounded
// down if <= xx.5
int round(float num)
{
   int stub = (int)floor(num);
   float rem = num - stub;
   if(rem > .5)
      stub++;
   return(stub);
}

// convert class, to convert a character reprensented as line segments
// to a 12x8 pixel array, the character is sized to fill the grid if needed
class Convert
{

public:

    Convert( char *, int, int, int, int, const char *);
    ~Convert(void);
    void Show(void);
    void scanSource(void);
    void fillGrid(const char *line);
    void output(void);
    void targetOutput(void);

private:

   fstream *target, *pattern, source;	// input & output streams
   int low, high;                       // range of characters
   int otype;				// output type
   int ftype;				// one or two output files
   short grid[12][8];			// the pattern
   char *inputFile;			// input file
   char currentChar;

};

// destructor
Convert::~Convert(void)
{
   if(inputFile)
      delete(inputFile);
}


// converts constructor, pass in the input file specs and the output
// representation type

Convert::Convert( char *characters, int rlow, int rhigh, int arrayType,
		  int fileType, const char *path)
{
   fstream pat, tar;
   low = rlow;
   high = rhigh;
   otype = arrayType;
   ftype = fileType;
   inputFile = new char[MAXPATH];
   char *inputPtr;
   char currNum[4];
   int charCount = 0;
   int numCount;

    // figure out the path if the source files are in a different dir
    if(path)
    {
       strcpy(inputFile,path);
       int len = strlen(inputFile);
       inputPtr = inputFile + len;
    }
    else
       inputPtr = inputFile;

    // open the output streams
    pat.open("pattern", ios::out);
    pattern = &pat;
    if(ftype == TWOFILE)
    {
       tar.open("target", ios::out);
       target = &tar;
    }
    else
       target = &pat;


    for(numCount = low; numCount <= high; numCount++)
    {

       charCount = 0;
       while(characters[charCount])
       {
	  // build input file name
	  inputPtr[0] = currentChar = characters[charCount++];
	  inputPtr[1] = 0;
	  sprintf(currNum, "%d", numCount);
	  strcat(inputFile, currNum);

	  source.open(inputFile, ios::in);

	  if(source.fail())
	  {
	     cerr << "Error opening " << inputFile << '\n';
	     exit(1);
	  }

	  // scan & generate the output
	  scanSource();
	  source.close();
	  output();
       }
    }
    tar.close();
    pat.close();
}

// output target file
void Convert::targetOutput(void)
{
   if(target->good())
   {
      switch(currentChar)
      {
      case 'a':
	 *target << "1 0 0 0 0 0 0 0 0 0" << endl;
	 break;
      case 'c':
	 *target << "0 1 0 0 0 0 0 0 0 0" << endl;
	 break;
      case 'd':
	 *target << "0 0 1 0 0 0 0 0 0 0" << endl;
	 break;
      case 'e':
	 *target << "0 0 0 1 0 0 0 0 0 0" << endl;
	 break;
      case 'f':
	 *target << "0 0 0 0 1 0 0 0 0 0" << endl;
	 break;
      case 'g':
	 *target << "0 0 0 0 0 1 0 0 0 0" << endl;
	 break;
      case 'h':
	 *target << "0 0 0 0 0 0 1 0 0 0" << endl;
	 break;
      case 'l':
	 *target << "0 0 0 0 0 0 0 1 0 0" << endl;
	 break;
      case 'p':
	 *target << "0 0 0 0 0 0 0 0 1 0" << endl;
	 break;
      case 'r':
	 *target << "0 0 0 0 0 0 0 0 0 1" << endl;
	 break;
      }
   }
}

// output grid to pattern & target files
void Convert::output(void)
{
   if(pattern->good())
   {
      int k, l;

      // output as one big picture line by line
      if(otype == ONEARRAY)
      {
	 for(k = 11; k >= 0; k--)
	 {
	    for(l = 0; l < 8; l++)
	       *pattern << grid[k][l] << ' ';
	 }
      }
      // output as 6 quadrants each quadrant line by line
      else
      {
	 for(k = 11; k >= 8; k--)
	 {
	    for(l = 0; l < 4; l++)
	       *pattern << grid[k][l] << ' ';
	 }
 	 for(k = 11; k >= 8; k--)
	 {
	    for(l = 4; l < 8; l++)
	       *pattern << grid[k][l] << ' ';
	 }
	 for(k = 7; k >= 4; k--)
	 {
	    for(l = 0; l < 4; l++)
	       *pattern << grid[k][l] << ' ';
	 }
	 for(k = 7; k >= 4; k--)
	 {
	    for(l = 4; l < 8; l++)
	       *pattern << grid[k][l] << ' ';
	 }
	 for(k = 3; k >= 0; k--)
	 {
	    for(l = 0; l < 4; l++)
	       *pattern << grid[k][l] << ' ';
	 }
	 for(k = 3; k >= 0; k--)
	 {
	    for(l = 4; l < 8; l++)
	       *pattern << grid[k][l] << ' ';
	 }

      }
      if(ftype == TWOFILE)
	 *pattern << endl;
      else
	 *pattern << "   ";

      // output target vector
      targetOutput();

   }
}

// reads one line at a time and calls fillgrid to store *pattern
void Convert::scanSource(void)
{
#ifndef NODEBUG
   cout << inputFile << endl;
#endif

   char line[LINELENGTH];
   for(int i = 0;i < 12; i++)
      for(int j = 0; j < 8;j++)
	 grid[i][j] = 0;

   while(!source.eof())
   {
      source.getline(line, LINELENGTH);

#ifndef NODEBUG
      cout << line << endl;
#endif

      if(line[0])
	 fillGrid(line);
   }
   if(source.fail())
      source.clear();

// for debug output
#ifndef NODEBUG

   for(int k = 11; k >= 0; k--)
   {
      for(int l = 0; l < 8; l++)
	 cout << grid[k][l] << ' ';
      cout << endl;
   }
#endif

}

void Convert::fillGrid(const char *line)
{
   int xy[4];
   int count;
   float size, val, slope, xflt, yflt;
   char tline[LINELENGTH];
   strcpy(tline, line);
   char *token = strstr(tline, "line");
   token = strtok(token," ");

   // retrieve the x & y values & the size value
   for(int i = 0; i < 4; i++)
   {
      token = strtok(NULL," ");
      xy[i] = atoi(token);
   }
   strtok(NULL," ");
   token = strtok(NULL," ");
   size = atof(token);
   val = size/14;

   // adjust the x & y coords to fit into a 12x8 grid

   for(int j = 0; j < 4; j++)
   {
      xy[j] = (int)(xy[j]  / val);
      if((xy[j] > 11) && ((j==1)||(j==3)))
	 xy[j] = 11;
      if((xy[j] > 7) && ((j==0)||(j==2)))
	 xy[j] = 7;
   }
   float den = xy[2] - xy[0];

   if(den < 0) // the line is drawing from right to left, switch it back
   {
      int tempval = xy[0];
      xy[0] = xy[2];
      xy[2] = tempval;
      tempval = xy[1];
      xy[1] = xy[3];
      xy[3] = tempval;
      den = xy[2] - xy[0];
   }

      // don't devide by 0!!
   if(den)
      slope = ((float)(xy[3] - xy[1]))/den;  // this is x/y
   else
      slope = 999999999999.0;

   if((slope > 1) || (slope < -1)) // index off of y axis
   {
      xflt = xy[0];
      if(slope > 1)
      {
	 for(count = xy[1]; count <= xy[3]; count++)
	 {
	    grid[count][round(xflt)] = 1;
	    xflt += 1/slope;
	 }
      }
      else  // negative slope
      {
	 for(count = xy[1]; count >= xy[3]; count--)
	 {
	    grid[count][round(xflt)] = 1;
	    xflt -= 1/slope;
	 }
      }
   }
   else  // index off of x axis
   {
      yflt = xy[1];

      for(count = xy[0]; count <= xy[2]; count++)
      {
	 grid[round(yflt)][count] = 1;
	 yflt += slope;
      }

   }

}


int main( int argc, char *argv[] )
{
   int atype = ONEARRAY;
   int ftype = TWOFILE;
   int low = 1;
   int high = 10;
   char chars[] = "acdefghlpr";
   char *thepath = 0;

    if( argc < 5 )
        {
        cerr << "Usage:  convert [-output] [acdefghlpr] x1 x2 [path]" << endl << endl;
        cerr << "output:" << endl;
        cerr << "\t-1\toutput *pattern & *target in one file" << endl;
        cerr << "\t-2\toutput *pattern & *target in two files" << endl;
        cerr << "\t-c\trepresent as 12x8 array" << endl;
        cerr << "\t-d\trepresent as 6 4x4 arrays" << endl;
	cerr << "\nacdefghlpr:  specify characters to convert" << endl;
	cerr << "\nx1 x2:       specify range to convert, x1 < x2, and 1 to 500" << endl;
	cerr << "\npath:        specify optional path to source files" << endl;
	cerr << "\n\nexample: >convert -2c acdefgh 1 25 source" << endl;

        exit(1);
        }

    else
    {

	if( strchr( argv[1], 'd' ) )
	   atype = SIXARRAY;
	if( strchr( argv[1], '1' ) )
	   ftype = ONEFILE;
	strncpy(chars, argv[2], 10);
//	strlwr(chars);               /****this function missing in gcc ******/
	low = atoi(argv[3]);
	high = atoi(argv[4]);
	if( !low || !high || (low > high))
	{
	   cerr << "ERROR: incorrect values for x1 or x2" << endl;
	   exit(1);
	}
	if(argc > 5)
	   thepath = argv[5];
	Convert(chars, low, high, atype, ftype, thepath);
    }

    return 0;
}
