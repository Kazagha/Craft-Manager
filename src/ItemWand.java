import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ItemWand extends Item{

	private int casterLevel;
	private int spellLevel;
	private boolean eternal;
	private Effect effect;
	
	/*
	public ItemWand(String name, int casterLevel, int spellLevel) 
	{		
		super(name, casterLevel * spellLevel * 750, casterLevel * spellLevel * 750 / 2);
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
	}
	*/
	
	public ItemWand() {}
	
	public ItemWand(String name, int baseCost, int matCost) 
	{
		super(name, baseCost, matCost);
	}

	@Override
	void update() {}

	public static Item create() 
	{
		ItemWand newItem = new ItemWand("Wand of", 0, 0);
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
		JTextField eternal = new JTextField(getEternal().toString());
		
		array.addAll(Arrays.asList(new Object[] { "Name", name, "Caster Level", casterLevel, "Spell Level", spellLevel, "Eternal", eternal}));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_CANCEL_OPTION)
		{
			try
			{
				result = Controller.editArray(array);
				
				// Set changes on this Item
				this.setName(name.getText());
				this.setEternal(Boolean.valueOf(eternal.getText()));
				//this.setCasterLevel(Integer.valueOf(casterLevel.getText()));
				//this.setSpellLevel(Integer.valueOf(casterLevel.getText()));				
				//this.setBaseCost(getCasterLevel() * getSpellLevel() * 750);
				//this.setMatCost(getBaseCost() / 2); 
				return result;
			} catch (Exception e) {
				
			}
			
		}
		return result;
	}
	
	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
		setName("Wand of " + effect.getName());
	}

	public int getXP()
	{
		return casterLevel * spellLevel * 750 / 25;
	}

	public boolean isEternal() 
	{		
		return eternal;
	}
	
	public Boolean getEternal()
	{
		if(eternal)
			return true;
		
		return false;
	}

	public void setEternal(boolean eternal) 
	{
		this.eternal = eternal;
	}
}
