import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "price", "DC" })
public class ItemMundane extends Item {

	private int price;
	private int DC;	
	
	public ItemMundane(String name, int price, int DC) {	
		super(name);		
		super.setItemType(Item.TYPE.MUNDANE);
		this.price = price;
		this.DC = DC;		
	}
	
	public ItemMundane() {}
	
	public static Item create()
	{
		ItemMundane newItem = new ItemMundane("", 0, 0);
		if(newItem.edit() == JOptionPane.OK_OPTION)
		{
			return newItem;
		}
		
		return null;
	}
	
	public int edit()
	{
		ArrayList<Object> array = new ArrayList<Object>();
		
		JTextField name = new JTextField(this.getName());
		JTextField price = new JTextField(String.valueOf(this.getPrice()));
		JTextField DC = new JTextField(String.valueOf(this.getDC()));
		
		// Prevent the user from changing the item after crafting has started
		if(this.getProgress() > 0) 
		{
			price.setEditable(false);
			DC.setEditable(false);
		}

		array.addAll(Arrays.asList(new Object[] {"Name", name, "Full Price", price, "DC", DC }));
				
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				// Open JOptionPane to prompt the user for input
				result = Controller.getInstance().editArray(array);
				
				// Set the changes on this
				this.setName(name.getText());
				this.setPrice(Integer.valueOf(price.getText()));
				this.setDC(Integer.valueOf(DC.getText()));
				return result;
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}
		}
		
		return result;
	}
	
	public int getDC() 
	{
		return this.DC;
	}
	
	public void setDC(int DC)
	{
		this.DC = DC;
	}
	
	public void setPrice(int price) 
	{
		this.price = price;
	}	
	
	@XmlElement
	@Override
	public int getPrice()
	{
		return price;
	}

	@Override
	void update() {}

	@Override
	public int getCraftPrice() {
		return price / 3;
	}
}