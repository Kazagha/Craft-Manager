import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.sun.xml.internal.ws.encoding.soap.SerializerConstants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ViewFX implements Observer, ViewInterface {

	private Scene scene;
	
	final int SCENE_WIDTH = 450;
	final int SCENE_HEIGHT = 700;
	
	private SwitchPane switchPane;
	private VBox queuePane;
	private VBox historyPane;
	
	private Text gpText;
	private Text xpText;
	
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
		
		VBox center = new VBox();
		center.setPadding(new Insets(0, 10, 0, 10));
		root.setCenter(center);
		
		HBox bottom = new HBox();
		root.setBottom(bottom);
		
		Button newButton = new Button("New Item");
		Button queueButton = new Button("Queue");
		Button historyButton = new Button("History");
		
		newButton.setOnAction(event -> Locator.getController().newItemMundane());
		queueButton.setOnAction(event -> switchPane.switchTo(queuePane));
		historyButton.setOnAction(event -> switchPane.switchTo(historyPane));		
		
		// Set the top pane
		top.getChildren().addAll(
					newButton,
					queueButton,
					historyButton
				);
		
		// Set the bottom pane
		HBox bottomL = new HBox();
		bottomL.setPrefSize((root.getWidth() / 2) - 10, 25);
		bottomL.setId("ResourceHBox");
		HBox bottomR = new HBox();
		bottomR.setPrefSize((root.getWidth() / 2) - 10, 25);
		bottomR.setId("ResourceHBox");	
		bottom.getChildren().addAll(bottomL, bottomR);	
		
		// Set resource values
		gpText = new Text();
		this.setGP(0);
		xpText = new Text();
		this.setXP(0);
		bottomL.getChildren().add(gpText);
		bottomR.getChildren().add(xpText);
				
		// Set the Queue Pane
		queuePane = new VBox();
		queuePane.setAlignment(Pos.TOP_CENTER);
		queuePane.setId("CenterVBox");
		
		// Set the History Pane
		historyPane = new VBox();
		historyPane.setAlignment(Pos.TOP_CENTER);
		historyPane.setId("CenterVBox");
		
		// Set the switch pane (queue/history)
		switchPane = new SwitchPane();
		switchPane.getSwapChildren().addAll(historyPane, queuePane);
		switchPane.switchTo(queuePane);
		switchPane.setPrefHeight(Integer.MAX_VALUE);
		switchPane.addEventHandler(MouseEvent.ANY, new ItemHandler(switchPane));
		center.getChildren().add(switchPane);
		
		ViewMenuFX itemMenu = new ViewMenuFX();
		center.getChildren().add(itemMenu);
			
		// Set the Center Menu
		/*
		VBox centerMenuPane = new VBox();
		centerMenuPane.setStyle("-fx-background-color: LIGHTBLUE");
		centerMenuPane.setAlignment(Pos.TOP_CENTER);
		centerMenuPane.getChildren().addAll(
				new Text("Item Name"),
				new HBox(new Text("150"), new Text("100")),
				new Button("Craft")
				);				
		center.getChildren().add(centerMenuPane);
		*/
		
	}
	
	/**
	 * Remove all <code>Item</code>s from the Queue and History panes
	 */
	private void removeAllItems() 
	{
		queuePane.getChildren().removeAll(queuePane.getChildren());
		historyPane.getChildren().removeAll(historyPane.getChildren());
	}
	
	/* (non-Javadoc)
	 * @see ViewInterface#getScene()
	 */
	@Override
	public Scene getScene()
	{
		return scene;
	}
	
	/**
	 * Add the <code>children</code> array to the specified Pane 
	 * @param root
	 * @param children
	 */
	private void appendItemsTo(Pane root, ArrayList<Item> children)
	{
		for (Item item : children) 
		{		
			ViewItemFX pane = new ViewItemFX();
			root.getChildren().add(pane);
			
			item.addObserver(pane);
			item.notifyObservers();
		}
	}
	
	/* (non-Javadoc)
	 * @see ViewInterface#setXP(int)
	 */
	@Override
	public void setXP(int xp) 
	{
		xpText.setText(String.format("%d XP", xp));
	}
	
	/* (non-Javadoc)
	 * @see ViewInterface#setGP(int)
	 */
	@Override
	public void setGP(int gp)
	{
		gpText.setText(String.format("%d gp", gp));
	}
	
	public void hookUpEvents(EventHandler<Event> handler) {}


	@Override
	public void update(Observable obs, Object obj) 
	{
		if (! (obs instanceof Model))
			return;
		
		Model m = (Model) obs;
		this.appendItemsTo(queuePane, m.getQueue());
		this.appendItemsTo(historyPane, m.getComplete());		
		this.setGP(m.getGold());
		this.setXP(m.getXP());
	}	
}
