import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

@XmlRootElement(name = "Bonus")
public class EffectBonus extends Effect {

	@XmlType(name = "ArmorType")
	public enum Type
	{
		ABILITY_BONUS		("Ability Bonus (enhancement)", 1000),
		ARMOR_BONUS			("Armor bonus (enhancement)", 1000),
		AC_BONUS_DEFLECTION ("AC bonus (deflection)", 2000),
		AC_BONUS_OTHER		("AC bonus (other)", 2500),
		AC_NATURAL			("Natural armor bonus (enhancement)", 2000),
		SAVE_BONUS_RESIST	("Save bonus (resistance)", 1000),
		SAVE_BONUS_OTHER	("Save bonus (other)", 2000),
		SKILL_BONUS			("Skill bonus (competence)", 100),
		WEAPON_BONUS		("Weapon bonus (enhancement)", 2000);		
		
		String desc;
		int cost;
		Type(String desc, int cost)
		{
			this.desc = desc;
			this.cost = cost;
		}
		
		public String getDesc()
		{
			return desc;
		}
		
		public int getCost()
		{
			return cost;
		}	
		
		public String toString()
		{
			return getDesc();
		}
	}
	
	int bonus;
	Type type;
	
	private static TextField bonusField = new TextField();
	private static ChoiceBox typeChoiceBox = new ChoiceBox();
	
	public EffectBonus() {}
	
	public EffectBonus(int bonus, Type type)
	{
		this.bonus = bonus;
		this.type = type;
	}
	
	public int squared(int bonus)
	{
		return (int) Math.pow(bonus, 2);
	}

	@Override
	public String getName() 
	{
		return type.getDesc();
	}

	@Override
	public int getPrice()
	{
		return squared(bonus) * type.getCost();
	}
	
	@Override
	public int getCraftPrice() 
	{
		// No additional price to craft
		return 0;
	}

	@Override
	public int getXpCost() 
	{
		return 0;
	}
	
	public int getBonus()
	{
		return bonus;
	}

	public void setBonus(int bonus) 
	{
		this.bonus = bonus;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) 
	{
		this.type = type;
	}
	
	public EffectBonus create()
	{
		EffectBonus newEffect = new EffectBonus(1, EffectBonus.Type.ABILITY_BONUS);
		
		//if(newEffect.edit() == JOptionPane.OK_OPTION)
		//	return newEffect;
		
		return null;
	}
	
	@Override
	public Pane toEditPane()
	{
		Platform.runLater(() -> bonusField.requestFocus());
				
		if (typeChoiceBox.getItems().size() <= 0)
			typeChoiceBox.getItems().addAll(this.type.values());
		
		bonusField.setText(String.valueOf(this.getBonus()));		
		typeChoiceBox.setValue(this.getType());
		
		return Locator.getView().toDialog(
				new Label("Bonus"), bonusField,
				new Label("Type"), typeChoiceBox
				);
	}
	
	@Override
	public boolean validateAndStore()
	{
		if (bonusField.getText().contains("[A-Za-Z]"))
		{
			return false;
		}
		
		if (Integer.valueOf(bonusField.getText()) <= 0)
		{
			return false;
		}
		
		this.setBonus(Integer.valueOf(bonusField.getText()));
		this.setType((Type) typeChoiceBox.getValue());
		
		return true;
	}
	
	public String toString()
	{
		return String.format("+%d %s", this.getBonus(), this.getType());
	}
	
	public String classToString()
	{
		return "Enhancement Bonus";
	}
}
