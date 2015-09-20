
public class EnhanceEffect extends Effect {

	String name;
	int bonus;
		
	enum enhance
	{
		ONE("+1", 1000);
		
		String str;
		int cost;
		enhance(String s, int i)
		{
			this.str = s;
			this.cost = i;
		}
		
		public static enhance getEnhance(int i)
		{
			return enhance.values()[i];
		}
		
		public static String getString(int i)
		{
			return getEnhance(i).toString();
		}
		
		public static int getCost(int i)
		{
			return getEnhance(i).getCost();
		}

		public String toString() 
		{
			return str;
		}

		public int getCost()
		{
			return cost;
		}
	}
	
	public EnhanceEffect() {};
	
	public EnhanceEffect(String name, int bonus)
	{
		this.name = name;
		this.bonus = bonus;
	}
	
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() 
	{
		return enhance.getString(bonus).toString();
	}

	@Override
	public int getBaseCost()
	{
		return enhance.getCost(bonus);
	}

	@Override
	public int getMaterialCost() 
	{
		return enhance.getCost(bonus) / 2;
	}

	@Override
	public int getXPCost() 
	{
		return enhance.getCost(bonus) / 25;
	}

	@Override
	public int edit() 
	{
		return 0;
	}

}
