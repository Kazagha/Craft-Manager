
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
		
		public enhance getEnhance(int i)
		{
			return enhance.values()[i];
		}
		
		public String getString(int i)
		{
			return this.getEnhance(i).toString();
		}
		
		public int getCost(int i)
		{
			return this.getEnhance(i).getCost();
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
		return "+" + bonus;
	}

	@Override
	public int getBaseCost()
	{
		return 0;
	}

	@Override
	public int getMaterialCost() 
	{
		return 0;
	}

	@Override
	public int getXPCost() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit() 
	{
		return 0;
	}

}
