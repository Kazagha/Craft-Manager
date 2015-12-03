import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.sun.xml.internal.ws.encoding.soap.SerializerConstants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
		center.setPadding(new Insets(0, 10, 0, 10));
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
				
		// Set the Queue Pane
		VBox queuePane = new VBox();
		queuePane.setAlignment(Pos.TOP_CENTER);
		queuePane.setId("CenterVBox");
		
		// Set the Complete Pane
		VBox completePane = new VBox();
		completePane.setAlignment(Pos.TOP_CENTER);
		completePane.setId("CenterVBox");		
		completePane.getChildren().add(new Text("This is a test"));
		
		// Set the Queue/Complete pane together in 
		StackSelect centerSS = new StackSelect();
		centerSS.getSwapChildren().addAll(completePane, queuePane);
		centerSS.setSelected(1);
		center.getChildren().add(centerSS);
					
		// Center Menu
		VBox centerMenuPane = new VBox();	
		centerMenuPane.setAlignment(Pos.BOTTOM_CENTER);	
		centerMenuPane.setId("MenuPane");
		Rectangle menuRect = new Rectangle(SCENE_WIDTH - 30, 90);
		menuRect.widthProperty().bind(center.widthProperty().add(-30));
						
		// Put Center Menu together into StackPane
		StackPane menuStack = new StackPane();
		menuStack.setAlignment(Pos.BOTTOM_CENTER);
		menuStack.getChildren().addAll(menuRect, centerMenuPane);
		center.getChildren().add(menuStack);
		
		// Create Clip on the Center Stack
		Rectangle menuClip = new Rectangle(SCENE_WIDTH - 30, 90);
		menuClip.translateYProperty().bind(menuStack.heightProperty().add(- 90));
		menuStack.setClip(menuClip);
		
		for (int i = 0; i < 50; i++) 
		{
			queuePane.getChildren().add(new Text("Item: " + i));
		}
				
		centerMenuPane.getChildren().addAll(
				new Text("Item Name"),
				new HBox(new Text("150"), new Text("100")),
				new Button("Craft")
				);
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
