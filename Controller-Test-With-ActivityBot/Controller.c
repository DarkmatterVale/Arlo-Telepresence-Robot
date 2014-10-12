//Controller code here for using the controller to talk to the Computer

#include "simpletools.h"                      // Include simple tools
#include "fdserial.h"
#include "adcDCpropab.h"

fdserial *term;

int main()
{
  simpleterm_close();
  term = fdserial_open( 31, 30, 0, 115200 );
  adc_init( 21, 20, 19, 18 );

  float lrV, udV;

  while ( 1 )
  {
    udV = adc_volts( 2 );
    lrV = adc_volts( 3 );

    if ( udV < 1.00 )
    {
      dprint( term, "b" );
    } else if ( udV > 4.00 )
    {
      dprint( term, "f" );
    } else if ( udV < 4.00 && udV > 1.00 && lrV < 4.00 && lrV > 1.00 )
    {
      dprint( term, "s" );
    } else if ( lrV < 1.00 )
    {
      dprint( term, "l" );
    } else if ( lrV > 4.00 )
    {
      dprint( term, "r" );
    }

    pause( 50 );
  }
}
