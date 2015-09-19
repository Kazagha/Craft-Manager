import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ItemWand extends Item{

	private int casterLevel;
	private int spellLevel;
	
	public ItemWand(String name, int casterLevel, int spellLevel) 
	{		
		super(name, casterLevel * spellLevel * 750, casterLevel * spellLevel * 750 / 2);
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
	}

	@Override
	void update() {
		setProgress(getProgress() + 1000); 
	}

	public static Item create() 
	{
		ItemWand newItem = new ItemWand("", 0, 0);
		if(newItem.edit() == JOptionPane.OK_OPTION)
			return newItem;
		
		return null;
	}
	
	@Override
	public int edit() 
	{
		ArrayList<Object> array = new ArrayList<Object>();

		JTextField name = new JTextField(this.getName());
		JTextField casterLevel = new JTextField();
		JTextField spellLevel = new JTextField();
		
		array.addAll(Arrays.asList(new Object[] { "Name", name, "Caster Level", casterLevel, "Spell Level", spellLevel}));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_CANCEL_OPTION)
		{
			try
			{
				result = Controller.editArray(array);
				
				// Set changes on this Item
				this.setName(name.getText());
				this.setCasterLevel(Integer.valueOf(casterLevel.getText()));
				this.setSpellLevel(Integer.valueOf(casterLevel.getText()));				
				this.setBaseCost(getCasterLevel() * getSpellLevel() * 750);
				this.setMatCost(getBaseCost() / 2); 
				return result;
			} catch (Exception e) {
				
			}
			
		}
		return result;
	}
	
	public void setCasterLevel(int num)
	{
		this.casterLevel = num;
	}
	
	public int getCasterLevel()
	{
		return this.casterLevel;
	}
	
	public void setSpellLevel(int num)
	{
		this.spellLevel = num;
	}
	
	public int getSpellLevel()
	{
		return this.spellLevel;
	}
	
	public int getXP()
	{
		return casterLevel * spellLevel * 750 / 25;
	}
}
