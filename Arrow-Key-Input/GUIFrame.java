/*

Author: DarkmatterVale ( ValeT )

*/

/*

To Do list:
  - Complete
*/

import javax.swing.*;
import java.awt.*;

public class GUIFrame extends JFrame
{
  public static void main( String [] args )
  {
    //Initialize the JFrame
    JFrame missionControl = new GUIFrame();
    
    //set frame to visible
    missionControl.setVisible( true );
  }
  
  public void GUIFrame()
  {
    //Set frame settings
    setTitle( "Arlo Telepresence Device Mission Control" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setSize( 500, 500 );
    
    //Create a GUIPanel object
    JPanel missionControlPanel = new GUIPanel();
    
    //Add panel to frame
    add( missionControlPanel );
  }
}
