
public class EffectBonus extends Effect {

	
	
	
	enum type
	{
		ABILITY_BONUS		("Ability Bonus (enhancement)", 1000);
		
		String desc;
		int price;
		type(String desc, int price)
		{
			this.desc = desc;
			this.price = price;
		}
		
	}
	
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
