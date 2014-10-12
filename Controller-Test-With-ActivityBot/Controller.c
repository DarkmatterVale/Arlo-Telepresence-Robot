//Controller code here for using the controller to talk to the Computer

#include "simpletools.h"                      // Include simple tools
#include "fdserial.h"
#include "adcDCpropab.h"

fdserial *xbee;

int main()
{
  xbee = fdserial_open( 9, 8, 0, 9600 );
  adc_init( 21, 20, 19, 18 );

  float lrV, udV;

  while ( 1 )
  {
    udV = adc_volts( 2 );
    lrV = adc_volts( 3 );

    if ( udV < 1.00 )
    {
      dprint( xbee, "b" );
    } else if ( udV > 4.00 )
    {
      dprint( xbee, "f" );
    } else if ( udV < 4.00 && udV > 1.00 && lrV < 4.00 && lrV > 1.00 )
    {
      dprint( xbee, "s" );
    } else if ( lrV < 1.00 )
    {
      dprint( xbee, "l" );
    } else if ( lrV > 4.00 )
    {
      dprint( xbee, "r" );
    }

    pause( 50 );
  }
}
