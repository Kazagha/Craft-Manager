import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ItemHandler implements EventHandler<InputEvent> {

	private SwitchPane switchPane;
	private Pane history;
	private Pane queue;
	private Pane newPane;
	private ViewMenuFX menu;
	private int idx = -1;	
	
	public ItemHandler()
	{
		switchPane = (SwitchPane) Locator.getView().getSwitchPane();
		history = Locator.getView().getHistoryPane();
		queue = Locator.getView().getQueuePane();
		newPane = Locator.getView().getNewPane();
		menu = (ViewMenuFX) Locator.getView().getMenu();
	}
	
	@Override
	public void handle(InputEvent event)
	{	
		if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
		{
			// Handle 'switch pane' events first  
			if (event.getSource() instanceof Button) 
			{
				String id = (((Control) event.getSource()).getId());
				
				try {
					// Select the new pane
					//  Clear the menu and reset the index
					switch (id) 
					{
					case "SwitchToNew":
						switchPane.switchTo(newPane);
						menu.setItem(null);
						idx = -1;
						break;
					case "SwitchToQueue":
						switchPane.switchTo(queue);
						menu.setItem(null);
						idx = -1;
						break;
					case "SwitchToHistory":
						switchPane.switchTo(history);
						menu.setItem(null);
						idx = -1;
						break;					
					}
				} catch (NullPointerException e) {
					// The Button has no 'id'
				}
			}
			
			// Handle all other events depending on the currently selected pane
			if (switchPane.getSelected().equals(newPane)) {
				newPaneEvent(event);
			} else if (switchPane.getSelected().equals(queue)) {
				queuePaneEvent(event);
			} else if (switchPane.getSelected().equals(history)) {
				historyPaneEvent(event);
			}
		} else if (event.getEventType() == InputEvent.ANY) {
			// The 'pane change' event has occurred 
			// TODO: Change the menu depending on the currently selected pane
		}		
	}
	
	public void historyPaneEvent(InputEvent event)
	{
		Object source = event.getSource();		
		ModelInterface model = Locator.getModel();
		
		if (source instanceof SwitchPane)
		{	
			// Find the index of the selected Item
			SwitchPane sourceSP = (SwitchPane) source;
			Pane target = (Pane) ((Pane) event.getTarget()).getParent();			
			idx = sourceSP.getSelected().getChildren().indexOf(target);
			
			System.out.format("Index %d%n", idx);
			
			// Return if no valid item has been selected
			if (idx < 0) 
				return;
			
			// Select the item, show details on the menu
			setSelection(model.getComplete().get(idx));
			
		} else if (source instanceof Button) {
			if (idx < 0 || idx > Locator.getModel().getComplete().size() - 1)
				return;
		}
	}
	
	public void queuePaneEvent(InputEvent event)
	{
		Object source = event.getSource();				
		ModelInterface model = Locator.getModel();
		
		if (source instanceof SwitchPane)
		{	
			// Find the index of the selected Item
			SwitchPane sourceSP = (SwitchPane) source;
			Pane target = (Pane) ((Pane) event.getTarget()).getParent();
			idx = sourceSP.getSelected().getChildren().indexOf(target);
			
			System.out.format("Index %d%n", idx);
			
			// Return if no valid item has been selected
			if (idx < 0) 
				return;
			
			// Select the item, show details on the menu
			setSelection(model.getQueue().get(idx));
			
			if (event instanceof MouseEvent) {
				MouseEvent mouseEvent = (MouseEvent) event;
				if (mouseEvent.getButton() == MouseButton.SECONDARY) 
				{
					ContextMenu cm = new ContextMenu();
					MenuItem menu = new MenuItem("Edit");
					cm.getItems().add(menu);
					
					cm.show(Locator.getView().getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());					
				}
			}
						
		} else if (source instanceof Button) {
			if (idx < 0 || idx > Locator.getModel().getQueue().size() - 1)
				return;
			
			// Craft the selected item
			Locator.getController().craftItemAt(idx);
		}
	}
		
	public void newPaneEvent(InputEvent event)
	{
		Object source = event.getSource();				
		ModelInterface model = Locator.getModel();
		
		if (source instanceof SwitchPane)
		{	
			// Find the index of the selected Item
			SwitchPane sourceSP = (SwitchPane) source;
			Pane target = (Pane) ((Pane) event.getTarget()).getParent();
			idx = sourceSP.getSelected().getChildren().indexOf(target);
			
			System.out.format("Index %d%n", idx);
			
			// Return if no valid item has been selected
			if (idx < 0) 
				return;
			
			// Select the item, show details on the menu
			setSelection(Locator.getController().getItemList().get(idx));
			
		} else if (source instanceof Button) {
			if (idx < 0 || idx > Locator.getController().getItemList().size() - 1)
				return;
			
			// Add the selected item to the queue
			model.appendQueue(Locator.getController().getItemList().get(idx));
			model.notifyObservers();
		}
	}
	
	/**
	 * Populate the menu with the specified item
	 * @param item
	 */
	public void setSelection(Item item)
	{
		menu.setItem(item);
	}
	
	@Deprecated
	private void buttonSelected(MouseEvent event) 
	{
		// Craft Item at the selected index
		if (idx < 0) 
			return; 
		
		if (switchPane.getSelected().equals(newPane)) {
			System.out.format("New Pane%n");
			Item item = Locator.getController().getItemList().get(idx);
			Model m = (Model) Locator.getModel();
			m.appendQueue(item);			
			m.notifyObservers();
		} else if (menu.getCraftButton().equals(event.getSource())) {
			// 	The 'Craft' button is removed when the StackPane isn't showing the queue
			Locator.getController().craftItemAt(idx);
		}
	}
	
	@Deprecated
	public void rightClickEvent(MouseEvent event)
	{
		System.out.format("Right Click Event%n");
		
	}
	
	@Deprecated
	private void leftClickItem(MouseEvent event)
	{
		Pane source = (Pane) event.getSource();
		Pane target = (Pane) ((Pane) event.getTarget()).getParent();		
		ModelInterface model = Locator.getModel();
				
		if (source instanceof SwitchPane)
		{	
			// Find the index of the selected Item
			SwitchPane sourceSP = (SwitchPane) source;			
			idx = sourceSP.getSelected().getChildren().indexOf(target);
			
			// Return if no valid item has been selected
			if (idx < 0) 
				return;
			
			// Determine which pane is currently selected
			if (sourceSP.getSelected().equals(history))
			{
				// Event on the History screen
				System.out.format("History Event%n");		
				menu.setItem(model.getComplete().get(idx));
			} else if (switchPane.getSelected().equals(queue)) {
				// Event on the Queue screen
				System.out.format("Queue Event%n");
				menu.setItem(model.getQueue().get(idx));
			} else if (switchPane.getSelected().equals(newPane)) {
				System.out.format("New Pane Event%n");
				menu.setItem(Locator.getController().getItemList().get(idx));
			}
		}
	}
}
