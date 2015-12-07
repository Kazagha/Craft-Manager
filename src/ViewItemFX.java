import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewItemFX extends StackPane
{	
	private Label name;
	private ProgressBar progress;
	
	public ViewItemFX()
	{
		init();
	}
		
	private void init()
	{
		this.setAlignment(Pos.CENTER);
		name = new Label();
		progress = new ProgressBar();
		Rectangle icon = new Rectangle(40, 40);
		icon.setStyle("-fx-fill: GREY");
		
		HBox h = new HBox();
		VBox v = new VBox();
		
		this.getChildren().add(h);
		h.getChildren().addAll(icon, v);
		v.getChildren().addAll(name, progress);
	}
	
	public void setName(String str)
	{
		this.name.setText(str);
	}
	
	public void setProgressBar(int value, int total)
	{
		if (total == 0) {
			progress.setProgress(0);
			return;
		}
		
		progress.setProgress(value/total);
	}
}
