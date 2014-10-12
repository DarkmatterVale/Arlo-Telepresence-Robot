//ActivityBot code here for moving the ActivityBot based on the value returned from the controller

//PSEUDOCODE:
//while true
  //Read in computer signals
  //if forward
    //go forward
  //else if left
    //turn left
  //else if right
    //turn right
  //else if backward
    //go backward

#include "simpletools.h"                      // Include simple tools
#include "fdserial.h"
#include "abdrive.h"

fdserial *term;

int main()                                    // Main function
{
  simpleterm_close();
  term = fdserial_open( 31, 30, 0, 115200 );

  char c;

  while ( 1 )
  {
    c = fdserial_rxChar( term );
 
    if ( c == 'f' )
    {
      drive_speed( 64, 64 );
    } else if ( c == 'b' )
    {
      drive_speed( -64, -64 );
    } else if ( c == 'l' )
    {
      drive_speed( 0, 64 );
    } else if ( c == 'r' )
    {
      drive_speed( 64, 0 );
    } else if ( c == 's' )
    {
      drive_speed( 0, 0 );
    }
  }
}
