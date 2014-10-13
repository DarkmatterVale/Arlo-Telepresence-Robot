//Controller code here for using the controller to talk to the Computer

/*

Author: Vale Tolpegin ( ValeT )

*/

#include "simpletools.h"                      // Include simple tools
#include "fdserial.h"
#include "adcDCpropab.h"

fdserial *term; //Create term variable

int main()
{
  simpleterm_close(); //close the communication to SimpleIDE ( standard communication protocol for over USB )
  term = fdserial_open( 31, 30, 0, 115200 ); //open the serialport
  adc_init( 21, 20, 19, 18 ); //initialize the oltage readers

  float lrV, udV; //create 2 analogue input variables

  while ( 1 )
  {
    udV = adc_volts( 2 ); //read in the input variables
    lrV = adc_volts( 3 );

    if ( udV < 1.00 ) //if the joystick is facing downwards, send over USB the commnd to go backward
    {
      dprint( term, "b" );
    } else if ( udV > 4.00 ) //if the jostick is facing upwards, send over USB the command to go forward
    {
      dprint( term, "f" );
    } else if ( udV < 4.00 && udV > 1.00 && lrV < 4.00 && lrV > 1.00 ) //if the joystick is in the center, send over USB th command to stop
    {
      dprint( term, "s" );
    } else if ( lrV < 1.00 ) //if the joystick is facing left, send over USB the command to turn left
    {
      dprint( term, "l" );
    } else if ( lrV > 4.00 ) //if the joystik is facing right, send over USB the command to turn right
    {
      dprint( term, "r" );
    }

    pause( 50 ); //only check the joystick 20 times a second
  }
}
