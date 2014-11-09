/*

Base code courtesy of ChrisL8, edited by DarkmatterVale ( ValeT )

*/

#include "simpletools.h"
#include "fdserial.h"

fdserial *term;

int main()
{
  simpleterm_close(); // Close simplex serial terminal
  term = fdserial_open(31, 30, 0, 115200); // Open Full Duplex serial connection

  while(1)
  {
    dprint(term, "Enter a string of 50 characters or less:\n");
    char buf[51]; // A Buffer long enough to hold the longest line you may send.
    int count = 0;
    while (count < 51) {
      buf[count] = fdserial_rxChar(term);
      if (buf[count] == '\r' || buf[count] == '\n')
        break;
        
      if ( buf[count] == '1' )
      {
        high( 26 );
        low( 27 );
      }
      
      if ( buf[count] == '2' )
      {
        low( 26 );
        high( 27 );
      }        
        
      count++;
    }
    buf[count] = '\0';
        
    dprint(term, "%s\n", buf);
  }
}
