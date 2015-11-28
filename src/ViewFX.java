import java.util.Observable;
import java.util.Observer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
		
		// Set the top pane
		top.getChildren().addAll(
				new Button("New Item"),
				new Button("Queue"),
				new Button("Complete")
				);
		
		// Set the bottom pane
		HBox bottomL = new HBox();
		bottomL.setPrefSize((root.getWidth() / 2) - 10, 25);
		bottomL.setId("ResourceHBox");
		HBox bottomR = new HBox();
		bottomR.setPrefSize((root.getWidth() / 2) - 10, 25);
		bottomR.setId("ResourceHBox");
		
		bottomL.getChildren().add(new Text("Gold: 10000"));
		bottomR.getChildren().add(new Text("XP: 1500"));
		
		bottom.getChildren().addAll(bottomL, bottomR);		
		
		// Set the Center Pane
		VBox itemPane = new VBox();
		itemPane.setAlignment(Pos.TOP_CENTER);
		VBox menuPane = new VBox();
		menuPane.setAlignment(Pos.BOTTOM_CENTER);	
		center.getChildren().addAll(itemPane, menuPane);
		
		for (int i = 0; i < 10; i++) 
		{
			itemPane.getChildren().add(new Text("Item: " + i));
		}
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
