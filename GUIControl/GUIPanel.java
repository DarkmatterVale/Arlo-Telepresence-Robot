/*

Author: DarkmatterVale

*/

/*

To Do list:
  -Add basic GUI

*/

/*

Design:

For the first version ( v1.0 ) of the GUI, I am thinking of a BorderLayout style with different areas [eventually]
seperated by borders. The first version shall have 1 non-editable text field that will display what is being sent. In 
addition, the first version should also include an exit button, start and stop buttons, and an "LED" that will be green if 
the session/communication is working and red if the session/communication with the robot is not working. Finally, the 1st
version shall include a Gmail status indicator which will be a JLabel that is in one of two states, OK or NOT WORKING.

ALL OTHER FEATURES SHOULD BE ADDED TO LATER VERSIONS

*/

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class GUIPanel
{
  //Instantiate 3 buttons, 1 border, 3 JLabels, 1 text field
  JButton Start, Stop, Exit;
  JTextField informationSent;
  JLabel Sending, ProgramStatus, GmailStatus;
  JPanel gmailPanel, programStatus;
  
  public GUIPanel()
  {
    //Create panels
    gmailPanel = new JPanel();
    programStatus = new JPanel();
    
    //Set all of the values for the panels
    Start = new JButton( "Start" );
    Stop = new JButton( "Stop" );
    Exit = new JButton( "Exit" );
    
    InformationSent = new JTextField( "Waiting for connection..." );
    
    Sending = new JLabel( "Sending: " );
    GmailStatus = new JLabel( "Waiting for connection..." );
    ProgramStatus = new JLabel( "Status: OFF" );
    
    //Add event listeners and set settings
    InformationSent.setEditable( false );
    Start.addEventListener( new StartButtonListener() );
    Stop.addEventListener( new StopButtonListener() );
    Exit.addEventListener( new ExitButtonListener() );
    
    //Add components to gmailPanel
    gmailPanel.add( Sending );
    gmailPanel.add( InformationSent );
    gmailPanel.add( GmailStatus );
    
    //Add components to programStatus panel
    programStatus.add( ProgramStatus );
    programStatus.add( Start );
    programStatus.add( Stop );
    programStatus.add( Exit );
    
    //Add the panels to the frame
    this.setLayout( new BorderLayout() );
    this.add( BorderLayout.CENTER, gmailPanel );
    this.add( BorderLayout.SOUTH, programStatus );
  }
  
  public class StartButtonListener extends JPanel implements ActionListener
  {
    //Deal with event when Start button is pressed
  }
  
  public class StopButtonListener extends JPanel implements ActionListener
  {
    //Deal with event when Stop button is pressed
  }
  
  public class ExitButtonListener extends JPanel implements ActionListener
  {
    //Deal with event when Exit button is pressed
  }
  }
}
