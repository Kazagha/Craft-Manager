import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ViewMenuFX extends VBox {

	private Text nameText;
	private Text gpText;
	private Text xpText;
	
	private HBox buttonHBox;
	
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
		buttonHBox = new HBox();
		
		this.setId("ItemMenu");
		this.setAlignment(Pos.TOP_CENTER);		
		this.getChildren().addAll(
				nameText,
				new HBox(gpText, xpText),
				buttonHBox
				);		
	}
	
	public void addButton(Button button)
	{
		buttonHBox.getChildren().add(button);
	}
	
	public void addAllButtons(Button... buttons)
	{
		buttonHBox.getChildren().addAll(buttons);
	}
	
	public ObservableList<Node> getButtons()
	{
		return buttonHBox.getChildren();
	}
		
	public void setItem(Item item)
	{
		if (item == null) 
		{
			nameText.setText("");
			gpText.setText("");
			xpText.setText("");
			return;
		}
			
		nameText.setText(String.format("%s (%d gp)", item.getName(), item.getPrice()));
		gpText.setText(String.format("%d gp",item.getCraftPrice()));
		
		if (item instanceof ItemMagic) {
			ItemMagic itemM = (ItemMagic) item;
			xpText.setText(String.format("%d XP", itemM.getXP()));
		} else {
			xpText.setText(String.format("0 XP"));
		}		
	}
}
