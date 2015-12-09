import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ItemHandler implements EventHandler<MouseEvent> {

	Pane queuePane;
	Pane historyPane;
	
	public ItemHandler(Pane switchPane)
	{
		this.queuePane = queuePane;
		this.historyPane = historyPane;				
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
		Object source = event.getSource();
		
	}
}
