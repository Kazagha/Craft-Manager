import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MagicItem")
public class ItemMagicBasic extends ItemMagic {
	
	public ItemMagicBasic() {}
	
	public ItemMagicBasic(String name)
	{
		super.setName(name);
		this.setItemType(Item.TYPE.MAGIC);
	}
	
	public static ItemMagicBasic create()
	{
		ItemMagicBasic newItem = new ItemMagicBasic("");
		if(newItem.edit() == JOptionPane.OK_OPTION)
			return newItem;	
		
		return null;
	}
	
	@Override
	public int edit() 
	{
		Object[] array;
		
		JTextField name = new JTextField(this.getName());
		
		array = new Object[] { "Name", name };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try {
				result = Controller.getInstance().editArray(array);
				
				// Set changes
				this.setName(name.getText());
				return result;
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}
		}
		
		return result;
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
