import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

@XmlRootElement(name = "MagicItem")
public class ItemMagicBasic extends ItemMagic {
	
	private static TextField nameField = new TextField();
	
	public ItemMagicBasic() {}
	
	public ItemMagicBasic(String name)
	{
		super.setName(name);
		this.setItemType(Item.TYPE.MAGIC);
	}
	
	public static ItemMagicBasic create()
	{
		ItemMagicBasic newItem = new ItemMagicBasic("");
		//if(newItem.edit() == JOptionPane.OK_OPTION)
		//	return newItem;	
		
		return null;
	}
	
	@Override
	public Pane toEditPane()
	{
		Platform.runLater(() -> nameField.requestFocus());
		nameField.setText(this.getName());
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Label("Name"));
		nodes.add(nameField);				
		
		for (Effect effect : this.getEffect())
		{
			ImageView view = new ImageView();
			view.setId(effect.classToString().replace(" ", ""));
			
			nodes.add(view);
			nodes.add(effect.toEditPane());
		}
				
		return Locator.getView().toDialog(
				nodes.toArray((Node[]) new Node[nodes.size()])
				);
	}
	
	@Override 
	public boolean validateAndStore()
	{
		if (nameField.getText().equals("")) 
		{
			return false;
		}
		
		for (Effect effect : this.getEffect())
		{
			if (! effect.validateAndStore()) 
			{
				return false;
			}
		}
		
		this.setName(nameField.getText());
		
		return true;
	}

	@Override
	public int getPrice() 
	{
		int price = 0;
		
		for(Effect effect : this.getEffect())
		{			
			// Calculate the base price
			price += effect.getPrice();						
		}
		
		return price;
	}
}
