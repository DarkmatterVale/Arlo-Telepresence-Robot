package telepresence.device.test;

/**
 *
 * @author vale_000
 */
import javax.swing.*;

public class TelepresenceDeviceTest extends JFrame
{
  public static void main( String [] args )
  {
    //Initialize the JFrame
    JFrame missionControl = new TelepresenceDeviceTest();
    
    //Create a GUIPanel object
    JPanel missionControlPanel = new GUIPanel();
    
    //Add panel to frame
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
