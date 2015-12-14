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
				event.getButton() == MouseButton.SECONDARY)
		{
			rightClick(event);
		}
	}
	
	private void rightClick(MouseEvent event)
	{
		Pane source = (Pane) event.getSource();
		Pane target = (Pane) event.getTarget();
				
		if (source instanceof SwitchPane)
		{	
			SwitchPane sourceSP = (SwitchPane) source;
			
			int idx = sourceSP.getSelected().getChildren().indexOf(target.getParent());
			
			if (sourceSP.getSelected().equals(history))
			{
				// Event on the History screen
				System.out.format("History Event%n");
			} else if (switchPane.getSelected().equals(queue)) {
				// Event on the Queue screen
				System.out.format("Queue Event%n");
			}				
		}
	}
}
