import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class ViewFX {

	Scene scene;
	
	public ViewFX()
	{
		GridPane grid = new GridPane();		
		
		scene = new Scene(grid);
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
