/*

Base code courtesy of ChrisL8
Edited and added to by ValeT ( DarkmatterVale )

To Do:
  -Add comments ( Current comments are from ChrisL8 )
  -Remove SerialPortReader
  -Add dynamic COM port support
  
*/

/**
 *
 * @author DarkmatterVale
 */

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class ArloTelepresenceDeviceActivtyBot {

static SerialPort outputPort;
static String outputString = "s";

public static void main(String[] args) {
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
