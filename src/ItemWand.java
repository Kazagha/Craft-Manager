import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "eternal" })
public class ItemWand extends ItemMagic {

	private boolean eternal;
	
	public ItemWand() {}
	
	public ItemWand(String name) 
	{
		setName(name);
		setEffect(new ArrayList<Effect>());
		super.setItemType(Item.TYPE.MAGIC);
	}

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
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}
			
		}
		return result;
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
	
	@Override
	public int getPrice()
	{		
		int price = 0;
		
		for(Effect effect : this.getEffect())
		{
			if(effect instanceof EffectSpell)
			{
				EffectSpell spell = (EffectSpell) effect;
				// Calculate the base price
				price += 750 * spell.getCasterLevel() * spell.getSpellLevel();
				// Additional costly 'material components'
				price += spell.getCraftPrice() * 50;
				// Additional cost for 'XP components'
				price += spell.getXPCost() * 50 * 5;
			}
		}
		
		return price;			
	}
	
	public boolean isEternal() 
	{		
		return eternal;
	}

	@XmlElement
	public Boolean getEternal()
	{
		if(eternal)
			return true;
		
		return null;
	}

	public void setEternal(boolean eternal) 
	{
		this.eternal = eternal;
		this.setChanged();
	}
}
