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
		CHARGES_SPELL_TRIGGER			("50 charges, spell trigger", 750, 50),
		COMMAND_WORD					("Command Word", 1800, 50),
		USE_ACTIVATED					("Use-activated", 2000, 50),
		CONTINUOUS						("Continuous", 2000, 100);
		
		String desc;
		int price;
		int charges;
		Type(String desc, int price, int charges)
		{
			this.desc = desc;
			this.price = price;
			this.charges = charges;
		}
		
		public int getPrice()
		{
			return price;
		}
		
		public int getCharges()
		{
			return charges;
		}
		
		public String toString()
		{
			return desc;
		}
	}
	
	public enum DailyUses
	{
		UNLIMITED						("Unlimited Uses", 1),
		FIVE							("5 per day @ 100%", 5/5),
		FOUR							("4 per day @ 80%", 4/5),
		THREE							("3 per day @ 60%", 3/5),
		TWO								("2 per day @ 40%", 2/5),
		ONE								("1 per day @ 20%", 1/5);
		
		String desc;
		double multiplier;
		DailyUses(String desc, double multiplier)
		{
			this.desc = desc;
			this.multiplier = multiplier;
		}	

		public double getMultiplier() 
		{
			return this.multiplier;
		}

		public String toString()
		{
			return this.desc;
		}
	}
	
	public enum SpellDuration
	{
		ROUNDS							("Rounds / level", 4),
		ONE_MINUTE						("1 minute / level", 2),
		TEN_MINUTE						("10 minutes / level", 1.5),
		DAY								("> 24 hour", .5);
		
		String desc;
		double multiplier;
		SpellDuration(String desc, double multiplier)
		{
			this.desc = desc;
			this.multiplier = multiplier;
		}
		
		public double getMultiplier()
		{
			return this.multiplier;
		}
		
		public String toString()
		{
			return this.desc;
		}
	}
	
	private String name;
	private Type type;
	private int casterLevel;
	private int spellLevel;
	private int craftCost;
	private int xpCost;
	
	private DailyUses dailyUses;
	private SpellDuration duration;
	
	public EffectSpell() {}
	
	public EffectSpell(String name, Type type, DailyUses dailyUses, SpellDuration duration, int casterLevel, int spellLevel, int craftCost, int xpCost)
	{
		this.name = name;
		this.type = type;
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
		this.craftCost = craftCost;
		this.xpCost = xpCost;
		this.dailyUses = dailyUses;
		this.duration = duration;
	}
	
	public static EffectSpell create()
	{
		EffectSpell newEffect = new EffectSpell(
				"", 
				EffectSpell.Type.CHARGES_SPELL_TRIGGER, 
				EffectSpell.DailyUses.UNLIMITED, 
				EffectSpell.SpellDuration.ROUNDS, 
				0, 0, 0, 0);
		
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
		spellType.setSelectedItem(this.type);
		
		JComboBox<EffectSpell.DailyUses> dailyUses = new JComboBox<EffectSpell.DailyUses>(EffectSpell.DailyUses.values());
		dailyUses.setSelectedItem(this.dailyUses);
		
		JComboBox<EffectSpell.SpellDuration> duration = new JComboBox<EffectSpell.SpellDuration>(EffectSpell.SpellDuration.values());
		duration.setSelectedItem(this.duration);
		
		array.addAll(Arrays.asList(new Object[] { 
				"Name", name,						
				"Caster Level", casterLevel, 
				"Spell Level", spellLevel, 
				"Material Cost", craftCost, 
				"XP Cost", xpCost,
				"Type", spellType,	
				"Daily Uses", dailyUses,
				"Duration", duration
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
				this.setCasterLevel(Integer.valueOf(casterLevel.getText()));
				this.setSpellLevel(Integer.valueOf(spellLevel.getText()));
				this.setCraftCost(Integer.valueOf(craftCost.getText()));
				this.setXPCost(Integer.valueOf(xpCost.getText()));
				
				this.setType((EffectSpell.Type) spellType.getSelectedItem());
				this.setDailyUses((EffectSpell.DailyUses) dailyUses.getSelectedItem());
				this.setDuration((EffectSpell.SpellDuration) duration.getSelectedItem());
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
		double price = 0;
		// Calculate the base price depending on the type of spell
		price += this.getType().getPrice() * this.getCasterLevel() * this.getSpellLevel();

		// Add Costly Material and XP Components
		price += addChargeCost(this.getType().getCharges());
		
		// Additional charges if the Item has Unlimited daily uses 
		if ((this.getType() == Type.USE_ACTIVATED || this.getType() == Type.COMMAND_WORD)
				&& this.getDailyUses() == DailyUses.UNLIMITED) 
				{
					price += addChargeCost(50);
				}
		
		// Discount for '# uses per day'
		price *= this.getDailyUses().getMultiplier();
		
		// If the item is Continuous 
		if(this.getType() == Type.CONTINUOUS)
		{
			price *= this.getDuration().getMultiplier();
		}
		
		/*
		// Is the Spell Effect single use
		switch(this.getType())
		{
		case SINGLE_USE_SPELL_COMPLETION:		
			// Spell Cost
			price += 25 * this.getCasterLevel() * this.getSpellLevel();
			// Costly material Components
			price += this.craftCost;
			break;
		case SINGLE_USE_USE_ACTIVATED:
			// Spell Cost
			price += 50 * this.getCasterLevel() * this.getSpellLevel();
			// Costly material components
			break;
		case CHARGES_SPELL_TRIGGER:
			// Spell Cost
			price += 750 * this.getCasterLevel() * this.getSpellLevel();
			// Material Cost
			price += 50 * this.getCraftPrice();
			// XP Cost
			price += 50 * 5 * this.getXPCost();
			break;
		case COMMAND_WORD:	
			// Spell Cost
			price += 1800 * this.getCasterLevel() * this.getSpellLevel();
			// Number of charges required
			int charges = 50;
			if(this.dailyUses == EffectSpell.Daily_Uses.UNLIMITED)
				charges = 100;
			// Material Cost			
			price += charges * this.getCraftPrice();
			// XP Cost
			price += charges * 5 * this.getXPCost();			
			// Discount for uses per day
			price *= this.getDailyUses().getMultiplier();
			break;		
		case USE_ACTIVATED:
		case CONTINOUS:
			// Spell Cost
			price += 2000 * this.getCasterLevel() * this.getSpellLevel();
			break;		
		}
		
		*/
		 return (int) price;
	}
	
	private int addChargeCost(int charges)
	{
		return charges * (this.getCraftPrice() + (this.getXpCost() * 5));
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
	public String getName() 
	{
		return this.name;
	}
			
	public int getXpCost() {
		return xpCost;
	}

	public void setXpCost(int xpCost) {
		this.xpCost = xpCost;
	}

	public DailyUses getDailyUses() {
		return dailyUses;
	}

	public void setDailyUses(DailyUses dailyUses) {
		this.dailyUses = dailyUses;
	}

	public SpellDuration getDuration() {
		return duration;
	}

	public void setDuration(SpellDuration duration) {
		this.duration = duration;
	}

	public int getCraftCost() {
		return craftCost;
	}

	public String toString()
	{
		return getName();
	}
}
