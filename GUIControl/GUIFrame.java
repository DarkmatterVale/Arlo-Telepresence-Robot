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
  //Create the JFrame and JPanel to hold all of the content
  JFrame missionControl;
  JPanel missionControlPanel;
  
  public GUIFrame()
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
    missionControl.setDefaultCloseOption( JFrame.EXIT_ON_CLOSE );
    missionControl.setSize( 500, 500 );
    
    //Create a GUIPanel object
    missionControlPanel = new GUIPanel();
    
    //Add panel to frame
    missionControl.add( missionControlPanel );
  }
  
  //Add setter and getter methods below
  
  public boolean getProgramStatus()
  {
    //Return program status ( whether the start button has been pressed )
    return missionControlPanel.getProgramStatus();
  }
  
  public void setProgramStatus( String status )
  {
    //Set the status symbol for the system status
    missionControlPanel.setProgramStatus( status );
  }
  
  public void setGmailStatus( String status )
  {
    //Set the status symbol for Gmail panel
    missionControlPanel.setGmailStatus( status );
  }
  
  public void setSendingValue( String value )
  {
    //Set the sending value for the Gmail panel
    missionControlPanel.setSendingValue( value );
  }
}
