import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.sun.xml.internal.ws.encoding.soap.SerializerConstants;

import javafx.application.Platform;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
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
	private VBox newPane;
	private ViewMenuFX itemMenu;
	private ItemHandler handler;
	
	private Button newButton;
	private Button queueButton;
	Button historyButton;
	
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
		root.setTop(initMenu());
				
		VBox center = new VBox();
		center.setPadding(new Insets(0, 10, 0, 10));
		root.setCenter(center);
		
		HBox bottom = new HBox();
		root.setBottom(bottom);
		
		newButton = new Button();
		newButton.setId("SwitchToNew");		
		queueButton = new Button();
		queueButton.setId("SwitchToQueue");
		historyButton = new Button();		
		historyButton.setId("SwitchToHistory");
				
		// Set the top pane
		HBox buttonPane = new HBox();
		buttonPane.getChildren().addAll(
					newButton,
					queueButton,
					historyButton
				);
		center.getChildren().add(buttonPane);
		
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
		
		// Set the New Item Pane
		newPane = new VBox();
		historyButton.setAlignment(Pos.TOP_CENTER);
		newPane.setId("CenterVBox");
		
		// Set the switch pane (queue/history)
		switchPane = new SwitchPane();
		switchPane.getSwapChildren().addAll(newPane, historyPane, queuePane);
		switchPane.switchTo(queuePane);
		switchPane.setPrefHeight(Integer.MAX_VALUE);
		center.getChildren().add(switchPane);
		
		itemMenu = new ViewMenuFX();
		center.getChildren().add(itemMenu);		
	}
	
	public Pane initMenu() 
	{
		Pane root = new Pane();		
		MenuBar menuBar = new MenuBar();
		root.getChildren().add(menuBar);
		
		MenuItem menu;
		
		Menu subMenu = new Menu("File");
		menuBar.getMenus().add(subMenu);
		
		menu = new MenuItem("Open");
		menu.setOnAction(ActionEvent -> Locator.getController().load());
		subMenu.getItems().add(menu);
		
		menu = new MenuItem("Save");
		menu.setOnAction(ActionEvent -> Locator.getController().save());
		subMenu.getItems().add(menu);
		
		menu = new MenuItem("Save As...");
		menu.setOnAction(ActionEvent -> Locator.getController().saveAs());
		subMenu.getItems().add(menu);
		
		menu = new MenuItem("Exit");
		menu.setOnAction(ActionEvent -> Platform.exit());
		subMenu.getItems().add(menu);
		
		subMenu = new Menu("Edit");
		menuBar.getMenus().add(subMenu);
		
		Menu craftReserve = new Menu("Craft Reserve");
		subMenu.getItems().add(craftReserve);
		
		menu = new MenuItem("Add Gold");
		menu.setOnAction(ActionEvent -> Locator.getController().addGold());
		craftReserve.getItems().add(menu);
		
		menu = new MenuItem("Add XP");
		menu.setOnAction(ActionEvent -> Locator.getController().addXP());
		craftReserve.getItems().add(menu);
		
		menu = new MenuItem("Clear Completed");
		menu.setOnAction(ActionEvent -> Locator.getController().clearComplete());
		subMenu.getItems().add(menu);
		
		subMenu = new Menu("Help");
		menuBar.getMenus().add(subMenu);
		
		menu = new MenuItem("Welcome");
		subMenu.getItems().add(menu);
		
		menu = new MenuItem("Updates");
		subMenu.getItems().add(menu);
		
		menu = new MenuItem("About");
		subMenu.getItems().add(menu);
				
		return root;
	}
	
	public void hookUpEventHandler(EventHandler handler) 
	{		
		newButton.addEventHandler(MouseEvent.ANY, handler);
		queueButton.addEventFilter(MouseEvent.ANY, handler);
		historyButton.addEventFilter(MouseEvent.ANY, handler);
		
		switchPane.addEventHandler(InputEvent.ANY, handler);
		itemMenu.hookUpEventHandler(handler);
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
	
	public void setNewItems(ArrayList<Item> array) 
	{
		appendItemsTo(newPane, array);
		
		for (Node view : newPane.getChildren())
		{
			((ViewItemFX) view).setProgressVisible(false);
		}
	}
	
	/**
	 * Add the <code>children</code> array to the specified Pane 
	 * @param root
	 * @param children
	 */
	private void appendItemsTo(Pane root, ArrayList<Item> children)
	{
		root.getChildren().removeAll(root.getChildren());
		
		for (Item item : children) 
		{		
			ViewItemFX pane = new ViewItemFX();
			pane.setId("PaneDefault");
			root.getChildren().add(pane);
			
			item.addObserver(pane);
			item.setChanged();
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
	
	@Override
	public Pane getMenu()
	{
		return itemMenu;
	}
	
	public Pane getHistoryPane()
	{
		return historyPane;
	}
	
	public Pane getQueuePane()
	{
		return queuePane;
	}
	
	public Pane getNewPane()
	{
		return newPane;
	}
	
	public SwitchPane getSwitchPane()
	{
		return switchPane;
	}
	
	public GridPane toDialog(Node... nodes)
	{
		GridPane gp = new GridPane();		
		gp.setVgap(10);
		gp.setHgap(10);
		gp.getStylesheets().addAll(scene.getStylesheets());		
		
		int x = 0;
		int y = 0;
		
		for (Node n : nodes)
		{
			if (n instanceof GridPane)
				n.setId("DialogGroup");
				
			gp.add(n, x, y);
						
			if (++x == 2)
			{
				x = 0;
				y++;
			}			
		}
		
		return gp;
	}
	
	public int checkDialog()
	{
		int check = -1;
		Dialog d = new Dialog();
		d.setTitle("Craft Check");
		TextField checkField = new TextField();
		
		d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		d.getDialogPane().setContent(this.toDialog(new Label("Result"), checkField));
		
		// Add event filter for valid inputs
		Button ok = (Button) d.getDialogPane().lookupButton(ButtonType.OK);
		ok.addEventFilter(ActionEvent.ACTION, event -> {
			// Check the input is valid
			if (checkField.getText().contains("[A-Za-z]")
					||Integer.valueOf(checkField.getText()) < 0)
				event.consume();			
			}
		);
		
		d.showAndWait()
			.filter(response -> response == ButtonType.OK);
		
		if(d.getResult() == ButtonType.OK)
			return Integer.valueOf(checkField.getText());
		
		else return -1;
	}

	@Override
	public void update(Observable obs, Object obj) 
	{
		if (! (obs instanceof Model))
			return;
					
		ModelInterface m = (ModelInterface) obs;
		this.appendItemsTo(queuePane, m.getQueue());
		this.appendItemsTo(historyPane, m.getComplete());
		this.setGP(m.getGold());
		this.setXP(m.getXP());		
	}
}
