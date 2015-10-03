import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class EffectBonus extends Effect {

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
	public int getCost()
	{
		return squared(bonus) * type.getCost();
	}

	@Override
	public int getCraftCost() 
	{
		return this.getCost() / 2;
	}

	@Override
	public int getXPCost() 
	{
		return this.getCost() / 25;
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
	
	public static EffectBonus create()
	{
		EffectBonus newEffect = new EffectBonus(1, EffectBonus.Type.ABILITY_BONUS);
		
		if(newEffect.edit() == JOptionPane.OK_OPTION)
			return newEffect;
		
		return null;
	}

	@Override
	public int edit() 
	{
		Object[] array;
		
		JComboBox<EffectBonus.Type> bonusType =
				new JComboBox<EffectBonus.Type>(EffectBonus.Type.values());
		bonusType.setSelectedItem(this.getType());
		JTextField bonus = new JTextField(String.valueOf(this.getBonus()));
		
		array = new Object[] { "Bonus Type", bonusType, "Bonus", bonus };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				result = Controller.getInstance().editArray(array);
				
				// Set changes 
				this.setType((EffectBonus.Type) bonusType.getSelectedItem());
				this.setBonus(Integer.valueOf(bonus.getText()));				
				return result;
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}			
		}
		
		return result;
	}
}
