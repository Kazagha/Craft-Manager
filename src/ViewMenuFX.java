import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ViewMenuFX extends VBox {

	private Text nameText;
	private Text gpText;
	private Text xpText;
	private Button craftButton;
	
	public ViewMenuFX()
	{
		init();
	}
	
	public void init() 
	{
		nameText = new Text();
		gpText = new Text();
		gpText.prefWidth(200);
		xpText = new Text();
		xpText.prefWidth(200);
		craftButton = new Button("Craft");
		
		this.setStyle("-fx-background-color: LIGHTBLUE");
		this.setAlignment(Pos.TOP_CENTER);		
		this.getChildren().addAll(
				nameText,
				new HBox(gpText, xpText),
				craftButton
				);		
	}
	
	public void setItem(Item item)
	{
		if (item == null)
			return;			
	
		nameText.setText(item.getName());
		gpText.setText(String.format("%d gp",item.getCraftPrice()));
		
		if (item instanceof ItemMagic) {
			ItemMagic itemM = (ItemMagic) item;
			xpText.setText(String.format("%d XP", itemM.getXP()));
		} else {
			xpText.setText(String.format("0 XP"));
		}		
	}
}
