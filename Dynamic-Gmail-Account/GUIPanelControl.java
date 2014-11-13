/**
 *
 * @author Vale Tolpegin ( valetolpegin@gmail.com )
 */

/*

To Do list:
  -Add documentation
  -Dynamic COM port support
  -Add dynamic Gmail support

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

//Importing libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class GUIPanelControl extends JPanel
{
 
  //Instantiate 3 buttons, 1 border, 3 JLabels, 1 text field
  JButton Start, Stop, Exit;
  JTextField InformationSent, GmailStatusField, ProgramStatusField, GmailUsername, GmailPassword;
  JLabel Sending, ProgramStatus, GmailStatus, GmailUsernameLabel, GmailPasswordLabel;
  JPanel gmailPanel, programStatusPanel;
  
  //Instantiate variables for the state of the program
  public boolean programStatusValue;
  
  //Instantiate Java Control object
  public Thread javaControl;
  
  public GUIPanelControl()
  {
    //Set values for program status variable
    programStatusValue = false;
    
    //Create panels
    gmailPanel =         new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    programStatusPanel = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    
    //Set all of the values for the panels
    Start = new JButton( "Start" );
    Stop =  new JButton( "Stop" );
    Exit =  new JButton( "Exit" );
    
    InformationSent =    new JTextField( "Waiting for connection..." );
    GmailStatusField =   new JTextField( "Waiting for connection..." );
    ProgramStatusField = new JTextField( "OFF" );
    GmailUsername =      new JTextField( "Enter Gmail username here" );
    GmailPassword =      new JTextField( "Enter Gmail password here" );
    
    Sending =            new JLabel( "Sending: " );
    GmailStatus =        new JLabel( "Status:" );
    ProgramStatus =      new JLabel( "Status:" );
    GmailUsernameLabel = new JLabel( "Username:" );
    GmailPasswordLabel = new JLabel( "Password:" );
    
    //Add event listeners and set settings
    ProgramStatusField.setEditable( false );
    GmailStatusField.setEditable( false );
    InformationSent.setEditable( false );
    GmailUsername.setEditable( true );
    GmailPassword.setEditable( true );
    Start.addActionListener( new StartButtonListener() );
    Stop.addActionListener( new StopButtonListener() );
    Exit.addActionListener( new ExitButtonListener() );
    
    //Add components to gmailPanel
    gmailPanel.add( Sending );
    gmailPanel.add( InformationSent );
    gmailPanel.add( GmailStatus );
    gmailPanel.add( GmailStatusField );
    gmailPanel.add( GmailUsernameLabel );
    gmailPanel.add( GmailUsername );
    gmailPanel.add( GmailPasswordLabel );
    gmailPanel.add( GmailPassword );
    
    //Add components to programStatus panel
    programStatusPanel.add( ProgramStatus );
    programStatusPanel.add( ProgramStatusField );
    programStatusPanel.add( Start );
    programStatusPanel.add( Stop );
    programStatusPanel.add( Exit );
    
    System.out.println( "ADDING LAYOUT..." );
    //Add the panels to the frame
    this.setLayout( new BorderLayout() );
    this.add( gmailPanel, BorderLayout.CENTER );
    this.add( programStatusPanel, BorderLayout.SOUTH );
  }
  
  //Class used to deal with what happens when the Start button is pressed
  public class StartButtonListener extends JPanel implements ActionListener
  {
    @Override
    public void actionPerformed( ActionEvent source )
    {
        if ( programStatusValue == true )
        {
        }
        else if ( programStatusValue == false )
        {
          //Code to deal with event when Start button is pressed and the Start button has not been pressed before
          programStatusValue = true;

          javaControl = new Thread( new MissionControl() );
          javaControl.start();
        }
    }
  }
  
  //Class used to deal with what happens when the Stop button is pressed
  public class StopButtonListener extends JPanel implements ActionListener
  {
    @Override
    public void actionPerformed( ActionEvent source )
    {
      programStatusValue = false;
    }
  }
  
  public class ExitButtonListener extends JPanel implements ActionListener
  {
    @Override
    public void actionPerformed( ActionEvent source )
    {
      //Deal with event when Exit button is pressed      
      System.exit( 0 );
    }
  }
  
  //Java object used to communicate with the ActivityBoard and Gmail
  public class MissionControl extends Thread
  {
    //Create port settings and variables
    SerialPort inputPort;
    String outputString = "s";
    String data = "";
    
    //Method invoked when the thread is started
    @Override
    public void run()
    {
        //Setting the program status value to OK once the thread is started
        ProgramStatusField.setText( "OK" );
        
        //Opening port on COM3 --- ADD DYNAMIC COM PORT SUPPORT
        inputPort = new SerialPort("COM3");
        try {
            //System.out.print("Opening COM3 at");
            inputPort.openPort();
            //System.out.print(" 115200, 8, 1, 0 and ");
            inputPort.setParams(115200, 8, 1, 0);
            //Preparing a mask. In a mask, we need to specify the types of events that we want to track.
            //Well, for example, we need to know what came some data, thus in the mask must have the
            //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states 
            //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR
            int mask = SerialPort.MASK_RXCHAR;
            //Set the prepared mask
            inputPort.setEventsMask( mask );
            //Add an interface through which we will receive information about events
            //System.out.println("waiting for data . . .");
            inputPort.addEventListener(new SerialPortReader());
        }
        catch (SerialPortException ex) {
            //Display that there was an error with opening the port
            System.out.println("Serial Port Opening Exception: " + ex);
            ProgramStatusField.setText( "ERROR..." );
        }
    }

    //This class is called when the program is started and communication has begun between the ActivityBorad and the computer
    class SerialPortReader extends JPanel implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event)
        {
            /*
            
              *** The username and password need to be changed based on the fields ***
            
            */
            //Setting the settings for Gmail
            String host = "smtp.gmail.com";
            String username = "***********@gmail.com";
            String password = "****************";
            Properties props = new Properties();
            // set any needed mail.smtps.* properties here
            Session session = Session.getInstance(props);
            MimeMessage msg = new MimeMessage(session);
            //Object type SerialPortEvent carries information about which event occurred and a value.
            //For example, if the data came a method event.getEventValue() returns us the number of bytes in the input buffer.
            /* For debugging, this should always be 1 unless we are
             * waiting for more than one byte, otherwise it just junks up the output :)
             */
            //System.out.println("Bytes: " + event.getEventType());
            //If there is a command sent over USB and the program is supposed to be on
            if(event.isRXCHAR() && programStatusValue == true ){
                /* See original code,
                 * it waited for a certain number of bytes,
                 * but if I want the characters, why do that?
                 */
                //if(event.getEventValue() == 10){
                    try {
                        //Get data from USB port
                        data = inputPort.readString();
                        //System.out.println("Data: " + data); // For debugging
                        //If there is data
                        if ( data != null )
                        {
                            //And the data coming in is not the same as before
                            if ( !(data.equals( outputString )) )
                            {
                              try
                              {
                                // set the message content here
                                msg.setFrom();      
                                msg.setRecipients( Message.RecipientType.TO, "***********@gmail.com" );
                                msg.setSubject( data );
                                msg.setContent( "Data", "text/html;charset=UTF-8"); 
                                Transport t = session.getTransport("smtps");

                                //Set the GUI InformationSent box
                                InformationSent.setText( data );
                                
                                try {
                                  //Send the message over Gmail
                                  t.connect(host, username, password);
                                  t.sendMessage(msg, msg.getAllRecipients());

                                  //Set GUI component that shows user what is being sent over email
                                  GmailStatusField.setText( "OK" );
                                } catch ( Exception ex )
                                {
                                  //If there is an error, set the fields
                                  GmailStatusField.setText( "Error..." );
                                  ProgramStatusField.setText( "Error" );
                                  
                                  //ex.printStackTrace(); For debugging
                                } finally {
                                  t.close();
                                }
                              } catch ( Exception ex )
                              {
                                //ex.printStackTrace(); For debugging
                              }
                            }
                          }

                          //System.out.print(data);
                          outputString = data;
                    }
                    catch (SerialPortException ex) {
                        System.out.println("Serial Port Reading Exception: " + ex);
                    }
                    
                    //As long as there is nothing wrong with the sending of the message
                    ProgramStatusField.setText( "OK" );
                //}
            }
            //If the CTS line status has changed, then the method event.getEventValue() returns 1 if the line is ON and 0 if it is OFF.
            else if(event.isCTS()){
                if(event.getEventValue() == 1){
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){
                if(event.getEventValue() == 1){
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            } else if ( programStatusValue == false )
            {
                GmailStatusField.setText( "OFF" );
                InformationSent.setText( "Waiting for connection..." );
            }
        }
    }
    }
}
