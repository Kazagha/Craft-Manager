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
	private int idx = -1;
	
	public ItemHandler(SwitchPane switchPane)
	{
		this.switchPane = switchPane;
		history = switchPane.getSwapChildren().get(0);
		queue = switchPane.getSwapChildren().get(1);
	}
	
	@Override
	public void handle(InputEvent event)
	{
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
		}		
	}
	
	private void buttonSelected(MouseEvent event) 
	{
		// Craft Item at the selected index
		if (idx < 0) 
			return; 
		
		System.out.format("Craft at Index: %d%n", idx);
	}
	
	public void rightClickEvent(MouseEvent event)
	{
		System.out.format("Right Click Event%n");
	}
	
	private void leftClickItem(MouseEvent event)
	{
		Pane source = (Pane) event.getSource();
		Pane target = (Pane) ((Pane) event.getTarget()).getParent();
		ViewMenuFX menu = (ViewMenuFX) Locator.getView().getMenu();
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
