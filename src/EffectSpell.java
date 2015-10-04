import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "name", "casterLevel", "spellLevel", "craftCost", "XPCost" })
public class EffectSpell extends Effect {

	public enum Type
	{
		SINGLE_USE_SPELL_COMPLETION		("Single use, spell completion", 25, 1),
		SINGLE_USE_USE_ACTIVATED		("Single use, use-activated", 50, 1),
		CHARGES_SPELL_TRIGGER			("50 charges, spell trigger", 750, 1),
		COMMAND_WORD					("Command Word", 1800, 1),
		USE_ACTIVATED					("Use-activated", 2000, 1),
		CONTINOUS_ROUND					("Continuous - round / level", 2000, 4),
		CONTINOUS_1_MIN					("Continuous - 1 min / level", 2000, 2),
		CONTINOUS_10_MIN				("Continuous - 10 min / level", 2000, 1.5),
		CONTINOUS_24_HOUR				("Continuous - > 24 hours", 2000, .5);
		
		String desc;
		int price;
		int mulitplier;
		Type(String desc, int price, double multiplier)
		{
			this.desc = desc;
			this.price = price;
		}
		
		public int getPrice()
		{
			return price;
		}
		
		public String toString()
		{
			return desc;
		}
	}	
	
	private String name;
	private Type type;
	private int casterLevel;
	private int spellLevel;
	private int craftCost;
	private int xpCost;
	
	public EffectSpell() {}
	
	public EffectSpell(String name, Type type, int casterLevel, int spellLevel, int craftCost, int xpCost)
	{
		this.name = name;
		this.type = type;
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
		this.craftCost = craftCost;
		this.xpCost = xpCost;
	}
	
	public static EffectSpell create()
	{
		EffectSpell newEffect = new EffectSpell("", EffectSpell.Type.CHARGES_SPELL_TRIGGER, 0, 0, 0, 0);
		
		if(newEffect.edit() == JOptionPane.OK_OPTION)
			return newEffect;
		
		return null;
	}
	
	public int edit()
	{
		ArrayList<Object> array = new ArrayList<Object>();
		
		JTextField name = new JTextField(getName());
		JTextField casterLevel = new JTextField(String.valueOf(getCasterLevel()));
		JTextField spellLevel = new JTextField(String.valueOf(getSpellLevel()));
		JTextField craftCost = new JTextField(String.valueOf(getCraftPrice()));
		JTextField xpCost = new JTextField(String.valueOf(getXPCost()));
		
		JComboBox<EffectSpell.Type> spellType = new JComboBox<EffectSpell.Type>(EffectSpell.Type.values());
		spellType.setSelectedItem(type);
		
		array.addAll(Arrays.asList(new Object[] { 
				"Name", name, 
				"Type", type,
				"Caster Level", casterLevel, 
				"Spell Level", spellLevel, 
				"Material Cost", craftCost, 
				"XP Cost", xpCost 
				}));
		
		FocusSelectAll focus = new FocusSelectAll();
		for(Object object : array)
		{
			if(object instanceof JTextField)
				((JTextField) object).addFocusListener(focus);
		}
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				result = Controller.getInstance().editArray(array);
				
				this.setName(name.getText());
				this.setType((EffectSpell.Type) spellType.getSelectedItem());
				this.setCasterLevel(Integer.valueOf(casterLevel.getText()));
				this.setSpellLevel(Integer.valueOf(spellLevel.getText()));
				this.setCraftCost(Integer.valueOf(craftCost.getText()));
				this.setXPCost(Integer.valueOf(xpCost.getText()));
				return result;
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}
		}
		
		return result;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) 
	{
		this.type = type;
	}
	
	@Override
	public int getPrice() {
		int price = 0;
		// Calculate the base price depending on the type of spell
		price += this.getCasterLevel() * this.getSpellLevel() * this.type.getPrice();
		// Is the Spell Effect single use
		if(isSingleUse()) {
			price += this.getCraftPrice() + this.getXPCost();
		} else {
			// Add 50 charges 
			price += 50 * this.getCraftPrice();
			price += 50 * 5 * this.getXPCost();
		}
		 return price;
	}
	
	private boolean isSingleUse()
	{
		return this.getType() == Type.SINGLE_USE_SPELL_COMPLETION ||
				this.getType() == Type.SINGLE_USE_USE_ACTIVATED;
	}

	@XmlElement
	public int getCraftPrice() 
	{
		return craftCost;
	}

	public void setCraftCost(int craftCost) 
	{
		this.craftCost = craftCost;
	}

	@XmlElement(name="XPCost")
	public int getXPCost() 
	{
		return xpCost;
	}

	public void setXPCost(int xpCost) 
	{
		this.xpCost = xpCost;
	}

	public int getCasterLevel() 
	{
		return casterLevel;
	}

	public void setCasterLevel(int casterLevel) throws ValidationException 
	{
		if(casterLevel <= 0)
			throw new ValidationException("Caster level must be greater than zero");
		
		this.casterLevel = casterLevel;
	}

	public int getSpellLevel() 
	{
		return spellLevel;
	}

	public void setSpellLevel(int spellLevel) 
	{
		this.spellLevel = spellLevel;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	@Override
	@XmlElement
	public String getName() {
		return this.name;
	}
}
