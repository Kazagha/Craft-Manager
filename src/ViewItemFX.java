import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewItemFX extends Pane
{	
	private Label name;
	private ProgressBar progress;
	
	public ViewItemFX()
	{
		init();
	}
		
	private void init()
	{
		name = new Label();
		progress = new ProgressBar();
		Rectangle icon = new Rectangle(40, 40);
		icon.setStyle("-fx-fill: GREY");
		
		HBox h = new HBox();
		VBox v = new VBox();
		
		h.getChildren().addAll(icon, v);
		v.getChildren().addAll(name, progress);
	}
}
