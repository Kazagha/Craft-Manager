import javax.swing.JOptionPane;


public class EffectBonus extends Effect {

	enum Type
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
	
	public EffectBonus() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCraftCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getXPCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit() {
		// TODO Auto-generated method stub
		return 0;
	}

}
