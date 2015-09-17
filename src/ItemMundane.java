import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemMundane extends Item {

	private int DC;	
	
	public ItemMundane(String name, int baseCost, int DC) {		
		super(name, baseCost * 10, baseCost * 10 / 3 );
		super.setItemType(Item.TYPE.MUNDANE);
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
		JTextField baseCost = new JTextField(String.valueOf(this.getBaseCost()));
		JTextField DC = new JTextField(String.valueOf(this.getDC()));
		
		// Prevent the user from changing the item after crafting has started
		if(this.getProgress() > 0) 
		{
			baseCost.setEditable(false);
			DC.setEditable(false);
		}

		array.addAll(Arrays.asList(new Object[] {"Name", name, "Base Item Price", baseCost, "DC", DC }));
				
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				// Open JOptionPane to prompt the user for input
				result = Controller.editArray(array);
				
				// Set the changes on this
				this.setName(name.getText());
				this.setBaseCost(Integer.valueOf(baseCost.getText()));
				this.setMatCost(this.getBaseCost() / 3);
				this.setDC(Integer.valueOf(DC.getText()));
				return result;
			} catch (Exception e) {
				// TODO: Show 'Invalid input' message
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
	
	@Override
	void update() {}
}