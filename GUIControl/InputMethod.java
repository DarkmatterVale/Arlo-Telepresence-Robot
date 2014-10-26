public class InputMethod
{
  public InputMethod()
  {
  }
  
  public int getInput( int decision )
  {
    //If the passed value is 0, get input from arrow keys
    //Else if passed value is 1, get input from the controller
    //Then return the value
    
    switch decision:
      case 0:
        return controllerInput();
      case 1:
        return arrowKeyInput();
    
    return -1;
  }
  
  public String controllerInput()
  {
    //In controllerInput, get values from Joystick on ActivityBoard
  }
  
  public String arrowKeyInput()
  {
    //In arrowKeyInput, get values from arrow keys on computer
  }
}
