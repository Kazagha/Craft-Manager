
public class Locator {
	
	private static ControllerInterface controller;	
	private static ViewInterface view;
	private static ModelInterface model;
	
	public Locator(){}
	
	static ControllerInterface getController()
	{
		return controller;
	}
	
	static ViewInterface getView()
	{
		return view;
	}
	
	static ModelInterface getModel()
	{
		return model;
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
	
	static void provideModel(ModelInterface service)
	{
		if (service == null)			
		{
			
		} else {
			model = service;
		}
	}
}
