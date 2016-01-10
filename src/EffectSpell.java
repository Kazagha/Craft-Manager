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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

@XmlRootElement(name = "Spell")
@XmlType(propOrder={ "name", "casterLevel", "spellLevel", "xpCost", "trigger", "dailyUses", "duration" })
public class EffectSpell extends Effect {

	public enum Trigger
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
		Trigger(String desc, int price, int charges)
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
		FIVE							("5 per day @ 100%", (double) 5/5),
		FOUR							("4 per day @ 80%", (double) 4/5),
		THREE							("3 per day @ 60%", (double) 3/5),
		TWO								("2 per day @ 40%", (double) 2/5),
		ONE								("1 per day @ 20%", (double) 1/5);
		
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
	private Trigger trigger;
	private int casterLevel;
	private int spellLevel;
	private int craftPrice;
	private int xpCost;
	
	private DailyUses dailyUses;
	private SpellDuration duration;
		
	public EffectSpell() {}
	
	private TextField nameField = new TextField();
	private ChoiceBox triggerChoice = new ChoiceBox();
	private ChoiceBox usesChoice = new ChoiceBox();
	private ChoiceBox durationChoice = new ChoiceBox();
	private TextField casterField = new TextField();
	private TextField spellField = new TextField();
	private TextField craftField = new TextField();
	private TextField xpField = new TextField();
	
	public EffectSpell(String name, Trigger type, DailyUses dailyUses, SpellDuration duration, int casterLevel, int spellLevel, int craftPrice, int xpCost)
	{
		this.name = name;
		this.trigger = type;
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
		this.craftPrice = craftPrice;
		this.xpCost = xpCost;
		this.dailyUses = dailyUses;
		this.duration = duration;
	}
	
	public EffectSpell create()
	{
		EffectSpell newEffect = new EffectSpell(
				"", 
				EffectSpell.Trigger.CHARGES_SPELL_TRIGGER, 
				EffectSpell.DailyUses.UNLIMITED, 
				EffectSpell.SpellDuration.ROUNDS, 
				0, 0, 0, 0);
		
		//if(newEffect.edit() == JOptionPane.OK_OPTION)
		//	return newEffect;
		
		return null;
	}
	
	@Override
	public Pane toEditPane()
	{		
		Platform.runLater(() -> nameField.requestFocus());
		
		if (triggerChoice.getItems().size() <= 0 )
		{
			triggerChoice.getItems().addAll((EffectSpell.Trigger.values()));
			usesChoice.getItems().addAll(EffectSpell.DailyUses.values());
			durationChoice.getItems().addAll(EffectSpell.SpellDuration.values());
		}
		
		nameField.setText(this.getName());
		casterField.setText(String.valueOf(casterLevel));
		spellField.setText(String.valueOf(spellLevel));
		triggerChoice.setValue(this.getTrigger());
		usesChoice.setValue(this.getDailyUses());
		durationChoice.setValue(this.getDuration());
		
		return Locator.getView().toDialog(
				new Label("Name"), nameField,
				new Label("Caster Level"), casterField,
				new Label("Spell Level"), spellField,
				new Label("Trigger"), triggerChoice,
				new Label("Daily Uses"), usesChoice,
				new Label("Duration"), durationChoice
				);
	}
	
	@Override
	public boolean validateAndStore()
	{
		if (nameField.getText().equals(""))
		{
			return false;
		}
		
		if (casterField.getText().matches("[A-Za-z]*")
				|| spellField.getText().matches("[A-Za-z]*"))
		{
			return false;
		}
		
		if (triggerChoice.getValue() == null ||
				usesChoice.getValue() == null ||
				durationChoice.getValue() == null) 
		{
			return false;
		}
		
		this.setName(nameField.getText());
		this.setCasterLevel(Integer.valueOf(casterField.getText()));
		this.setSpellLevel(Integer.valueOf(spellField.getText()));
		this.setTrigger((Trigger) triggerChoice.getValue());
		this.setDailyUses((DailyUses) usesChoice.getValue());
		this.setDuration((SpellDuration) durationChoice.getValue());
		
		return true;
	}
	
	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger type) 
	{
		this.trigger = type;
	}
	
	@Override
	public int getPrice() {
		// Set the price variable
		double price = 0;
		
		// Get the spell level (where level 0 spells count as .5)
		double spellLevel = this.getSpellLevel();
		if(spellLevel == 0)
			spellLevel = .5;
		
		// Calculate the base price depending on the type of spell
		price += this.getTrigger().getPrice() * this.getCasterLevel() * spellLevel;

		// Add Costly Material and XP Components
		price += addChargeCost(this.getTrigger().getCharges());
		
		// Additional charges if the Item has Unlimited daily uses 
		if ((this.getTrigger() == Trigger.USE_ACTIVATED || this.getTrigger() == Trigger.COMMAND_WORD)
				&& this.getDailyUses() == DailyUses.UNLIMITED) 
				{
					price += addChargeCost(50);
				}
		
		// Discount for '# uses per day'
		price *= this.getDailyUses().getMultiplier();
		
		// If the item is Continuous 
		if(this.getTrigger() == Trigger.CONTINUOUS)
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

	@XmlTransient
	public int getCraftPrice() 
	{
		return craftPrice;
	}

	public void setCraftPrice(int craftCost) 
	{
		this.craftPrice = craftCost;
	}

	public int getCasterLevel() 
	{
		return casterLevel;
	}

	public void setCasterLevel(int casterLevel)  
	{
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
			
	@XmlElement
	public int getXpCost() 
	{
		return xpCost;
	}

	public void setXpCost(int xpCost) 
	{
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
		return craftPrice;
	}

	public String toString()
	{
		return getName();
	}
	
	public String classToString()
	{
		return "Spell";
	}
}
