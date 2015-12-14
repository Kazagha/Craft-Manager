
public class Locator {
	
	private static ControllerInterface controller;	
	private static ViewInterface view;
	
	public Locator(){}
	
	static ControllerInterface getController()
	{
		return controller;
	}
	
	static ViewInterface getView()
	{
		return view;
	}
	
	static void provideController(ControllerInterface service) 
	{
		if (service == null) 
		{
			
		} else {
			controller = service;
		}
	}
	
	static void provideView(ViewInterface service)
	{
		if (service == null) {
			
		} else {
			view = service;
		}
	}
}
