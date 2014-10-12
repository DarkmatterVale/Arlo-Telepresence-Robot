import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author Vale Tolpegin
 */
public class GmailTestProject
{
   public static void main(String [] args)
   {          
        String host = "smtp.gmail.com";
        String username = "*****************@gmail.com";
        String password = "*************";
        Properties props = new Properties();
        // set any needed mail.smtps.* properties here
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        // set the message content here
        try
        {
            msg.setFrom();      
            msg.setRecipients(Message.RecipientType.TO, "************@gmail.com" );
            msg.setSubject("Subject");
            msg.setContent("Brought to you by Carls Junior", "text/html;charset=UTF-8"); 
            Transport t = session.getTransport("smtps");
            try {
                t.connect(host, username, password);
                t.sendMessage(msg, msg.getAllRecipients());
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
        
        try
        {
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "**********@gmail.com", "***********");
            System.out.println(store);

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            Message messages[] = inbox.getMessages();
            for(Message message : messages) {
                System.out.println( message.getSubject() );
            }
        } catch ( Exception ex )
        {
            ex.printStackTrace();
        }
   }
}
