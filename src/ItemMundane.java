import java.util.ArrayList;

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
		
		for(String s : new String[] {"Name", "Base Cost", "DC" }) 
		{
			array.add(s);
			array.add(new JTextField(""));
		}	
		
		Controller.getInstance().editArray(array);
		
		return new ItemMundane("test", 1, 1);
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