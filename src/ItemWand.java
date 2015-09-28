import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ItemWand extends ItemMagic {

	private String name;
	private int casterLevel;
	private int spellLevel;
	private boolean eternal;
	private ArrayList<Effect> effect;
	
	public ItemWand() {}
	
	public ItemWand(String name) 
	{
		setName(name);
		effect = new ArrayList<Effect>();
	}

	@Override
	void update() {}

	public static Item create() 
	{
		ItemWand newItem = new ItemWand("Wand of");
		if(newItem.edit() == JOptionPane.OK_OPTION)
			return newItem;
		
		return null;
	}
	
	@Override
	public int edit() 
	{
		ArrayList<Object> array = new ArrayList<Object>();

		JTextField name = new JTextField(this.getName());
		//JComboBox<String> eternal = new JComboBox<String>(new String[] { "False", "True" });
		JCheckBox eternal = new JCheckBox();
		eternal.setSelected(this.eternal);
				
		array.addAll(Arrays.asList(new Object[] { 
				"Name", name, 
				"Eternal", eternal, 
				}));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				result = Controller.getInstance().editArray(array);
				
				// Set changes on this Item
				this.setName(name.getText());
				this.setEternal(eternal.isSelected());
				return result;
			} catch (Exception e) {
				
			}
			
		}
		return result;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{			
		this.name = name;
		this.setChanged();
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
	
	public ArrayList<Effect> getEffect() 
	{
		return effect;
	}
	
	public void setEffect(ArrayList<Effect> effects)
	{
		this.effect = effects;
		this.setName("Wand of " + effects.get(0).getName());
		this.setChanged();
	}

	public void addEffect(Effect newEffect) {
		if(newEffect == null)
			return;
		
		// Allow only only one effect to be set
		if(getEffect().size() > 0)
		{
			this.getEffect().set(0, newEffect);
		} else {
			this.getEffect().add(newEffect);
		}
		// Set the name to match the effect
		this.setName("Wand of " + newEffect.getName());
		
		this.setChanged();
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
		this.setChanged();
	}
}
