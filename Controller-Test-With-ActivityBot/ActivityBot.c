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

fdserial *xbee;

int main()                                    // Main function
{
  xbee = fdserial_open( 9, 8, 0, 9600 );

  char c;

  while ( 1 )
  {
    c = fdserial_rxChar( xbee );
 
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
