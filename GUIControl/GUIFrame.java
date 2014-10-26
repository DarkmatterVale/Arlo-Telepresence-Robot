/*

Author: DarkmatterVale ( ValeT )

*/

/*

To Do list:
  - Add setter and getter methods
*/

import javax.swing.*;
import java.awt.*;

public class GUIFrame extends JFrame
{
  JFrame missionControl;
  
  public GUIFrame()
  {
    //Initialize the JFrame
    missionControl = new JFrame();
    
    //Call setupFrame() to set up the frame
    setupFrame();
    
    //set frame to visible
    missionControl.setVisible( true );
  }
  
  public setupFrame()
  {
    //Set frame settings
    missionControl.setTitle( "Arlo Telepresence Device Mission Control" );
    missionControl.setDefaultCloseOption( JFrame.EXIT_ON_CLOSE );
    missionControl.setSize( 500, 500 );
    
    //Create a GUIPanel object
    missionControlPanel = new GUIPanel();
    
    //Add panel to frame
    missionControl.add( missionControlPanel );
  }
  
  //Add setter and getter methods below
}
