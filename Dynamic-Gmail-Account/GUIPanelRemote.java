package arlo.telepresence.device;
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

public class GUIPanelRemote extends JPanel
{
  //Creating username and password variables for the Gmail account
  public String username, password, defaultUsername, defaultPassword;
  
  //Instantiate 3 buttons, 1 border, 3 JLabels, 1 text field
  JButton Start, Stop, Exit, GmailAccountSwitch, GmailAccountDefault;
  JTextField InformationSent, GmailStatusField, ProgramStatusField, GmailUsername, GmailPassword;
  JLabel Sending, ProgramStatus, GmailStatus, GmailUsernameLabel, GmailPasswordLabel;
  JPanel gmailPanel, programStatusPanel;
  
  //Instantiate variables for the state of the program
  public boolean programStatusValue;
  
  //Instantiate Java Control object
  public Thread javaControl;
  
  public GUIPanelControl()
  {
    //Set values for program
    programStatusValue = false;
    username           = "";
    password           = "";
    defaultUsername    = "";
    defaultPassword    = "";
    
    //Create panels
    gmailPanel =         new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    programStatusPanel = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    
    //Set all of the values for the panels
    Start =               new JButton( "Start" );
    Stop =                new JButton( "Stop" );
    Exit =                new JButton( "Exit" );
    GmailAccountSwitch =  new JButton( "Switch Gmail Accounts" );
    GmailAccountDefault = new JButton( "Set Gmail Account to Default" );
    
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
    GmailAccountSwitch.addActionListener( new GmailAccountSwitchListener() );
    GmailAccountDefault.addActionListener( new GmailAccountDefaultListener() );
    
    //Add components to gmailPanel
    gmailPanel.add( Sending );
    gmailPanel.add( InformationSent );
    gmailPanel.add( GmailStatus );
    gmailPanel.add( GmailStatusField );
    gmailPanel.add( GmailUsernameLabel );
    gmailPanel.add( GmailUsername );
    gmailPanel.add( GmailPasswordLabel );
    gmailPanel.add( GmailPassword );
    gmailPanel.add( GmailAccountSwitch );
    gmailPanel.add( GmailAccountDefault );
    
    //Add components to programStatus panel
    programStatusPanel.add( ProgramStatus );
    programStatusPanel.add( ProgramStatusField );
    programStatusPanel.add( Start );
    programStatusPanel.add( Stop );
    programStatusPanel.add( Exit );
    
    //Add the panels to the frame
    this.setLayout( new BorderLayout() );
    this.add( gmailPanel, BorderLayout.CENTER );
    this.add( programStatusPanel, BorderLayout.SOUTH );
  }
  
  public boolean detectOs()
  {
    //detect OS
    String os = System.getProperty( "os.name", "generic" ).toLowerCase();

    if ( os.contains( "win" ) )
    {
        return true;
    } else if ( os.contains( "mac" ) )
    {
        //JOptionPane dialog box saying OS is not supported
        JOptionPane.showMessageDialog( null, os + " is not supported", "Invalid OS", JOptionPane.ERROR_MESSAGE );

        //return false since os is not supported
        return false;
    } else
    {
        //JOptionPane dialog box saying OS is not supported
        JOptionPane.showMessageDialog( null, os + " is not supported", "Invalid OS", JOptionPane.ERROR_MESSAGE );

        //return false since the os is not supported
        return false;
    }
  }
  
  //Class used to deal with what happens when the Set Gmail Account button is pressed
  public class GmailAccountDefaultListener extends JPanel implements ActionListener
  {
    @Override
    public void actionPerformed( ActionEvent source )
    {
        if ( programStatusValue == false )
        {
            if ( !username.equals( "" ) && !password.equals( "" ) )
            {
                //Send message to old Gmail account that Gmail account has been switched, send new credentials
                String host = "smtp.gmail.com";
                Properties props = new Properties();
                // set any needed mail.smtps.* properties here
                Session session = Session.getInstance(props);
                MimeMessage msg = new MimeMessage(session);

                try {
                    // set the message content here
                    msg.setFrom();      
                    msg.setRecipients( Message.RecipientType.TO, username );
                    msg.setSubject( "defaultaccount " + defaultUsername + " " + defaultPassword );
                    msg.setContent( "", "text/html;charset=UTF-8"); 

                    Transport t = session.getTransport("smtps");

                    try {
                        //Send the message over Gmail
                        t.connect(host, defaultUsername, defaultPassword);
                        t.sendMessage(msg, msg.getAllRecipients());
                    } catch ( Exception ex )
                    {
                        //ex.printStackTrace(); For debugging
                    } finally {
                        t.close();
                    }
                } catch ( Exception ex )
                {
                }
            } else
            {
                //Set new Gmail account and reset data fields
                defaultUsername = GmailUsername.getText();
                defaultUsername = GmailPassword.getText();
                
                if ( username.equals( "" ) && password.equals( "" ) )
                {
                    username = defaultUsername;
                    password = defaultPassword;
                }
          
                GmailUsername.setText( "Enter Gmail username here" );
                GmailPassword.setText( "Enter Gmail password here" );
            }
        } else if ( programStatusValue == true )
          {
              //show JOptionPane window saying program must be off to set default Gmail account
          }
      }
  }
  
