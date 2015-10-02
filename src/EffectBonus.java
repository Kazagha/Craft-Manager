import javax.swing.JOptionPane;


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

	@Override
	public int edit() { return 0; }
}
