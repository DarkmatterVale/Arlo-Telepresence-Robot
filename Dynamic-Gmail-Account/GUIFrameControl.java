package telepresence.device.test;

/**
 *
 * @author Vale Tolpegin
 */
import javax.swing.*;

public class GUIFrameControl extends JFrame
{
  public static void main( String [] args )
  {
    //Initialize the JFrame
    JFrame missionControl = new TelepresenceDeviceTest();
    
    //Create a GUIPanel object and add panel to frame
    JPanel missionControlPanel = new GUIPanel();
    missionControl.add( missionControlPanel );
    
    //set frame to visible
    missionControl.setVisible( true );
  }
  
  public void TelepresenceDeviceTest()
  {
    //Set frame settings
    setTitle( "Arlo Telepresence Device Mission Control" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setSize( 500, 500 );
  }
}
