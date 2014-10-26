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

public class GUIPanel
{
  //Code for GUI goes in here
  //Instantiate 3 buttons, 1 border, 4 JLabels, 1 text field
  
  public GUIPanel()
  {
    //Set all of the values for the Panel
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
