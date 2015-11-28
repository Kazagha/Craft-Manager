import java.util.Observable;
import java.util.Observer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ViewFX implements Observer {

	private Scene scene;
	private Button test;
	
	final int SCENE_WIDTH = 450;
	final int SCENE_HEIGHT = 700;
	
	public ViewFX()
	{
		init();			
	}
	
	public void init()
	{		
		// Set the Scene and style sheet
		BorderPane root = new BorderPane();
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		scene.getStylesheets().add(
				ViewFX.class.getResource("ViewFX.css").toExternalForm());
		
		// Set the Top/Center/Bottom panes
		HBox top = new HBox();
		root.setTop(top);
		
		StackPane center = new StackPane();
		root.setCenter(center);
		
		HBox bottom = new HBox();
		root.setBottom(bottom);
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
