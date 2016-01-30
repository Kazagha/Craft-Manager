import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ItemHandler implements EventHandler<InputEvent> {

	private SwitchPane switchPane;
	private Pane history;
	private Pane queue;
	private Pane newPane;
	private ViewMenuFX historyMenu;
	private ViewMenuFX queueMenu;
	private ViewMenuFX newMenu;
	private ViewMenuFX switchMenu;
	private int idx = -1;	
	
	private Node hover = new ViewItemFX();
	private Node select = new ViewItemFX();
	
	public ItemHandler()
	{
		switchPane = (SwitchPane) Locator.getView().getSwitchPane();
		history = Locator.getView().getHistoryPane();
		queue = Locator.getView().getQueuePane();
		newPane = Locator.getView().getNewPane();
		
		setupMenus();
	}
	
	@Override
	public void handle(InputEvent event)
	{	
		Object target = event.getTarget();
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
						switchToMenu(newMenu);
						reset();
						break;
					case "SwitchToQueue":
						switchPane.switchTo(queue);
						switchToMenu(queueMenu);
						reset();
						break;
					case "SwitchToHistory":
						switchPane.switchTo(history);
						switchToMenu(historyMenu);
						reset();
						break;					
					}
				} catch (NullPointerException e) {
					// The Button has no 'id'
				}
			}
			
			// Check if a new selection has been made; hover not NULL
			// Check if the selection has changed; hover not selected 
			if (hover != null && select != hover)
			{
				select.setId("PaneDefault");
				
				select = hover;
				select.setId("PaneSelectHover");
			}
			
			// Handle all other events depending on the currently selected pane
			if (switchPane.getSelected().equals(newPane)) {
				newPaneEvent(event);
			} else if (switchPane.getSelected().equals(queue)) {
				queuePaneEvent(event);
			} else if (switchPane.getSelected().equals(history)) {
				historyPaneEvent(event);
			}
		} else if (event.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
			if (event.getTarget() instanceof ViewItemFX)
			{
				// Don't 'hover' if the target is selected
				if (target == select) 
				{
					select.setId("PaneSelectHover");
					hover = (Node) event.getTarget();
					return;
				}
				
				hover = (Node) event.getTarget();
				hover.setId("PaneHover");
			}
		} else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
			// Don't remove 'hover' if target is selected 
			if (hover != null) 
			{
				if (hover == select)
				{
					select.setId("PaneSelect");
					hover = null;
				} else {
					hover.setId("PaneDefault");
					hover = null;
				}
			}
		}
	}
	
	private void reset()
	{
		switchMenu.setItem(null);
		idx = -1;
		
		// Clear the selected item
		select.setId("PaneDefault");
		select = new ViewItemFX();
	}
	
	private void switchToMenu(ViewMenuFX menu)
	{
		switchMenu = menu;
		((ViewFX)Locator.getView()).setMenu(menu);
	}
	
	private void setupMenus()
	{
		newMenu = new ViewMenuFX();
		Button button = new Button("Add Item");
		button.addEventHandler(InputEvent.ANY, this);		
		newMenu.addAllButtons(button);
		
		queueMenu = new ViewMenuFX();
		button = new Button("Craft");
		button.addEventHandler(InputEvent.ANY, this);
		queueMenu.addAllButtons(button);
		
		historyMenu = new ViewMenuFX();
		button = new Button("Examine");
		button.addEventHandler(InputEvent.ANY, this);
		historyMenu.addAllButtons(button);
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
					Item item = Locator.getModel().getQueue().get(idx);
					ContextMenu cm = new ContextMenu();
					MenuItem menu = new MenuItem(item.getName());
					cm.getItems().addAll(menu, new SeparatorMenuItem());
					
					menu = new MenuItem("Edit");
					
					menu.setOnAction(ActionEvent -> {
							this.edit(item);
						});
					
					cm.getItems().addAll(menu);	
					
					if (item instanceof ItemMagic) 
					{						
						ItemMagic itemM = (ItemMagic) item;
						
						// Create menu to add new enchantments
						Menu subMenu = new Menu("Add Enchantment");
						cm.getItems().add(subMenu);

						menu = new MenuItem("Spell Effect");
						menu.setOnAction(ActionEvent -> {
							EffectSpell effect = new EffectSpell();
							if(this.edit(effect))
								Locator.getController().newEffect(itemM, effect);
						});
						subMenu.getItems().add(menu);
						
						for (EffectBonus.Type type : EffectBonus.Type.values())
						{
							menu = new MenuItem(type.getDesc());
							menu.setOnAction(ActionEvent -> {
								Effect bonus = new EffectBonus(1, type);
								if (this.edit(bonus))
									Locator.getController().newEffect(itemM, bonus);
							});	
							subMenu.getItems().add(menu);
						}	
					}
					
					cm.show(Locator.getView().getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());					
				}
			}
						
		} else if (source instanceof Button) {
			if (idx < 0 || idx > Locator.getModel().getQueue().size() - 1)
				return;
			
			//if (((Button) source).getId().equals("Craft")) {
			if (((Button) source).getText().equals("Craft")) {
				// Craft the selected item
				Locator.getController().craftItemAt(idx);
			}
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
			
			if (((Button) source).getText().equals("Add Item")) {
				// Add the selected item to the queue
				model.appendQueue(Locator.getController().getItemList().get(idx));
				model.notifyObservers();
			}
		}
	}
	
	/**
	 * Populate the menu with the specified item
	 * @param item
	 */
	public void setSelection(Item item)
	{
		switchMenu.setItem(item);
	}
	
	private void edit(Item item)
	{		
		Dialog d = new Dialog();
		d.setTitle("Edit Item");
		d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);		
		d.getDialogPane().setContent(item.toEditPane());
		
		// Add event filter for valid inputs
		Button ok = (Button) d.getDialogPane().lookupButton(ButtonType.OK);
		ok.addEventFilter(ActionEvent.ACTION, event -> {
			if (! item.validateAndStore())	// Check if the input is valid and save it
				event.consume();
			}
		);
		
		Button apply = new Button("New");
		apply.addEventFilter(ActionEvent.ACTION, event -> {
			//((ItemMagic) item).getEffect().remove(0);
			((ItemMagic) item).getEffect().add(new EffectBonus());
			d.getDialogPane().setContent(item.toEditPane());
			d.setResizable(true);
			event.consume();
		});			
		GridPane grid = ((GridPane) d.getDialogPane().getContent());
		grid.add(apply, 0, 5);
		
		if (item instanceof ItemMagic)
		{
			//d.getDialogPane().getButtonTypes().add();
		}
		
		d.showAndWait()
			.filter(response -> response == ButtonType.OK)
			.ifPresent(response -> item.notifyObservers());
	}
	
	private boolean edit(Effect effect)
	{
		Dialog d = new Dialog();
		d.setTitle("Edit Item");
		d.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.OK);		
		d.getDialogPane().setContent(effect.toEditPane());
		
		// Add event filter for valid inputs
		Button ok = (Button) d.getDialogPane().lookupButton(ButtonType.OK);
		ok.addEventFilter(ActionEvent.ACTION, event -> {
			// Check if the input is valid and save it
			if (! effect.validateAndStore())	
				event.consume();
			}
		);
		
		d.showAndWait()
			.filter(response -> response == ButtonType.OK)
			.ifPresent(response -> System.out.format("Effect: %s%n", effect.getName()));
		
		return d.getResult() == ButtonType.OK;
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
		/*
		} else if (menu.getCraftButton().equals(event.getSource())) {		
			// TODO: If required, check that the 'craft' button has been pressed
			// 	The 'Craft' button is removed when the StackPane isn't showing the queue
			Locator.getController().craftItemAt(idx);
		*/
		}
	}
}
