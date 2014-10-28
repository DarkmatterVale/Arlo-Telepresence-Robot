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

public class GUIPanel extends JPanel
{
  //Instantiate 3 buttons, 1 border, 3 JLabels, 1 text field
  JButton Start, Stop, Exit;
  JTextField InformationSent;
  JLabel Sending, ProgramStatus, GmailStatus;
  JPanel gmailPanel, programStatus;
  
  //Instantiate variables for the state of the program
  public boolean programStatusValue, exitValue;
  
  //Instantiate Java Control object
  public MissionControl javaControl;
  
  public GUIPanel()
  {
    //Set values for program status variables
    programStatusValue = false;
    exitValue = false;
    
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
    Start.addActionListener( new StartButtonListener() );
    Stop.addActionListener( new StopButtonListener() );
    Exit.addActionListener( new ExitButtonListener() );
    
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
    
    while ( true )
    {
      try
      {
        //Edit fields based on values from javaControl object
        ProgramStatus = javaControl.getProgramStatus();
        InformationSent = javaControl.getInfoSent();
        GmailStatus = javaControl.getGmailStatus();
      }
      catch ( Exception ex )
      {
        //If unable to grab the data from javaControl object, set values to Error...
        InformationSent.setText( "Communication Error" );
        ProgramStatus = new JLabel( "Communication Error" );
        GmailStatus = new JLabel( "Communication Error" );
      }
    }
  }
  
  public class StartButtonListener extends JPanel implements ActionListener
  {
    public void actionPerformed( ActionEvent source )
    {
      if ( programStatusValue == true )
      {
      } else if ( programStatusValue == false )
      {
        //Code to deal with event when Start button is pressed and the Start button has not been pressed before
        progamStatusValue = true;
        
        javaControl = new MissionControl();
      }
    }
  }
  
  public class StopButtonListener extends JPanel implements ActionListener
  {
    public void actionPerformed( ActionEvent source )
    {
      if ( programStatusValue == false )
      {
      } else if ( programStatusValue == true )
      {
        //Deal with event when Stop button is pressed
        programStatusValue = false;
        
        javaControl = null;
      }
    }
  }
  
  public class ExitButtonListener extends JPanel implements ActionListener
  {
    public void actionPerformed( ActionEvent source )
    {
      //Deal with event when Exit button is pressed
      //v1.0: exitValue = true;
      
      System.exit( 0 );
    }
  }
}
