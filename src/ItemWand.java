import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ItemWand extends Item{

	private int casterLevel;
	private int spellLevel;
	private boolean eternal;
	private Effect effect;
	
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
		JComboBox<String> eternal = new JComboBox<String>(new String[] { "False", "True" });
		if(this.eternal) 
			eternal.setSelectedIndex(1); 
		
		JButton editButton = new JButton("Edit Effect");
		editButton.setText("Edit Effect");
		editButton.setAction(new Effect.editAction());
				
		array.addAll(Arrays.asList(new Object[] { "Name", name, "Caster Level", casterLevel, "Spell Level", spellLevel, "Eternal", eternal, editButton}));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				result = Controller.editArray(array);
				
				// Set changes on this Item
				this.setName(name.getText());
				this.setEternal(Boolean.valueOf(eternal.getSelectedItem().toString()));
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
	
	@Override
	public int getBaseCost()
	{
		return 0;
	}
	
	@Override 
	public int getMatCost()
	{
		return 0;
	}
	
	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		if(effect == null)
			return;
		
		this.effect = effect;
		this.setName("Wand of " + getEffect().getName());
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
