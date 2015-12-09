
public class Locator {
	
	private static ControllerInterface controller;	
	private boolean debug;
	
	public Locator(){}
	
	static ControllerInterface getController()
	{
		return controller;
	}
	
	static void provideController(ControllerInterface service) 
	{
		if (service == null) 
		{
			
		} else {
			controller = service;
		}
	}
}