  //Class used to deal with what happens when the Switch Gmail Account button is pressed
  public class GmailAccountSwitchListener extends JPanel implements ActionListener
  {
    @Override
    public void actionPerformed( ActionEvent source )
    {
      if ( programStatusValue == false )
      {
        if ( !username.equals( "" ) && !password.equals( "" ) )
        {
          //Send message to old Gmail account that Gmail account has been switched, send new credentials
          String host = "smtp.gmail.com";
          Properties props = new Properties();
          // set any needed mail.smtps.* properties here
          Session session = Session.getInstance(props);
          MimeMessage msg = new MimeMessage(session);
          
          try {
            // set the message content here
            msg.setFrom();      
            msg.setRecipients( Message.RecipientType.TO, username );
            msg.setSubject( "newgmailaccount " + username + " " + password );
            msg.setContent( "", "text/html;charset=UTF-8"); 
          
            Transport t = session.getTransport("smtps");
          
              try {
                //Send the message over Gmail
                t.connect(host, defaultUsername, defaultPassword);
                t.sendMessage(msg, msg.getAllRecipients());
              } catch ( Exception ex )
              {
                //ex.printStackTrace(); For debugging
              } finally {
                t.close();
              }
          } catch ( Exception ex )
          {
          }
        } else
        {
          //Set new Gmail account and reset data fields
          username = GmailUsername.getText();
          password = GmailPassword.getText();
          
          if ( defaultUsername.equals( "" ) && defaultPassword.equals( "" ) )
          {
              defaultUsername = username;
              defaultPassword = password;
          }
          
          GmailUsername.setText( "Enter Gmail username here" );
          GmailPassword.setText( "Enter Gmail password here" );
        }
      } else if ( programStatusValue == true )
      {
        //Create a JOptionPane message saying to stop the program to switch Gmail accounts
        JOptionPane.showMessageDialog( null, "Program must be off to switch Gmail accounts", "", JOptionPane.ERROR_MESSAGE );
      }
    }
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
        else if ( programStatusValue == false && detectOs() )
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
      
      ProgramStatusField.setText( "OFF" );
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
      //serialPort = new SerialPort(args[0]); // Use this to get the COM port form the command line when you bild a JAR file.
      outputPort = new SerialPort("COM3");
      try {
        //System.out.print("Opening " + args[0] + " at");
        System.out.print("Opening COM3 at");
        outputPort.openPort();
        System.out.print(" 115200, 8, 1, 0 and ");
        outputPort.setParams(115200, 8, 1, 0);
        //Preparing a mask. In a mask, we need to specify the types of events that we want to track.
        //Well, for example, we need to know what came some data, thus in the mask must have the
        //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states 
        //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR
        /*
          Removed all of mask info because I am not reading anything from a serial port
        */
        //int mask = SerialPort.MASK_RXCHAR;
        //Set the prepared mask
        //outputPort.setEventsMask(mask);
        //Add an interface through which we will receive information about events
        /*
          Removed all of SerialPortReader() code because I am using Gmail get commands instead
        */
        System.out.println("waiting for data . . .");
        //inputPort.addEventListener(new SerialPortReader());
      }
      catch (SerialPortException ex) {
        System.out.println("Serial Port Opening Exception: " + ex);
      }
    
      Properties props = new Properties();
      Session session = Session.getInstance(props); 
      MimeMessage msg = new MimeMessage(session); 
      Message currentMessage;
        
      while(true) {
        try
        {
          Store store = session.getStore("imaps");
          store.connect("imap.gmail.com", "**********@gmail.com", "***********");
          System.out.println(store);

          Folder inbox = store.getFolder("Inbox");
          inbox.open(Folder.READ_ONLY);
          Message messages[] = inbox.getMessages();
        
          if ( inbox.getMessageCount > 213 )
          {
            currentMessage = messages[ inbox.getMessageCount() - 1 ];
            outputString = currentMessage.getSubject();
          }
        
          store.close();
        } catch ( Exception ex )
        {
          ex.printStackTrace();
        }
      
        try {
          outputPort.writeString( outputString );
        } catch (SerialPortException e) {
          e.printStackTrace();
        }
      }
    }
}
}
