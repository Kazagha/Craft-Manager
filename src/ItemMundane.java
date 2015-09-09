import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ItemMundane extends Item {

	private int DC;	
	
	public ItemMundane(String name, int baseCost, int DC) {		
		super(name, baseCost * 10, baseCost / 3 );
		this.DC = DC;
	}

	public static Item create()
	{		
		ArrayList<Object> array = new ArrayList<Object>();
		
		for(String str : new String[] {"Name", "Base Cost", "DC" }) 
		{
			array.add(str);
			array.add(new JTextField(""));
		}
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				// Open JOptionPane to prompt the user for input
				result = Controller.editArray(array);
				// Attempt to create an instance with the specified input
				return new ItemMundane(
						(((JTextField) array.get(1)).getText()),
						Integer.valueOf(((JTextField) array.get(3)).getText()),
						Integer.valueOf(((JTextField) array.get(5)).getText())
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