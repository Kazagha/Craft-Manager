import javafx.application.Application;
import javafx.stage.Stage;

public class CraftManager extends Application {

	private ViewFX v;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Craft Manager");
		primaryStage.setScene(v.getScene());
		primaryStage.show();
	}
	
	public static void main (String[] args)
	{
		Model m = new Model();
		ViewFX v = new ViewFX();
		
		//new Controller(m, v);		
		
		launch();
	}
}
