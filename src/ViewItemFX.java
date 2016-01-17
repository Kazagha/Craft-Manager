import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewItemFX extends StackPane implements Observer
{	
	private Label name;
	private ProgressBar progress;
	private ImageView icon;
	
	private VBox v;
	
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
		icon = new ImageView();
			
		HBox h = new HBox();
		v = new VBox();
		
		this.getChildren().addAll(h, new StackPane());
		h.getChildren().addAll(icon, v);
		v.setPrefWidth(500);
		v.getChildren().addAll(name, progress);
	}	
	
	public void setName(String str)
	{
		this.name.setText(str);
	}
	
	public void setImage(Image image)
	{
		icon.setImage(image);
	}
	
	public void setProgressBar(int value, int total)
	{
		if (total == 0) {
			progress.setProgress(0);
			return;
		}
		
		progress.setProgress((double)value/total);
	}
	
	public void setProgressVisible(boolean isVisible)
	{
		if (isVisible) {
			v.getChildren().add(progress);
		} else {
			v.getChildren().remove(progress);
		}
	}
	
	public void updateItem(Item item)
	{
		this.setName(item.getName());
		this.setProgressBar(item.getProgress(), item.getPrice());
		
		if (item.getImageID() != null && !item.getImageID().equals(icon.getId())) 
		{
			icon.setId(item.getImageID());
		} else if (item.getImageID() == null) {
			icon.setId("DefaultImage");
		}		
	}
	
	@Override
	public void update(Observable item, Object arg1) 
	{
		this.updateItem((Item) item);
	}
}
