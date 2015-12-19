import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
	}
	
	@Override
	public void handle(InputEvent event)
	{
		menu = (ViewMenuFX) Locator.getView().getMenu();
		
		if (event instanceof MouseEvent)
		{
			MouseEvent mouseEvent = (MouseEvent) event; 
			if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
			{
				if (mouseEvent.getButton() == MouseButton.PRIMARY) {
					if (mouseEvent.getSource() instanceof Button)
					{
						buttonSelected(mouseEvent);
					} else {
						leftClickItem(mouseEvent);
					}
				} else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
					rightClickEvent(mouseEvent);
				}			
			}
		} else if (event.getEventType() == InputEvent.ANY){
			// Generic Input Event
			System.out.format("Generic Input%n");	
			
			if (switchPane.getSelected().equals(history))
			{
				menu.setMode(true);
			} else if (switchPane.getSelected().equals(queue)) {
				menu.setMode(false);
			}
		}		
	}
	
	private void buttonSelected(MouseEvent event) 
	{
		// Craft Item at the selected index
		if (idx < 0) 
			return; 
		
		if (menu.getCraftButton().equals(event.getSource())) 
		{
			// 	The 'Craft' button is removed when the StackPane isn't showing the queue
			Locator.getController().craftItemAt(idx);
		}
	}
	
	public void rightClickEvent(MouseEvent event)
	{
		System.out.format("Right Click Event%n");
	}
	
	private void leftClickItem(MouseEvent event)
	{
		Pane source = (Pane) event.getSource();
		Pane target = (Pane) ((Pane) event.getTarget()).getParent();		
		ModelInterface model = Locator.getModel();
				
		if (source instanceof SwitchPane)
		{	
			SwitchPane sourceSP = (SwitchPane) source;			
			idx = sourceSP.getSelected().getChildren().indexOf(target);
			
			if (idx < 0) 
				return;
			
			if (sourceSP.getSelected().equals(history))
			{
				// Event on the History screen
				System.out.format("History Event%n");		
				menu.setItem(model.getComplete().get(idx));
			} else if (switchPane.getSelected().equals(queue)) {
				// Event on the Queue screen
				System.out.format("Queue Event%n");
				menu.setItem(model.getQueue().get(idx));
			}					
		}
	}
}
