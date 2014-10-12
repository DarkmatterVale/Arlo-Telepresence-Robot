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

fdserial *xbee; //Create full-duplex XBee serial communication variable

int main()                                    // Main function
{
  xbee = fdserial_open( 9, 8, 0, 9600 ); //Initialize communication

  char c; //Create char

  while ( 1 )
  {
    c = fdserial_rxChar( xbee ); //get all values sent to the ActivityBot
 
    if ( c == 'f' ) //if the robot was told to go forward, go forward
    {
      drive_speed( 64, 64 );
    } else if ( c == 'b' ) //if the robot was told to go backward, go backward
    {
      drive_speed( -64, -64 );
    } else if ( c == 'l' ) //if the robot was told to go left, go left
    {
      drive_speed( 0, 64 );
    } else if ( c == 'r' ) //if the robot was told to go right, go right
    {
      drive_speed( 64, 0 );
    } else if ( c == 's' ) //if the robot is told to stop, stop
    {
      drive_speed( 0, 0 );
    }
  }
}
