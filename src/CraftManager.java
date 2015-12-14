import javafx.application.Application;
import javafx.stage.Stage;

public class CraftManager extends Application {

	private static ViewFX view;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Craft Manager");
		primaryStage.setScene(view.getScene());
		primaryStage.show();
	}
	
	public static void main (String[] args)
	{
		Model model = new Model();
		view = new ViewFX();
		
		ControllerFX controller = new ControllerFX(model, view);	
		Locator.provideController(controller);
		Locator.provideView(view);
		Locator.provideModel(model);
		
		launch();
	}
}
