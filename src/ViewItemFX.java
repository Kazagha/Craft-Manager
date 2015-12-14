import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewItemFX extends StackPane implements Observer
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
		progress.setPrefWidth(400);
		Rectangle icon = new Rectangle(40, 40);
		
		HBox h = new HBox();
		VBox v = new VBox();
		
		this.getChildren().addAll(h, new StackPane());
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
		
		progress.setProgress((double)value/total);
	}
	
	public void updateItem(Item item)
	{
		this.setName(item.getName());
		this.setProgressBar(item.getProgress(), item.getPrice());
	}
	
	@Override
	public void update(Observable item, Object arg1) 
	{
		this.updateItem((Item) item);
	}
}
