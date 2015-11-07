import java.util.Observable;
import java.util.Observer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class ViewFX implements Observer {

	Scene scene;
	
	final int SCENE_WIDTH = 450;
	final int SCENE_HEIGHT = 650;
	
	public ViewFX()
	{
		GridPane grid = new GridPane();		
		
		scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
	}
	
	private void removeAllItems() {}
	
	public Scene getScene()
	{
		return scene;
	}	
	
	public void hookUpEvents(EventHandler<Event> handler) {}

	@Override
	public void update(Observable obs, Object obj) {}	
}
