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
  //Create the JFrame and JPanel to hold all of the content
  JFrame missionControl;
  JPanel missionControlPanel;
  
  public static void main( String [] args )
  {
    //Initialize the JFrame
    missionControl = new JFrame();
    
    //Call setupFrame() to set up the frame
    setupFrame();
    
    //set frame to visible
    missionControl.setVisible( true );
  }
  
  public void setupFrame()
  {
    //Set frame settings
    missionControl.setTitle( "Arlo Telepresence Device Mission Control" );
    missionControl.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    missionControl.setSize( 500, 500 );
    
    //Create a GUIPanel object
    missionControlPanel = new GUIPanel();
    
    //Add panel to frame
    missionControl.add( missionControlPanel );
  }
}
