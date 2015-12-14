import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ItemHandler implements EventHandler<MouseEvent> {

	private SwitchPane switchPane;
	private Pane history;
	private Pane queue;
	
	public ItemHandler(SwitchPane switchPane)
	{
		this.switchPane = switchPane;
		history = switchPane.getSwapChildren().get(0);
		queue = switchPane.getSwapChildren().get(1);
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_RELEASED &&
				event.getButton() == MouseButton.PRIMARY)
		{
			leftClick(event);
		}
	}
	
	private void leftClick(MouseEvent event)
	{
		Pane source = (Pane) event.getSource();
		Pane target = (Pane) ((Pane) event.getTarget()).getParent();
		ViewMenuFX menu = (ViewMenuFX) Locator.getView().getMenu();
		ModelInterface model = Locator.getModel();
				
		if (source instanceof SwitchPane)
		{	
			SwitchPane sourceSP = (SwitchPane) source;			
			int idx = sourceSP.getSelected().getChildren().indexOf(target);
			
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
