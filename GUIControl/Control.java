/*

Base code courtesy of ChrisL8
Edited and added to by ValeT ( DarkmatterVale )

To Do:
  -Add comments ( Current comments are from ChrisL8 )
  -Add dynamic COM port support
  -Add GUI support

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

static SerialPort inputPort;
static String outputString = "s";
static String data = "";

//GUI component initialization should go here
//GUI should be used in serialPortReader()

public static void main(String[] args) {
    //Scanner input = new Scanner(System.in);
    //serialPort = new SerialPort(args[0]); // Use this to get the COM port form the command line when you bild a JAR file.
    inputPort = new SerialPort("COM3");
    try {
        //System.out.print("Opening " + args[0] + " at");
        System.out.print("Opening COM3 at");
        inputPort.openPort();
        System.out.print(" 115200, 8, 1, 0 and ");
        inputPort.setParams(115200, 8, 1, 0);
        //Preparing a mask. In a mask, we need to specify the types of events that we want to track.
        //Well, for example, we need to know what came some data, thus in the mask must have the
        //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states 
        //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR
        int mask = SerialPort.MASK_RXCHAR;
        //Set the prepared mask
        inputPort.setEventsMask( mask );
        //Add an interface through which we will receive information about events
        System.out.println("waiting for data . . .");
        inputPort.addEventListener(new SerialPortReader());
    }
    catch (SerialPortException ex) {
        System.out.println("Serial Port Opening Exception: " + ex);
    }
    
    while(true) {
    }
}


static class SerialPortReader implements SerialPortEventListener {
    @Override
    public void serialEvent(SerialPortEvent event) {
        String host = "smtp.gmail.com";
        String username = "*****************@gmail.com";
        String password = "*************";
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
        if(event.isRXCHAR()){
            /* See original code,
             * it waited for a certain number of bytes,
             * but if I want the characters, why do that?
             */
            //if(event.getEventValue() == 10){
                try {
                    data = inputPort.readString();
                    //System.out.println("Data: " + data); // For debugging
                    if ( data != null )
                    {
                        if ( !(data.equals( outputString )) )
                        {
                          try
                          {
                            // set the message content here
                            msg.setFrom();      
                            msg.setRecipients( Message.RecipientType.TO, "************@gmail.com" );
                            msg.setSubject( "Data" );
                            msg.setContent( data, "text/html;charset=UTF-8"); 
                            Transport t = session.getTransport("smtps");
                            try {
                              t.connect(host, username, password);
                              t.sendMessage(msg, msg.getAllRecipients());
                              
                              //Set GUI component that shows user what is being sent over email
                              
                            } catch ( Exception ex )
                            {
                              ex.printStackTrace();
                            } finally {
                              t.close();
                            }
                            } catch ( Exception ex )
                            {
                              ex.printStackTrace();
                            }
                          }
                        }
                        
                        System.out.print(data);
                        outputString = data;
                    }
                }
                catch (SerialPortException ex) {
                    System.out.println("Serial Port Reading Exception: " + ex);
                }
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
        }
    }
}
}
