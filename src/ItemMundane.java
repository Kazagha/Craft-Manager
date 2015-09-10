import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ItemMundane extends Item {

	private int DC;	
	
	public ItemMundane(String name, int baseCost, int DC) {		
		super(name, baseCost * 10, baseCost * 10 / 3 );
		this.DC = DC;
	}

	public static Item create()
	{		
		ArrayList<Object> array = new ArrayList<Object>();
		
		JTextField name = new JTextField("");
		JTextField baseCost = new JTextField("");
		JTextField DC = new JTextField("");

		array.addAll(Arrays.asList(new Object[] {"Name", name, "Base Cost", baseCost, "DC", DC }));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				// Open JOptionPane to prompt the user for input
				result = Controller.editArray(array);
				// Attempt to create an instance with the specified input
				return new ItemMundane(
						name.getText(),
						Integer.valueOf(baseCost.getText()),
						Integer.valueOf(DC.getText())
						);
			} catch (Exception e) {
				// TODO: Show 'Invalid input' message
			}
		}
		
		return null;
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
	void update()
	{		
		String text = String.format("Roll craft check (DC=%s)", getDC());
		int check = Controller.check(text);	
			
		// Was the check successful
		if(check >= getDC()) 
		{
			// Successful check
			setProgress(getProgress() + (getDC() * check));
		} else if (check < getDC() - 4)
		{
			// Check Failed by 5 or more: Half raw materials have been destroyed
			int cost = getMatCost() / 2;
		}	
				
		notifyObservers();
	}
}