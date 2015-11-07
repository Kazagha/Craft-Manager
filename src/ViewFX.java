import java.util.Observable;
import java.util.Observer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ViewFX implements Observer {

	private Scene scene;
	private GridPane grid;
	private Button test;
	
	final int SCENE_WIDTH = 450;
	final int SCENE_HEIGHT = 650;
	
	public ViewFX()
	{
		init();	
		
		grid.add(test, 0, 0);
	}
	
	public void init()
	{	
		grid = new GridPane();
		scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		
		test = new Button(ControllerFX.Action.ADDGOLD.toString());
		test.setOnAction(actionEvent -> ControllerFX.getInstance().addGold());
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
